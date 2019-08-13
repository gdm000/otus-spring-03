package edu.otus.spring03;


import edu.otus.spring03.domain.JpaBook;
import edu.otus.spring03.domain.MongoBook;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Collection;

@MessagingGateway
public interface SaverGateway {
    @Gateway(requestChannel = "srcChannel", replyChannel = "resChannel")
    Collection<JpaBook> process(Collection<MongoBook> items);
}
