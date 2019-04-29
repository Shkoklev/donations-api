package com.mk.donations.api;

import com.mk.donations.model.Organization;
import com.mk.donations.service.OrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    Page<Organization> getOrganizations(Pageable pageable) {
        return organizationService.getOrganizationsPage(pageable);
    }

    @GetMapping("/{categoryId}")
    Page<Organization> getOrganizationsPageByCategory(@PathVariable Long categoryId, Pageable pageable) {
        return organizationService.getOrganizationsPageByCategory(pageable, categoryId);
    }

}
