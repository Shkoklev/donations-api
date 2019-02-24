package com.mk.donations.model;

import javax.persistence.*;

@Entity
@Table(name = "organization_demand")
public class DemandPerOrganization {

    @Id
    @SequenceGenerator(name = "organization_demand_id_seq",
            sequenceName = "organization_demand_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "organization_demand_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organization")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "demand")
    private Demand demand;

    private int quantity;

    public DemandPerOrganization() {}

    public DemandPerOrganization(Organization organization, Demand demand, int quantity) {
        this.organization = organization;
        this.demand = demand;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Demand getDemand() {
        return demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
