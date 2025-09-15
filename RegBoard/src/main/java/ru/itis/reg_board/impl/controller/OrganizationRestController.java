package ru.itis.reg_board.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.reg_board.api.api.api.OrganizationRestApi;
import ru.itis.reg_board.api.dto.request.AddAdminRequest;
import ru.itis.reg_board.api.dto.request.OrganizationRequest;
import ru.itis.reg_board.api.dto.response.AccountResponse;
import ru.itis.reg_board.api.dto.response.OrganizationAdminResponse;
import ru.itis.reg_board.api.dto.response.OrganizationBriefResponse;
import ru.itis.reg_board.api.dto.response.OrganizationResponse;
import ru.itis.reg_board.impl.security.details.AccountUserDetails;
import ru.itis.reg_board.impl.service.OrganizationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrganizationRestController implements OrganizationRestApi {

    private final OrganizationService orgService;

    @Override
    public List<OrganizationBriefResponse> getAllBriefOrganizations(AccountUserDetails userDetails) {
        Long accountId = userDetails.accountId();
        return orgService.getAllBriefOrganizations(accountId);
    }

    @Override
    public OrganizationAdminResponse getOrganization(long orgId) {
        return orgService.getOrganization(orgId);
    }

    @Override
    public OrganizationAdminResponse createOrganization(AccountUserDetails userDetails, OrganizationRequest organizationRequest) {
        Long accountId = userDetails.accountId();
        return orgService.createOrganization(accountId, organizationRequest);
    }

    @Override
    public OrganizationResponse updateOrganization(long orgId, OrganizationRequest organizationRequest) {
        return orgService.updateOrganization(orgId, organizationRequest);
    }

    @Override
    public void deleteOrganization(long orgId) {
        orgService.deleteOrganization(orgId);
    }

    @Override
    public AccountResponse addAdmin(long orgId, AddAdminRequest request) {
        return orgService.addAdmin(orgId, request.email());
    }

    @Override
    public void deleteAdmin(long orgId, long accountId) {
        orgService.deleteAdmin(orgId, accountId);
    }
}
