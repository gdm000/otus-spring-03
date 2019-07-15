package edu.otus.spring03.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public Mongock mongock(MongoProps mongoProps, MongoClient mongoClient, @Value("${changelogs_package}") String changelogsPackage) {
        return new SpringMongockBuilder(mongoClient, mongoProps.getDatabase(), changelogsPackage)
                .setLockQuickConfig()
                .build();
    }
}
