package com.coinlibrary.backend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @ManyToMany(mappedBy = "coins")
    private Set<Librarian> librarians = new HashSet<>();

    public Coin() {

    }

    public Set<Librarian> getLibrarians() {
        return librarians;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
        librarian.getCoins().add(this);
    }

    public void removeLibrarian(Librarian librarian) {
        librarians.remove(librarian);
        librarian.getCoins().remove(this);
    }
}
