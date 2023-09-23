package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Librarian;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface LibrarianRepository<T, S> extends PagingAndSortingRepository<Librarian, Long>, JpaSpecificationExecutor<Librarian> {

    public Optional<Librarian> findByLibrarianEmail(String librarianEmail);
}
