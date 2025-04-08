package com.openelements.opendata.project;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "ProjectsEndpoint", description = "Endpoint to get metadata about Open Source Projects")
public class ProjectEndpoint {

    private final ProjectService projectService;

    public ProjectEndpoint(@NonNull final ProjectService projectService) {
        this.projectService = Objects.requireNonNull(projectService);
    }

    @NonNull
    @GetMapping(value = "/projects", produces = {"application/json"})
    @Operation(summary = "Endpoint to get information about all projects")
    public List<ProjectDTO> getProjects() {
        return projectService.getAll();
    }

    @GetMapping(value = "/projects/count", produces = {"application/json"})
    @Operation(summary = "Endpoint to get the number of projects")
    public int getCount() {
        return projectService.getAll().size();
    }

}
