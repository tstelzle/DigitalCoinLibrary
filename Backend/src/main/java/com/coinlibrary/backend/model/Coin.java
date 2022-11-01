package com.coinlibrary.backend.model;

import com.coinlibrary.backend.enums.CoinSize;
import com.coinlibrary.backend.enums.Country;

import javax.persistence.*;

@Entity
@Table(name = "coin")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Country country;
    private int year;
    private boolean special;
    private String name;
    private CoinSize size;

    public Coin(String country, int year, String size) {
        this.country = Country.valueOf(country);
        this.year = year;
        this.size = CoinSize.valueOf(size);
        this.special = false;
        this.name = "";
    }

    public Coin(String country, int year, String size, boolean special, String name) {
        this.country = Country.valueOf(country);
        this.year = year;
        this.size = CoinSize.valueOf(size);
        this.special = special;
        this.name = name;
    }

    public Coin() {

    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CoinSize getSize() {
        return size;
    }

    public void setSize(CoinSize size) {
        this.size = size;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
