package com.currix.distributed_lovable.workspace_service.service;

import com.currix.distributed_lovable.workspace_service.dto.project.DeployResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public interface DeploymentService {

    @Nullable DeployResponse deploy(Long projectId);
}
