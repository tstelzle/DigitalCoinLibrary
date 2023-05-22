package com.coinlibrary.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "edition")
@Embeddable
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(columnDefinition = "TEXT COLLATE utf8mb4_unicode_ci")
    private String country;
    private int edition;
    private int yearFrom;
    private int yearTo;

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

    public int getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(int year_from) {
        this.yearFrom = year_from;
    }

    public int getYearTo() {
        return yearTo;
    }

    public void setYearTo(int year_to) {
        this.yearTo = year_to;
    }

    public int getId() {
        return id;
    }
}
