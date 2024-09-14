package com.tg.kafkaconfiglib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaServiceConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    public String getBootstrapServers() {
        return bootstrapServers;
    }
}