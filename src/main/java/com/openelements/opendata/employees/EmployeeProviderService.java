package com.openelements.opendata.employees;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.openelements.opendata.base.AbstractProviderService;
import com.openelements.opendata.base.UpdateEntityRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EmployeeProviderService extends AbstractProviderService<EmployeeDTO> {

    private final static String EMPLOYEE_UUID_PREFIX = "Employee-";

    @Autowired
    public EmployeeProviderService(@NonNull UpdateEntityRepository updateRepository) {
        super(EmployeeDTO.class, updateRepository);
    }

    @Override
    public List<EmployeeDTO> getAvailableData(ZonedDateTime lastUpdate) {
        RestClient restClient = RestClient.create();
        final String jsonString = restClient.get()
                .uri("https://raw.githubusercontent.com/OpenElements/open-elements-website/refs/heads/main/data/en/team.json")
                .retrieve()
                .body(String.class);
        try {
            final JsonNode jsonNode = new ObjectMapper().readTree(jsonString);
            final ArrayNode arrayNode = (ArrayNode) jsonNode;
            final List<EmployeeDTO> employees = new ArrayList<>();
            for (JsonNode node : arrayNode) {
                String id = node.get("id").asText();
                String name = node.get("firstName").asText() + " " + node.get("lastName").asText();
                String gitHubUsername = null;
                if (node.has("socials")) {
                    final JsonNode socials = node.get("socials");
                    if (socials.isArray()) {
                        for (JsonNode social : socials) {
                            if (social.get("name").asText().equals("GitHub")) {
                                gitHubUsername = social.get("link").asText().substring("https://github.com/".length());
                            }
                        }
                    }
                }
                EmployeeDTO dto = new EmployeeDTO(EMPLOYEE_UUID_PREFIX + id, name, gitHubUsername);
                employees.add(dto);
            }
            return employees;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
