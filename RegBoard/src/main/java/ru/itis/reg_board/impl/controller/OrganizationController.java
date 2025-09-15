package ru.itis.reg_board.impl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.itis.reg_board.api.api.api.OrganizationApi;
import ru.itis.reg_board.impl.security.details.AccountUserDetails;

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
