package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Edition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditionDao extends CrudRepository<Edition, Integer>, EditionRepository<Edition, Integer> {
}
