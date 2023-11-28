package com.coinlibrary.backend.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "librarians")
public class Librarian {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private String librarianEmail;
    @ManyToMany
    @JoinTable(
            name = "librarian_coin",
            joinColumns = @JoinColumn(name = "librarian_id"),
            inverseJoinColumns = @JoinColumn(name = "coin_id")
    )
    private Set<Coin> coins = new HashSet<>();

    public String getLibrarianEmail() {
        return librarianEmail;
    }

    public void setLibrarianEmail(String librarianEmail) {
        this.librarianEmail = librarianEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Set<Coin> getCoins() {
        return coins;
    }

    public void setCoins(Set<Coin> coins) {
        this.coins = coins;
    }

    public void addCoin(Coin coin) {
        coins.add(coin);
        coin.getLibrarians()
                .add(this);
    }

    public void removeCoin(Coin coin) {
        coins.remove(coin);
        coin.getLibrarians()
                .remove(this);
    }
}
