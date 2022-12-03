package com.coinlibrary.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "coin")
public class Coin {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "edition_id")
    private Edition edition;
    private int year;
    private boolean special;
    @Lob
    private String name;
    private int size;
    private boolean available;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    private String imagePath;

    public Coin() {

    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public String getEditionString() {
        if (edition.getYear_from() == 0 && edition.getYear_to() == 0) {
            return String.format("%d. %s", edition.getEdition(), edition.getCountry());
        } else if (edition.getYear_to() == 0) {
            return String.format("%d. %s (%d)", edition.getEdition(), edition.getCountry(), edition.getYear_from());
        } else {
            return String.format("%d. %s (%d - %d)", edition.getEdition(), edition.getCountry(), edition.getYear_from(), edition.getYear_to());
        }
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
