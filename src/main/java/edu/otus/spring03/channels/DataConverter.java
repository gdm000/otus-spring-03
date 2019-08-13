package edu.otus.spring03.channels;

import edu.otus.spring03.domain.JpaBook;
import edu.otus.spring03.domain.MongoBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataConverter {
    public JpaBook convert(MongoBook mongoBook) {
        log.info("Converting book: {}", mongoBook);
        return new JpaBook(mongoBook.getId(), mongoBook.getName(), mongoBook.getGenre(), mongoBook.getAuthor());
    }
}
