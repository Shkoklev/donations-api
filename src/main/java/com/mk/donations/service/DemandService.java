package com.mk.donations.service;

import com.mk.donations.model.Demand;

import java.util.List;

public interface DemandService {

    List<Demand> getAllDemands();

    Demand saveDemand(String name, String category, String unitName);

    void deleteDemandById(Long id);

    Demand updateDemand(Long id, String name);

    void linkDemandToCategory(String demandName, String categoryName);


}
