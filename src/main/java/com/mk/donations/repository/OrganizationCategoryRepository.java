package com.mk.donations.repository;

import com.mk.donations.model.OrganizationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationCategoryRepository extends JpaRepository<OrganizationCategory, Long> {

    Optional<OrganizationCategory> findByName(String name);
}
