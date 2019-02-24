package com.mk.donations.repository;

import com.mk.donations.model.OrganizationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationCategoryRepository extends JpaRepository<OrganizationCategory, Long> {

}
