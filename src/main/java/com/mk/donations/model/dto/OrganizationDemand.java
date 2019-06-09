package com.mk.donations.model.dto;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mk.donations.model.Demand;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class OrganizationDemand {


    private Demand demand;

    private Double quantity;

    public OrganizationDemand() {
    }

    public OrganizationDemand(Demand demand, Double quantity) {
        this.demand = demand;
        this.quantity = quantity;
    }

    public Demand getDemand() {
        return demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
