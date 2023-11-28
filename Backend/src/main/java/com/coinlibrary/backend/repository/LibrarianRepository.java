package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Librarian;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface LibrarianRepository<T, S> extends PagingAndSortingRepository<Librarian, Long>, JpaSpecificationExecutor<Librarian> {
    Librarian save(Librarian librarian);

    Optional<Librarian> findById(long id);

    public Optional<Librarian> findByLibrarianEmail(String librarianEmail);
}
