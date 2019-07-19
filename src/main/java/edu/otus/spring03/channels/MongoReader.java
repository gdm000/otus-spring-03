package edu.otus.spring03.channels;

import edu.otus.spring03.dao.MongoBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MongoReader {
    private final MessageChannel output;
    private final MongoBookRepository bookRepository;

    public MongoReader(@Qualifier("mongoChannel") MessageChannel output, MongoBookRepository bookRepository) {
        this.output = output;
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void readAll() {
        bookRepository.findAll().stream().forEach(mongoBook -> {
            if (output.send(MessageBuilder.withPayload(mongoBook).build(), 5000)) {
                log.info("Sent message: {}", mongoBook);
            } else {
                log.warn("Failed to send message: {}", mongoBook);
            }});
    }
}
