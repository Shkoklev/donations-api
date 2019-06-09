package com.mk.donations.model;

import javax.persistence.*;

@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @SequenceGenerator(name = "unit_id_seq",
            sequenceName = "unit_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "unit_id_seq")
    private Long id;

    private String name;

    public Unit() {
    }

    public Unit(String name) {
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
