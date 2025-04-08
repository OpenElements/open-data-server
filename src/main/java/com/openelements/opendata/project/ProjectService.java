package com.openelements.opendata.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.openelements.opendata.base.AbstractService;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
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
                        final Function<String, String> base64ImageSupplier = fieldName -> {
                            if (node.has(fieldName)) {
                                final String imageFile = node.get(fieldName).asText();
                                try (final InputStream inputStream = ProjectService.class.getClassLoader()
                                        .getResourceAsStream("logos/" + imageFile)) {
                                    try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                                        final byte[] buffer = new byte[8192];
                                        int bytesRead;
                                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                                            outputStream.write(buffer, 0, bytesRead);
                                        }
                                        final byte[] inputBytes = outputStream.toByteArray();
                                        return Base64.getEncoder().encodeToString(inputBytes);
                                    }
                                } catch (Exception e) {
                                    throw new RuntimeException("Error in reading stream", e);
                                }
                            } else {
                                return null;
                            }
                        };
                        final String svgLogoForBrightBackground;
                        if (node.has("svgLogoForBrightBackground")) {
                            svgLogoForBrightBackground = node.get("svgLogoForBrightBackground").asText();
                        } else {
                            svgLogoForBrightBackground = null;
                        }
                        final String svgLogoForDarkBackground;
                        if (node.has("svgLogoForDarkBackground")) {
                            svgLogoForDarkBackground = node.get("svgLogoForDarkBackground").asText();
                        } else {
                            svgLogoForDarkBackground = null;
                        }
                        final String pngLogoForBrightBackground;
                        if (node.has("pngLogoForBrightBackground")) {
                            pngLogoForBrightBackground = node.get("pngLogoForBrightBackground").asText();
                        } else {
                            pngLogoForBrightBackground = null;
                        }
                        final String pngLogoForDarkBackground;
                        if (node.has("pngLogoForDarkBackground")) {
                            pngLogoForDarkBackground = node.get("pngLogoForDarkBackground").asText();
                        } else {
                            pngLogoForDarkBackground = null;
                        }

                        final Set<String> matchingRepos = new HashSet<>();
                        final ArrayNode matchingReposNode = (ArrayNode) node.get("repositories");
                        matchingReposNode.forEach(matchingRepoNode -> {
                            matchingRepos.add(matchingRepoNode.asText());
                        });
                        result.add(new ProjectDTO(uuid, name, description, svgLogoForBrightBackground,
                                svgLogoForDarkBackground, pngLogoForBrightBackground, pngLogoForDarkBackground,
                                Collections.unmodifiableSet(matchingRepos)));
                    }
                });
            }
            return Collections.unmodifiableList(result);
        } catch (final Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    private String getImageAsBase64(final String name) {
        return null;
    }
}
