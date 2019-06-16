package com.mk.donations.api;

import com.mk.donations.model.*;
import com.mk.donations.model.exception.ParameterMissingException;
import com.mk.donations.model.request.*;
import com.mk.donations.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin({"*", "localhost:3000"})
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final DemandCategoryService demandCategoryService;
    private final OrganizationCategoryService organizationCategoryService;
    private final DemandService demandService;
    private final DonorService donorService;
    private final OrganizationService organizationService;

    public AdminController(AdminService adminService, DemandCategoryService demandCategoryService,
                           OrganizationCategoryService organizationCategoryService,
                           DemandService demandService, DonorService donorService, OrganizationService organizationService) {
        this.adminService = adminService;
        this.demandCategoryService = demandCategoryService;
        this.organizationCategoryService = organizationCategoryService;
        this.demandService = demandService;
        this.donorService = donorService;
        this.organizationService = organizationService;
    }

    @PostMapping("/register")
    public Admin registerAdmin(@Valid @RequestBody Admin adminRequest) {
        return adminService.saveAdmin(adminRequest.getEmail(), adminRequest.getPassword());
    }

    @GetMapping("/loggedAdmin")
    public Admin getLoggedAdmin(Authentication authentication) {
        Long id = Long.valueOf(authentication.getDetails().toString());
        return adminService.getById(id);
    }

    @DeleteMapping("/delete")
    public void deleteAdmin(Authentication authentication) {
        SecurityContextHolder.clearContext();
        Admin admin = getLoggedAdmin(authentication);
        adminService.deleteAdmin(admin);
    }

    @DeleteMapping("/demand_categories/{id}")
    public void deleteDemandCategoryById(@PathVariable Long id) {
        demandCategoryService.deleteDemandCategoryById(id);
    }

    @PostMapping("/demand_categories")
    public DemandCategory saveOrganizationCategory(@Valid @RequestBody DemandCategory demandCategory) {
        return demandCategoryService.saveDemandCategory(demandCategory.getName());
    }

    @PutMapping("/demand_categories/{id}")
    public DemandCategory updateOrganizationCategory(@PathVariable Long id, @Valid @RequestBody DemandCategory demandCategory) {
        return demandCategoryService.updateOrganizationCategory(id, demandCategory.getName());
    }

    @DeleteMapping("/organization_categories/{id}")
    public void deleteOrganizationCategoryById(@PathVariable Long id) {
        organizationCategoryService.deleteOrganizationCategoryById(id);
    }

    @PostMapping("/organization_categories")
    public OrganizationCategory saveOrganizationCategory(@Valid @RequestBody OrganizationCategory organizationCategory) {
        return organizationCategoryService.saveOrganizationCategory(organizationCategory);
    }

    @PutMapping("/organization_categories/{id}")
    public OrganizationCategory updateOrganizationCategory(@PathVariable Long id, @Valid @RequestBody OrganizationCategoryRequest request) {
        checkForEmptyOrganizationCategoryRequest(request);
        return organizationCategoryService.updateOrganizationCategory(id, request.name, request.pictureUrl);
    }

    @PostMapping("/demands")
    public Demand saveDemand(@Valid @RequestBody DemandRequest demandRequest) {
        String demandName = demandRequest.name;
        String demandCategory = demandRequest.category;
        String demandUnitName = demandRequest.unitName;
        return demandService.saveDemand(demandName, demandCategory, demandUnitName);
    }

    @DeleteMapping("/demands/{id}")
    public void deleteDemandById(@PathVariable Long id) {
        demandService.deleteDemandById(id);
    }

    @PutMapping("/demands/{id}")
    public Demand updateDemand(@PathVariable Long id, @Valid @RequestBody Demand demand) {
        return demandService.updateDemand(id, demand.getName());
    }

    @PostMapping("/demands/link_to_category")
    public void linkDemandToCategory(@Valid @RequestBody DemandCategoryRequest request) {
        demandService.linkDemandToCategory(request.demandName, request.demandCategoryName);
    }

    @PutMapping("/donors/{donorId}")
    public Donor updateDonor(@Valid @RequestBody EditDonorRequest editDonorRequest, @PathVariable Long donorId) {
        checkForEmptyDonorRequest(editDonorRequest);
        String firstName = editDonorRequest.firstName;
        String lastName = editDonorRequest.lastName;
        String email = editDonorRequest.email;
        String password = editDonorRequest.password;
        String phone = editDonorRequest.phone;
        String pictureUrl = editDonorRequest.pictureUrl;
        return this.donorService.updateDonor(donorId, firstName, lastName, email, password, phone, pictureUrl);
    }

    @DeleteMapping("/donors/{donorId}")
    public void deleteDonor(@PathVariable Long donorId) {
        this.donorService.deleteDonor(donorId);
    }

    @PutMapping("/organizations/{organizationId}")
    public Organization updateOrganization(@PathVariable Long organizationId, @Valid @RequestBody EditOrganizationRequest request) {
        checkForEmptyOrganizationRequest(request);
        return organizationService.updateOrganization(organizationId, request.email, request.password, request.phone);

    }

    @DeleteMapping("/organizations/{organizationId}")
    public void deleteOrganization(@PathVariable Long organizationId) {
        this.organizationService.deleteOrganization(organizationId);
    }

    public void checkForEmptyOrganizationRequest(EditOrganizationRequest request) {
        if ((request.email == null || request.email.isEmpty()) && (request.password == null || request.password.isEmpty())
                && (request.phone == null || request.phone.isEmpty()))
            throw new ParameterMissingException("Внесете барем едно поле. ");
    }

    public void checkForEmptyOrganizationCategoryRequest(OrganizationCategoryRequest request) {
        if ((request.name == null || request.name.isEmpty()) && (request.pictureUrl == null || request.pictureUrl.isEmpty()))
            throw new ParameterMissingException("Внесете барем едно поле. ");
    }


    private void checkForEmptyDonorRequest(EditDonorRequest request) {
        if ((request.email == null || request.email.isEmpty()) && (request.password == null || request.password.isEmpty())
                && (request.phone == null || request.phone.isEmpty()) && (request.firstName == null || request.firstName.isEmpty())
                && (request.lastName == null || request.lastName.isEmpty()) && (request.pictureUrl == null || request.pictureUrl.isEmpty()))
            throw new ParameterMissingException("Внесете барем едно поле. ");
    }
}
