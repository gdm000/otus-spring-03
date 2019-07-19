package edu.otus.spring03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.channel.MessageChannels;

@Configuration
@IntegrationComponentScan
public class IngegrationConfig {

    @Bean("jpaChannel")
    public PublishSubscribeChannel jpaChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean("mongoChannel")
    public PublishSubscribeChannel mongoChannel() {
        return MessageChannels.publishSubscribe().get();
    }
}
