package edu.otus.spring03.service;

import edu.otus.spring03.dao.MongoBookRepository;
import edu.otus.spring03.domain.MongoBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final MongoBookRepository mongoBookRepository;

    @Transactional
    @Override
    public <T> List<T> getBooks(Function<MongoBook, T> mapper) {
        return StreamSupport.stream(mongoBookRepository.findAll().spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public <T> Optional<T> getBook(String id, Function<MongoBook, T> mapper) {
        return mongoBookRepository.findById(id).map(mapper);
    }

    @Transactional
    @Override
    public String createBook(String name, String author, String genreId) {
        return mongoBookRepository.save(
                MongoBook.builder()
                        .name(name)
                        .author(author)
                        .genre(genreId)
                        .build()).getId();
    }


    @Override
    public <T> T updateBook(String bookId, String name, String author, String genre, Function<MongoBook, T> mapper) {
        MongoBook mongoBook = mongoBookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        mongoBook.setName(name);
        mongoBook.setAuthor(author);
        mongoBook.setGenre(genre);
        return mapper.apply(mongoBookRepository.save(mongoBook));
    }

    @Override
    public void deleteBook(String bookId) {
        mongoBookRepository.deleteById(bookId);
    }
}
