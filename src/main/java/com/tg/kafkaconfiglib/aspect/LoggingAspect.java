package com.tg.kafkaconfiglib.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Aspect
@Component
public class LoggingAspect {

    // Pointcut for all methods in the producer package
    @Pointcut("execution(* com.tg.kafkaconfiglib.producer.*.*(..))")
    public void producerMethods() {}

    // Pointcut for all methods in the consumer package
    @Pointcut("execution(* com.tg.kafkaconfiglib.consumer.*.*(..))")
    public void consumerMethods() {}

    // Advice before producer and consumer methods execution
    @Before("producerMethods() || consumerMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("Executing: " + joinPoint.getSignature().getName() + " with arguments: " + joinPoint.getArgs());
    }

    // Advice after producer and consumer methods execution
    @AfterReturning(pointcut = "producerMethods() || consumerMethods()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        System.out.println("Executed: " + joinPoint.getSignature().getName() + " with result: " + result);
    }

 // Pointcut for Kafka producer's send method to log messages
    @Pointcut("execution(* com.tg.kafkaconfiglib.producer.KafkaProducerService.sendMessage(..))")
    public void kafkaMessageSending() {}

    // Log the result of sending Kafka messages
    @AfterReturning(pointcut = "kafkaMessageSending()", returning = "result")
    public void logProducerMessage(JoinPoint joinPoint, CompletableFuture<SendResult<String, String>> result) {
        result.thenAccept(sendResult -> {
            System.out.println("Message sent successfully to topic: " + sendResult.getRecordMetadata().topic() +
                    " partition: " + sendResult.getRecordMetadata().partition() +
                    " offset: " + sendResult.getRecordMetadata().offset());
        }).exceptionally(ex -> {
            System.err.println("Failed to send message due to: " + ex.getMessage());
            return null;
        });
    }

    
    // Specific pointcut for the consumer service's message consumption
    @Pointcut("execution(* com.tg.kafkaconfiglib.consumer.KafkaConsumerService.consumeMessage(..))")
    public void kafkaMessageConsumption() {}

    // Log details of the consumed Kafka message after the consumer method returns
    @AfterReturning("kafkaMessageConsumption()")
    public void logKafkaMessage(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Message<?> message = (Message<?>) args[0];
        
        System.out.println("Consumed message: " + message.getPayload());
        System.out.println("From topic: " + message.getHeaders().get(KafkaHeaders.RECEIVED_TOPIC));
        System.out.println("Partition: " + message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION));
        System.out.println("Offset: " + message.getHeaders().get(KafkaHeaders.OFFSET));
    }
}
