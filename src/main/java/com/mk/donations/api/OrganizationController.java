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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/search")
    public List<Organization> getOrganizations(@RequestParam(value = "query", required = false) String query) {
        return organizationService.getOrganizationsByQuery(query);
    }

    @GetMapping
    public Page<Organization> getOrganizations(@PageableDefault(value = 8) Pageable pageable) {
        return organizationService.getOrganizationsPage(pageable);
    }

    @GetMapping("/{categoryId}")
    public Page<Organization> getOrganizationsPageByCategory(@PathVariable Long categoryId, @PageableDefault(value = 8) Pageable pageable) {
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
    public ResponseEntity<Void> addDemandToOrganization(@Valid @RequestBody OrganizationDemandRequest organizationDemandRequest,
                                                        Authentication authentication,
                                                        @PathVariable Long organizationId) {
        Long id = Long.valueOf(authentication.getDetails().toString());
        if (id != organizationId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        organizationService.addDemandToOrganization(organizationDemandRequest.demandName,
                id,
                organizationDemandRequest.quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{organizationId}/change_demand_quantity/{demandId}")
    public void changeDemandQuantity(@PathVariable Long organizationId, @PathVariable Long demandId, @Valid @RequestBody QuantityRequest quantityRequest) {
        organizationService.changeExistingDemandQuantity(organizationId, demandId, quantityRequest.quantity);
    }

    @DeleteMapping("/{organizationId}/delete_demand/{demandId}")
    public ResponseEntity<Void> deleteDemandFromOrganization(Authentication authentication, @PathVariable Long organizationId, @PathVariable Long demandId) {
        Long id = Long.valueOf(authentication.getDetails().toString());
        if (id != organizationId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        this.organizationService.deleteDemandFromOrganization(demandId, organizationId);
        return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<Void> acceptDonation(@PathVariable Long organizationId, @PathVariable Long donationId, Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != organizationId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        this.donationsService.acceptDonation(donationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{organizationId}/decline_donation/{donationId}")
    public ResponseEntity<Void> declineDonation(@PathVariable Long organizationId, @PathVariable Long donationId, Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != organizationId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        this.donationsService.declineDonation(donationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{organizationId}/successful_donations")
    public ResponseEntity<List<Donation>> getSuccessfulDonations(@PathVariable Long organizationId, Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != organizationId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Donation> donations = donationsService.getSuccessfulDonationsForOrganization(organizationId);
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }

    @GetMapping("/{organizationId}/pending_donations")
    public ResponseEntity<List<Donation>> getPendingDonations(@PathVariable Long organizationId, Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != organizationId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Donation> donations = donationsService.getPendingDonationsForOrganization(organizationId);
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }

    @GetMapping("/{organizationId}/declined_donations")
    public ResponseEntity<List<Donation>> getDeclinedDonations(@PathVariable Long organizationId, Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != organizationId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Donation> donations = donationsService.getDeclinedDonationsForOrganization(organizationId);
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }

    public void checkForEmptyRequest(EditOrganizationRequest request) {
        if ((request.email == null || request.email.isEmpty()) && (request.password == null || request.password.isEmpty())
                && (request.phone == null || request.phone.isEmpty()))
            throw new ParameterMissingException("Внесете барем едно поле. ");
    }
}
