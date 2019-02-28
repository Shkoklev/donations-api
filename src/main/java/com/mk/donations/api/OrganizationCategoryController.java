package com.mk.donations.api;

import com.mk.donations.model.OrganizationCategory;
import com.mk.donations.service.OrganizationCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class OrganizationCategoryController {

    private final OrganizationCategoryService organizationCategoryService;

    public OrganizationCategoryController(OrganizationCategoryService organizationCategoryService) {
        this.organizationCategoryService = organizationCategoryService;
    }

    @GetMapping
    public List<OrganizationCategory> getCategories() {
        return organizationCategoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteOrganizationCategoryById(@PathVariable Long id) {
        organizationCategoryService.deleteOrganizationCategoryById(id);
    }

    @PostMapping
    public OrganizationCategory saveOrganizationCategory(@Valid @RequestBody OrganizationCategory organizationCategory) {
        return organizationCategoryService.saveOrganizationCategory(organizationCategory);
    }

}
