package edu.otus.spring03;

import edu.otus.spring03.dao.MongoBookRepository;
import edu.otus.spring03.domain.MongoBook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        DirectChannel outputChannel = ctx.getBean("outputChannel", DirectChannel.class);
        outputChannel.subscribe(x -> System.out.println("Saved: "+x));

        DtoConsumer consumer = ctx.getBean(DtoConsumer.class);
        MongoBookRepository mongoBookRepository = ctx.getBean(MongoBookRepository.class);
        readAll(mongoBookRepository, consumer);

        ctx.close();
    }

    private static void readAll(MongoBookRepository repository, DtoConsumer consumer) {
        final int pageSize = 10;
        int counter = 0;
        do {
            Pageable pageable = PageRequest.of(counter++, pageSize, Sort.by("id"));
            Page<MongoBook> page = repository.findAll(pageable);
            if (!page.hasContent()) {
                break;
            }
            page.getContent().stream().forEach(dto -> consumer.process(dto));
        } while (true);
    }

    @Bean
    DirectChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow dtoFlow() {
        return flow -> flow
                .handle("dataConverter", "convert")
                .handle("jpaBookRepository", "save")
                .channel("outputChannel");
    }


    @MessagingGateway
    public interface DtoConsumer {
        @Gateway(requestChannel = "dtoFlow.input")
        void process(MongoBook mongoBook);
    }
}
