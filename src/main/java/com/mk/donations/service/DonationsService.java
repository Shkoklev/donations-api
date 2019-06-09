package com.mk.donations.service;

import com.mk.donations.model.Donation;

import java.util.List;

public interface DonationsService {

    Donation donate(Long donorId, Long organizationId, Long demandId, Double quantity);

    void acceptDonation(Long donationId);

    void declineDonation(Long donationId);

    List<Donation> getPendingDonationsForOrganization(Long organizationId);

    List<Donation> getSuccessfulDonationsForOrganization(Long organizationId);

    void removeDonation(Long donationId);


}
