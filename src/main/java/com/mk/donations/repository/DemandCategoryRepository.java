package com.mk.donations.repository;

import com.mk.donations.model.DemandCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DemandCategoryRepository extends JpaRepository<DemandCategory, Long> {

    Optional<DemandCategory> findByName(String name);
}
