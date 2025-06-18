package ru.itis.semester_work.impl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.itis.semester_work.api.api.OrganizationApi;
import ru.itis.semester_work.impl.security.details.AccountUserDetails;

@Controller
public class OrganizationController implements OrganizationApi {

    @Override
    public String getAllOrganizationsPage(AccountUserDetails userDetails, Model model) {
        Long accountId = userDetails.accountId();
        model.addAttribute("accountId", String.valueOf(accountId));
        return "org/organizations_list";
    }

    @Override
    public String getOrganizationPage(long orgId, Model model) {
        model.addAttribute("orgId", String.valueOf(orgId));
        return "org/organization_update";
    }
}
