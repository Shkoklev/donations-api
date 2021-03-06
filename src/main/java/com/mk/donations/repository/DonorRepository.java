package com.mk.donations.repository;

import com.mk.donations.model.Admin;
import com.mk.donations.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Donor> findByEmail(String email);

    List<Donor> findAllByOrderByPointsDesc();
}
