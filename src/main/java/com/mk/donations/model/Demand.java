package com.mk.donations.model;

import javax.persistence.*;

@Entity
@Table(name = "demand")
public class Demand {

    @Id
    @SequenceGenerator(name = "demand_id_seq1",
                       sequenceName = "demand_id_seq1",
                       allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "demand_id_seq1")
    private Long id;

    private String name;

    public Demand() {}

    public Demand(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
