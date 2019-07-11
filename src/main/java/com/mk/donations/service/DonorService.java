package com.mk.donations.service;

import com.mk.donations.model.Donation;
import com.mk.donations.model.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface DonorService extends UserDetailsService {

    Page<Donor> getDonorsPage(Pageable pageable);

    UserDetails loadUserByUsername(String email);

    Donor getDonorById(Long id);

    Donor addDonor(Donor donor);

    List<Donor> getAllDonorsOrderedByPoints();

    Donor updateDonor(Long id, String firstName, String lastName, String email, String password, String phone, String pictureUrl);

    void deleteDonor(Long donorId);
}
