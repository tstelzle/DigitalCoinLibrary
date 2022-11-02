package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Edition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditionRepository extends CrudRepository<Edition, Integer> {
}
