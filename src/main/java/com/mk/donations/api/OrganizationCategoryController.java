package com.mk.donations.api;

import com.mk.donations.api.error.ApiError;
import com.mk.donations.model.OrganizationCategory;
import com.mk.donations.model.exception.EntityNotFoundException;
import com.mk.donations.service.OrganizationCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
