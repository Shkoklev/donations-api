package com.mk.donations.repository;

import com.mk.donations.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    boolean existsByEmail(String email);
    Optional<Organization> findByEmail(String email);
    Optional<Organization> findByPhone(String phone);
    Optional<Organization> findByName(String name);

    Page<Organization> findAllByOrganizationCategory_Name(Pageable pageable, Long categoryId);
}
