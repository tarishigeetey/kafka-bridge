package com.tg.kafkaconfiglib.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(KafkaErrorHandler.class);

    public void handleError(Exception e, String message) {
        logger.error("Error processing message: {}. Error: {}", message, e.getMessage());
        // Add retry or error handling logic here
    }
}

