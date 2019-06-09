package com.mk.donations.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;

@Entity
@Table(name = "donation")
public class Donation {

    @Id
    @SequenceGenerator(name = "donation_id_seq",
            sequenceName = "donation_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "donation_id_seq")
    private Long id;

    private Double quantity;

    private String status;

    @ManyToOne
    @JoinColumn(name = "organization")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "donor")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "demand")
    private Demand demand;

    @Column(name = "duration")
    private LocalDateTime validUntil;

    public Donation() {
    }

    public Donation(Double quantity, String status, Organization organization, Donor donor, Demand demand) {
        this.quantity = quantity;
        this.status = status;
        this.organization = organization;
        this.donor = donor;
        this.demand = demand;
        this.validUntil = LocalDateTime.now().plusMonths(1);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Demand getDemand() {
        return demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }
}
