package ru.itis.semester_work.impl.service;

import ru.itis.semester_work.api.dto.request.OrganizationRequest;
import ru.itis.semester_work.api.dto.response.AccountResponse;
import ru.itis.semester_work.api.dto.response.OrganizationBriefResponse;
import ru.itis.semester_work.api.dto.response.OrganizationAdminResponse;
import ru.itis.semester_work.api.dto.response.OrganizationResponse;

import java.util.List;

public interface OrganizationService {

    List<OrganizationBriefResponse> getAllBriefOrganizations(long accountId);

    OrganizationAdminResponse getOrganization(long orgId);

    OrganizationAdminResponse createOrganization(long accountId, OrganizationRequest organizationRequest);

    OrganizationResponse updateOrganization(long orgId, OrganizationRequest organizationRequest);

    void deleteOrganization(long orgId);

    AccountResponse addAdmin(long orgId, String email);

    void deleteAdmin(long orgId, long accountId);

}
