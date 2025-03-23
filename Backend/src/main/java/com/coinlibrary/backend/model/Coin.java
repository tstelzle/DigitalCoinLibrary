package com.coinlibrary.backend.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
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
    private boolean special = false;
    @Lob
    @Column(columnDefinition = "TEXT COLLATE utf8mb4_unicode_ci")
    private String name;
    private int size;
    private boolean available;
    @Lob
    @Column(columnDefinition = "TEXT COLLATE utf8mb4_unicode_ci")
    private String imagePath;

    public Coin() {

    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
