package com.openelements.opendata.organization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.openelements.opendata.base.AbstractService;
import com.openelements.opendata.base.Language;
import com.openelements.opendata.project.ProjectService;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService extends AbstractService<OrganizationDTO> {

    @Override
    public @NonNull List<OrganizationDTO> getAll(Language language) {
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            final JsonNode jsonNode = mapper.readTree(
                    ProjectService.class.getClassLoader().getResourceAsStream("organization.json"));
            final String uuid = jsonNode.get("uuid").asText();
            final String name = jsonNode.get("name").asText();
            final String legalName = jsonNode.get("legalName").asText();
            final String streetAddress = jsonNode.get("streetAddress").asText();
            final String postalCode = jsonNode.get("postalCode").asText();
            final String city = jsonNode.get("city").asText();
            final String country = jsonNode.get("country").asText();
            final String email = jsonNode.get("email").asText();
            final String telephone = jsonNode.get("telephone").asText();
            final String founder = jsonNode.get("founder").asText();
            final String registerNumber = jsonNode.get("registerNumber").asText();
            final String registerCourt = jsonNode.get("registerCourt").asText();
            final String vatNumber = jsonNode.get("vatId").asText();
            final String url = jsonNode.get("url").asText();

            OrganizationDTO dto = new OrganizationDTO(uuid, name, legalName, streetAddress, postalCode, city,
                    country,
                    email, telephone, founder, registerNumber, registerCourt, vatNumber, url);
            return List.of(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error in reading stream", e);
        }
    }

    @Override
    public long getCount() {
        return getAll(Language.EN).size();
    }
}
