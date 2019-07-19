package edu.otus.spring03.channels;

import edu.otus.spring03.domain.JpaBook;
import edu.otus.spring03.domain.MongoBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataConverter {
    public DataConverter(@Qualifier("mongoChannel") SubscribableChannel inChannel, @Qualifier("jpaChannel") SubscribableChannel outChannel) {
        inChannel.subscribe(message -> {
            MongoBook mongoBook = (MongoBook) message.getPayload();
            JpaBook jpaBook = new JpaBook(mongoBook.getId(), mongoBook.getName(), mongoBook.getGenre(), mongoBook.getAuthor());
            if (outChannel.send(MessageBuilder.withPayload(jpaBook).build(), 5000)) {
                log.info("Converted and sent message, was: {}, is: {}", mongoBook, jpaBook);
            } else {
                log.info("Failed to send message: {}", jpaBook);
            }
        });
    }
}
