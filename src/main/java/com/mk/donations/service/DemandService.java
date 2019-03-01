package com.mk.donations.service;

import com.mk.donations.model.Demand;

import java.util.List;

public interface DemandService {

    List<Demand> getAllDemands();

    Demand saveDemand(Demand demand);

    void deleteDemandById(Long id);

    Demand updateDemand(Long id, String name);


}
