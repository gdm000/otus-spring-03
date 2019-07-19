package edu.otus.spring03.dao;

import edu.otus.spring03.domain.MongoBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoBookRepository extends MongoRepository<MongoBook, String> {
}
