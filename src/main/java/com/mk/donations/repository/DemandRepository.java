package com.mk.donations.repository;

import com.mk.donations.model.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Long> {

    Optional<Demand> findByName(String name);
    boolean existsByName(String name);

    List<Demand> findByCategoryId(Long id);
}
