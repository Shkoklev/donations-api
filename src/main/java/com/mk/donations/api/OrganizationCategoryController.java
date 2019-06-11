package com.mk.donations.api;

import com.mk.donations.model.OrganizationCategory;
import com.mk.donations.model.exception.ParameterMissingException;
import com.mk.donations.model.request.OrganizationCategoryRequest;
import com.mk.donations.service.OrganizationCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin({"*", "localhost:3000"})
@RestController
@RequestMapping("/organization_categories")
public class OrganizationCategoryController {

    private final OrganizationCategoryService organizationCategoryService;

    public OrganizationCategoryController(OrganizationCategoryService organizationCategoryService) {
        this.organizationCategoryService = organizationCategoryService;
    }

    @GetMapping
    public List<OrganizationCategory> getCategories() {
        return organizationCategoryService.getAllCategories();
    }

//    @DeleteMapping("/{id}")
//    public void deleteOrganizationCategoryById(@PathVariable Long id) {
//        organizationCategoryService.deleteOrganizationCategoryById(id);
//    }
//
//    @PostMapping
//    public OrganizationCategory saveOrganizationCategory(@Valid @RequestBody OrganizationCategory organizationCategory) {
//        return organizationCategoryService.saveOrganizationCategory(organizationCategory);
//    }
//
//    @PutMapping("/{id}")
//    public OrganizationCategory updateOrganizationCategory(@PathVariable Long id, @Valid @RequestBody OrganizationCategoryRequest request) {
//        checkForEmptyRequest(request);
//        return organizationCategoryService.updateOrganizationCategory(id, request.name, request.pictureUrl);
//    }
//
//    public void checkForEmptyRequest(OrganizationCategoryRequest request) {
//        if ((request.name == null || request.name.isEmpty()) && (request.pictureUrl == null || request.pictureUrl.isEmpty()))
//            throw new ParameterMissingException("Внесете барем едно поле. ");
//    }

}
