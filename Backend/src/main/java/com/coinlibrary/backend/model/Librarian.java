package com.coinlibrary.backend.model;

import javax.persistence.*;
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
    private String librarianName;
    @ManyToMany
    @JoinTable(
            name = "librarian_coin",
            joinColumns = @JoinColumn(name = "librarian_id"),
            inverseJoinColumns = @JoinColumn(name = "coin_id")
    )
    private Set<Coin> coins = new HashSet<>();
}
