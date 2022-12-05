package com.coinlibrary.backend.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "edition")
@Embeddable
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String country;
    private int edition;
    private int year_from;
    private int year_to;

    public Edition() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public int getYear_from() {
        return year_from;
    }

    public void setYear_from(int year_from) {
        this.year_from = year_from;
    }

    public int getYear_to() {
        return year_to;
    }

    public void setYear_to(int year_to) {
        this.year_to = year_to;
    }
}
