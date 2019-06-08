package com.mk.donations.model.dto;


import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mk.donations.model.Demand;
import com.mk.donations.model.Quantity;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)
public class OrganizationDemand {


    private Demand demand;

    private Quantity quantity;

    public OrganizationDemand() {}

    public OrganizationDemand(Demand demand, Quantity quantity) {
        this.demand = demand;
        this.quantity = quantity;
    }

    public Demand getDemand() {
        return demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
