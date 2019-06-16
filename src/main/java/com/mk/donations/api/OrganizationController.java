package com.mk.donations.api;

import com.mk.donations.model.Donation;
import com.mk.donations.model.Organization;
import com.mk.donations.model.dto.OrganizationDemand;
import com.mk.donations.model.exception.ParameterMissingException;
import com.mk.donations.model.request.AddOrganizationRequest;
import com.mk.donations.model.request.OrganizationDemandRequest;
import com.mk.donations.model.request.EditOrganizationRequest;
import com.mk.donations.model.request.QuantityRequest;
import com.mk.donations.service.DonationsService;
import com.mk.donations.service.OrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin({"*", "localhost:3000"})
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final DonationsService donationsService;

    public OrganizationController(OrganizationService organizationService, DonationsService donationsService) {
        this.organizationService = organizationService;
        this.donationsService = donationsService;
    }

    @GetMapping("/loggedOrganization")
    public Organization getLoggedOrganization(Authentication authentication) {
        Long id = Long.valueOf(authentication.getDetails().toString());
        return organizationService.getOrganizationById(id);
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

    @PostMapping("/register")
    public Organization register(@Valid @RequestBody AddOrganizationRequest addOrganizationRequest) {
        String name = addOrganizationRequest.name;
        String phone = addOrganizationRequest.phone;
        String email = addOrganizationRequest.email;
        String password = addOrganizationRequest.password;
        String category = addOrganizationRequest.category;
        return organizationService.addOrganization(name, phone, email, password, category);
    }

    @PostMapping("/{organizationId}/add_demand")
    public void addDemandToOrganization(@Valid @RequestBody OrganizationDemandRequest organizationDemandRequest, @PathVariable Long organizationId) {
        //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //        if (!(authentication instanceof AnonymousAuthenticationToken)) {
        //              your code here
        //        }
        organizationService.addDemandToOrganization(organizationDemandRequest.demandName,
                organizationId,
                organizationDemandRequest.quantity);
    }

    @PutMapping("/{organizationId}/change_demand_quantity/{demandId}")
    public void changeDemandQuantity(@PathVariable Long organizationId, @PathVariable Long demandId, @Valid @RequestBody QuantityRequest quantityRequest) {
        organizationService.changeExistingDemandQuantity(organizationId, demandId, quantityRequest.quantity);
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

    @GetMapping("/{organizationId}/accept_donation/{donationId}")
    public void acceptDonation(@PathVariable Long donationId) {
        this.donationsService.acceptDonation(donationId);
    }

    @GetMapping("/{organizationId}/decline_donation/{donationId}")
    public void declineDonation(@PathVariable Long donationId) {
        this.donationsService.declineDonation(donationId);
    }

    @GetMapping("/{organizationId}/successful_donations")
    public List<Donation> getSuccessfulDonations(@PathVariable Long organizationId) {
        return donationsService.getSuccessfulDonationsForOrganization(organizationId);
    }

    @GetMapping("/{organizationId}/pending_donations")
    public List<Donation> getPendingDonations(@PathVariable Long organizationId) {
        return donationsService.getPendingDonationsForOrganization(organizationId);
    }

    @GetMapping("/{organizationId}/declined_donations")
    public List<Donation> getDeclinedDonations(@PathVariable Long organizationId) {
        return donationsService.getDeclinedDonationsForOrganization(organizationId);
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
