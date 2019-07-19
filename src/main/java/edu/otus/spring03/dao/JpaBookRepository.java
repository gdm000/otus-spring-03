package edu.otus.spring03.dao;

import edu.otus.spring03.domain.JpaBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBookRepository extends CrudRepository<JpaBook, Integer> {
}
