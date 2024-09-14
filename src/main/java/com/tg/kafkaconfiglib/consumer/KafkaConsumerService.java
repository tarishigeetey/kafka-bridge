package com.tg.kafkaconfiglib.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group-id}")
    public void consumeMessage(Object message) {
        System.out.println("Consumed message: " + message);
    }
}
