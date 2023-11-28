package com.coinlibrary.backend.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
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

    public void setLibrarianEmail(String librarianEmail) {
        this.librarianEmail = librarianEmail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
