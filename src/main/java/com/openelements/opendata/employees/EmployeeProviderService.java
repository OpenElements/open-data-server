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

    private final static String JSON_URL = "https://raw.githubusercontent.com/OpenElements/open-elements-website/refs/heads/main/data/en/team.json";

    @Autowired
    public EmployeeProviderService(@NonNull final UpdateEntityRepository updateRepository) {
        super(EmployeeDTO.class, updateRepository);
    }

    @Override
    public List<EmployeeDTO> getAvailableData(@NonNull final ZonedDateTime lastUpdate) {
        final RestClient restClient = RestClient.create();
        final String jsonString = restClient.get()
                .uri(JSON_URL)
                .retrieve()
                .body(String.class);
        try {
            final JsonNode jsonNode = new ObjectMapper().readTree(jsonString);
            final ArrayNode arrayNode = (ArrayNode) jsonNode;
            final List<EmployeeDTO> employees = new ArrayList<>();
            for (JsonNode node : arrayNode) {
                final String id = node.get("id").asText();
                final String firstName = node.get("firstName").asText();
                final String lastName = node.get("lastName").asText();
                final String role;
                if (node.has("role")) {
                    role = node.get("role").asText();
                } else {
                    role = null;
                }
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
                final EmployeeDTO dto = new EmployeeDTO(EMPLOYEE_UUID_PREFIX + id, firstName, lastName, role,
                        gitHubUsername);
                employees.add(dto);
            }
            return employees;
        } catch (final Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
