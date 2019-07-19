package edu.otus.spring03.channels;

import edu.otus.spring03.dao.JpaBookRepository;
import edu.otus.spring03.domain.JpaBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class JpaWriter {
    private final JpaBookRepository bookRepository;
    private final SubscribableChannel channel;

    public JpaWriter(JpaBookRepository bookRepository, @Qualifier("jpaChannel") SubscribableChannel channel) {
        this.bookRepository = bookRepository;
        this.channel = channel;
    }

    @PostConstruct
    public void  initRead() {
        channel.subscribe(message -> {bookRepository.save((JpaBook) message.getPayload());
        log.info("Saved message: {}", message.getPayload());
        });
    }
}
