package com.mk.donations.service.impl;

import com.mk.donations.model.Donor;
import com.mk.donations.model.exception.EntityAlreadyExistsException;
import com.mk.donations.model.exception.EntityNotFoundException;
import com.mk.donations.repository.DonorRepository;
import com.mk.donations.service.DonorService;
import com.mk.donations.service.util.EmailChecker;
import com.mk.donations.service.util.PhoneChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class DonorServiceImpl implements DonorService {

    private Logger LOGGER = LoggerFactory.getLogger(DonorServiceImpl.class);

    private List<String> PICTURE_URLS = Arrays.asList(
            "https://image.flaticon.com/icons/svg/149/149071.svg",
            "https://image.flaticon.com/icons/svg/149/149995.svg",
            "https://image.flaticon.com/icons/svg/149/149452.svg"
    );


    private final DonorRepository donorRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailChecker emailChecker;
    private final PhoneChecker phoneChecker;

    public DonorServiceImpl(DonorRepository donorRepository, PasswordEncoder passwordEncoder,
                            EmailChecker emailChecker, PhoneChecker phoneChecker) {
        this.donorRepository = donorRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailChecker = emailChecker;
        this.phoneChecker = phoneChecker;
    }

    @Override
    public Page<Donor> getDonorsPage(Pageable pageable) {
        return donorRepository.findAll(pageable);
    }

    @Override
    public List<Donor> getAllDonorsOrderedByPoints() {
        return this.donorRepository.findAllByOrderByPointsDesc();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return donorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Лице со id  " + id + " не постои."));
    }

    @Override
    public Donor addDonor(Donor donor) {
        Random ran = new Random();
        checkDonorExistence(donor.getEmail(), donor.getPhone());
        donor.setPassword(passwordEncoder.encode(donor.getPassword()));
        int randomIndex = ran.nextInt(3);
        donor.setPictureUrl(PICTURE_URLS.get(randomIndex));
        return donorRepository.save(donor);
    }

    @Override
    public Donor updateDonor(Long id, String firstName, String lastName, String email, String password, String phone, String pictureUrl) {
        emailChecker.checkDuplicateEmail(email);
        phoneChecker.checkDuplicatePhone(phone);
        return donorRepository.findById(id)
                .map((donor) -> {
                    if (firstName != null && !firstName.isEmpty())
                        donor.setFirstName(firstName);
                    if (lastName != null && !lastName.isEmpty())
                        donor.setLastName(lastName);
                    if (email != null && !email.isEmpty())
                        donor.setEmail(email);
                    if (password != null && !password.isEmpty())
                        donor.setPassword(passwordEncoder.encode(password));
                    if (phone != null && !phone.isEmpty())
                        donor.setPhone(phone);
                    if (pictureUrl != null && !pictureUrl.isEmpty())
                        donor.setPictureUrl(pictureUrl);
                    return donorRepository.save(donor);
                })
                .orElseThrow(() -> new EntityNotFoundException("Корисник" + "  со id : " + id + " не постои."));
    }

    @Override
    public void deleteDonor(Long donorId) {
        donorRepository.findById(donorId)
                .map((donor) -> {
                    donorRepository.deleteById(donorId);
                    return donor;
                })
                .orElseThrow(() -> new EntityNotFoundException("Лице" + "  со id : " + donorId + " не постои."));
    }

    public void checkDonorExistence(String email, String phone) {
        emailChecker.checkDuplicateEmail(email);
        phoneChecker.checkDuplicatePhone(phone);
    }
}
