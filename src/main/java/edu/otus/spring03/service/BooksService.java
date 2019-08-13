package edu.otus.spring03.service;

import edu.otus.spring03.domain.MongoBook;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface BooksService {
    <T> List<T> getBooks(Function<MongoBook, T> mapper);
    <T> Optional<T> getBook(String id, Function<MongoBook, T> mapper);
    String createBook(String name, String author, String genre);
    <T> T updateBook(String id, String name, String author, String genre, Function<MongoBook, T> mapper);
    void deleteBook(String bookId);
}
