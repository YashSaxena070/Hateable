package com.currix.distributed_lovable.workspace_service.service;

import com.currix.distributed_lovable.common_lib.enums.ProjectPermission;
import com.currix.distributed_lovable.workspace_service.dto.project.ProjectRequest;
import com.currix.distributed_lovable.workspace_service.dto.project.ProjectResponse;
import com.currix.distributed_lovable.workspace_service.dto.project.ProjectSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    List<ProjectSummaryResponse> getUserProjects();

    ProjectSummaryResponse getUserProjectById(Long id);

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse updateProject(Long id, ProjectRequest request);

    void softDelete(Long id);

    boolean hasPermission(Long projectId, ProjectPermission permission);

}
