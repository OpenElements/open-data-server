package com.openelements.opendata.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.openelements.opendata.base.AbstractService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends AbstractService<ProjectDTO> {

    private final static String PROJECT_UUID_PREFIX = "Project-";

    @NonNull
    @Override
    public List<ProjectDTO> getAll() {
        try {
            final List<ProjectDTO> result = new ArrayList<>();
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            final JsonNode jsonNode = mapper.readTree(
                    ProjectService.class.getClassLoader().getResourceAsStream("projects.yml"));
            if (jsonNode.isArray()) {
                final ArrayNode arrayNode = (ArrayNode) jsonNode;
                arrayNode.forEach(node -> {
                    final boolean propagate = node.get("propagate").asBoolean(false);
                    if (propagate) {
                        final String uuid = PROJECT_UUID_PREFIX + node.get("identifier").asText();
                        final String name = node.get("name").asText();
                        final String description = node.get("description").asText();
                        final String logoPath = node.get("logo").asText();
                        final Set<String> matchingRepos = new HashSet<>();
                        final ArrayNode matchingReposNode = (ArrayNode) node.get("repositories");
                        matchingReposNode.forEach(matchingRepoNode -> {
                            matchingRepos.add(matchingRepoNode.asText());
                        });
                        result.add(new ProjectDTO(uuid, name, description, logoPath,
                                Collections.unmodifiableSet(matchingRepos)));
                    }
                });
            }
            return Collections.unmodifiableList(result);
        } catch (final Exception e) {
            throw new RuntimeException("Error", e);
        }
    }
}
