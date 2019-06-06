package com.mk.donations.service.impl;

import com.mk.donations.model.Demand;
import com.mk.donations.model.exception.EntityAlreadyExistsException;
import com.mk.donations.model.exception.EntityNotFoundException;
import com.mk.donations.repository.DemandCategoryRepository;
import com.mk.donations.repository.DemandRepository;
import com.mk.donations.service.DemandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandServiceImpl implements DemandService {

    private Logger LOGGER = LoggerFactory.getLogger(DemandServiceImpl.class);

    private final DemandRepository demandRepository;
    private final DemandCategoryRepository demandCategoryRepository;

    public DemandServiceImpl(DemandRepository demandRepository, DemandCategoryRepository demandCategoryRepository) {
        this.demandRepository = demandRepository;
        this.demandCategoryRepository = demandCategoryRepository;
    }

    @Override
    public List<Demand> getAllDemands() {
        return demandRepository.findAll();
    }

    @Override
    public Demand saveDemand(Demand demand) {
        demandRepository.findByName(demand.getName())
                .ifPresent((d) -> {
                    throw new EntityAlreadyExistsException(demand.getName() + " веќе постои.");
                });
        return demandRepository.save(demand);
    }

    @Override
    public void deleteDemandById(Long id) {
        demandRepository.findById(id)
                .map((demand) -> {
                    demandRepository.deleteById(id);
                    return demand;
                })
                .orElseThrow(() -> new EntityNotFoundException("Објект со id : " + id + " не постои"));
    }

    @Override
    public Demand updateDemand(Long id, String name) {
       return demandRepository.findById(id)
                .map((demand) -> {
                    demand.setName(name);
                    return demandRepository.save(demand);
                })
               .orElseThrow(() -> new EntityNotFoundException("Објект со id : " + id + " не постои"));
    }

    @Override
    public void linkDemandToCategory(String demandName, String categoryName) {
        demandRepository.findByName(demandName)
                .map((demand) -> {
                    demandCategoryRepository.findByName(categoryName)
                            .map((category) -> {
                                demand.setCategory(category);
                                return demandRepository.save(demand);
                            })
                            .orElseThrow(() -> new EntityNotFoundException("Категорија" + "  со име : " + categoryName + " не постои."));
                    return demand;
                })
                .orElseThrow(() -> new EntityNotFoundException("Објект со име : " + demandName + " не постои"));
    }


}
