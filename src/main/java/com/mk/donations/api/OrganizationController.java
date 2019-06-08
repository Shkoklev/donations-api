package com.mk.donations.api;

import com.mk.donations.model.DemandPerOrganization;
import com.mk.donations.model.Organization;
import com.mk.donations.model.Quantity;
import com.mk.donations.model.Unit;
import com.mk.donations.model.dto.OrganizationDemand;
import com.mk.donations.model.exception.ParameterMissingException;
import com.mk.donations.model.request.AddOrganizationRequest;
import com.mk.donations.model.request.OrganizationDemandRequest;
import com.mk.donations.model.request.EditOrganizationRequest;
import com.mk.donations.service.OrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public Page<Organization> getOrganizations(Pageable pageable) {
        return organizationService.getOrganizationsPage(pageable);
    }

    @GetMapping("/{categoryId}")
    public Page<Organization> getOrganizationsPageByCategory(@PathVariable Long categoryId, Pageable pageable) {
        return organizationService.getOrganizationsPageByCategory(pageable, categoryId);
    }

    @GetMapping("/organization/{organizationId}")
    public Organization getOrganizationById(@PathVariable Long organizationId) {
        return organizationService.getOrganizationById(organizationId);
    }

    @GetMapping("/{organizationId}/demands")
    public List<OrganizationDemand> getDemandsForOrganization(@PathVariable Long organizationId) {
        return organizationService.getDemandsForOrganization(organizationId);
    }

    @PostMapping
    public Organization addOrganization(@Valid @RequestBody AddOrganizationRequest addOrganizationRequest) {
        String name = addOrganizationRequest.name;
        String phone = addOrganizationRequest.phone;
        String email = addOrganizationRequest.email;
        String password = addOrganizationRequest.password;
        String category = addOrganizationRequest.category;
        return organizationService.addOrganization(name,phone,email,password,category);
    }

    @PostMapping("/{organizationId}/add_demand")
    public void addDemandToOrganization(@Valid @RequestBody OrganizationDemandRequest organizationDemandRequest, @PathVariable Long organizationId) {
        //        if (!(SecurityContextHolder.getContext().getAuthentication()
        //                instanceof AnonymousAuthenticationToken)) {
        //        }
        Unit unit = Unit.valueOf(organizationDemandRequest.unitName);
        Quantity quantity = new Quantity(organizationDemandRequest.quantity, unit);
        organizationService.addDemandToOrganization(organizationDemandRequest.demandName,
                organizationDemandRequest.demandCategoryName,
                organizationId,
                quantity);
    }

    @PutMapping("/{organizationId}/change_demand_quantity/{demandId}")
    public void changeDemandQuantity(@PathVariable Long organizationId, @PathVariable Long demandId, @Valid @RequestBody Quantity quantity) {
        organizationService.changeExistingDemandQuantity(organizationId, demandId, quantity);
    }

    @DeleteMapping("/{organizationId}/delete_demand/{demandId}")
    public void deleteDemandFromOrganization(@PathVariable Long organizationId, @PathVariable Long demandId) {
        this.organizationService.deleteDemandFromOrganization(demandId, organizationId);
    }

    @PutMapping("/{organizationId}")
    public Organization updateOrganization(@PathVariable Long organizationId, @Valid @RequestBody EditOrganizationRequest request) {
        checkForEmptyRequest(request);
        return organizationService.updateOrganization(organizationId, request.email, request.password, request.phone);

    }

    @DeleteMapping("/{organizationId}")
    public void deleteOrganization(@PathVariable Long organizationId) {
        this.organizationService.deleteOrganization(organizationId);
    }

    public void checkForEmptyRequest(EditOrganizationRequest request) {
        if ((request.email == null || request.email.isEmpty()) && (request.password == null || request.password.isEmpty())
                && (request.phone == null || request.phone.isEmpty()))
            throw new ParameterMissingException("Внесете барем едно поле. ");
    }

    private Organization getActiveOrganization() {
        return (Organization) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
