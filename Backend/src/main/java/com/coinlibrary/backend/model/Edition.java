package com.coinlibrary.backend.model;

import com.coinlibrary.backend.util.CountryLookUp;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
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

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setYearFrom(int year_from) {
        this.yearFrom = year_from;
    }

    public void setYearTo(int year_to) {
        this.yearTo = year_to;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEditionString() {
        boolean isSpecial = getEdition() == 0;
        String editionString = isSpecial ? "Sondermünzen" : String.format("%d.", getEdition());
        String countryString = CountryLookUp.getInstance()
                .getCountryName(getCountry());

        String year = "";
        if (!isSpecial) {
            if (getYearTo() == 2100 || getYearTo() == 0) {
                if (getYearFrom() == 1800 || getYearFrom() == 0) {
                    year = " ab 1999";
                } else {
                    year = String.format(" ab %d", getYearFrom());
                }
            } else {
                year = String.format(" %d bis %d", getYearFrom(), getYearTo());
            }
        }

        return String.format("%s %s%s", editionString, countryString, year);
    }
}
