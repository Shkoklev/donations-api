package com.mk.donations.service.util;

import com.mk.donations.model.exception.EntityAlreadyExistsException;
import com.mk.donations.repository.DonorRepository;
import com.mk.donations.repository.OrganizationRepository;
import org.springframework.stereotype.Component;

@Component
public class PhoneChecker {
    private final OrganizationRepository organizationRepository;
    private final DonorRepository donorRepository;

    public PhoneChecker(OrganizationRepository organizationRepository, DonorRepository donorRepository) {
        this.organizationRepository = organizationRepository;
        this.donorRepository = donorRepository;
    }

    public void checkDuplicatePhone(String phone) {
        boolean phoneExists = donorRepository.existsByPhone(phone)
                || organizationRepository.existsByPhone(phone);
        if(phoneExists)
            throw new EntityAlreadyExistsException("Корисник со телефонски број " + phone + " веќе постои.");
    }
}
