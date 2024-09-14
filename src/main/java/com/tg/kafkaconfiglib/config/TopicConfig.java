package com.tg.kafkaconfiglib.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class TopicConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("#{'${kafka.topics}'.split(',')}")
    private List<String> topics;

    @Value("${kafka.topic.num-partitions:1}")
    private int numPartitions;

    @Value("${kafka.topic.replication-factor:1}")
    private short replicationFactor;

    @Bean
    KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers
        );
        return new KafkaAdmin(config);
    }

    @Bean
    List<NewTopic> createTopics() {
        return topics.stream()
                .map(topic -> new NewTopic(topic.trim(), numPartitions, replicationFactor))
                .collect(Collectors.toList());
    }
}
