package com.currix.distributed_lovable.workspace_service.service;

import com.currix.distributed_lovable.workspace_service.dto.member.InviteMemberRequest;
import com.currix.distributed_lovable.workspace_service.dto.member.MemberResponse;
import com.currix.distributed_lovable.workspace_service.dto.member.UpdateMemberRoleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectMemberService {

    List<MemberResponse> getProjectMembers(Long projectId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request);

    void removeProjectMember(Long projectId, Long memberId);
}
