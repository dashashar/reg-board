package ru.itis.semester_work.api.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.semester_work.impl.security.details.AccountUserDetails;

@RequestMapping("/org")
public interface OrganizationApi {

    @GetMapping("/account")
    String getAllOrganizationsPage(@AuthenticationPrincipal AccountUserDetails userDetails, Model model);

    @GetMapping("/{orgId}")
    String getOrganizationPage(@PathVariable("orgId") long orgId, Model model);

}
