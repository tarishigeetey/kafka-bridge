
# Kafka Messaging Library (Kafka-Bridge)

`kafka-bridge` is a reusable Kafka-based Producer-Consumer library for Java microservices. It simplifies Kafka integration by providing easy-to-configure producer and consumer services, as well as automatic topic creation and centralized logging.

## Features

- **Kafka Producer**: Easily publish messages to Kafka topics.
- **Kafka Consumer**: Consume messages from Kafka topics with minimal setup.
- **Dynamic Topic Creation**: Automatic topic creation if topics do not exist.
- **Flexible Configurations**: Allows different configurations for Kafka topics, group IDs, and brokers.
- **AOP Logging**: Centralized logging for producer and consumer message flow using Aspect-Oriented Programming (AOP).
- **Exception Handling**: Built-in error handling for both producer and consumer operations.


## Setup

To use the Kafka Messaging Library, follow these steps:

### 1. Add the Dependency
Include the library in your microservice by adding the following dependency:

```xml
<dependency>
    <groupId>com.yourorg.kafka</groupId>
    <artifactId>kafka-messaging-library</artifactId>
    <version>1.0.0</version>
</dependency>
```
## 2. Configure Kafka Properties

In your `application.properties` or `application.yml`, add the Kafka configurations based on your environment:

**application.properties**:
```propertie
kafka.bootstrap-servers=localhost:9092
kafka.group-id=your-group-id
kafka.topic=your-topic
```
## 3. Implement Producer and Consumer

**Producer Implementation**
The KafkaProducerService allows you to send messages to Kafka topics. Autowire the service in your microservice and use it to send messages:

```
@Autowired
private KafkaProducerService kafkaProducerService;

public void sendMessage(Object message) {
    kafkaProducerService.send("your-topic", message);
}
```
**Consumer Implementation**
To consume messages from a Kafka topic, extend the BaseKafkaConsumerService and implement the processMessage method:

```java
public class MyKafkaConsumerService extends BaseKafkaConsumerService {

    @Override
    protected void processMessage(Object message) {
        // Process the consumed message
        log.info("Message received: {}", message);
    }
}
```
## 4. Error Handling

The Kafka Messaging Library provides a default `KafkaErrorHandler`. You can customize the behavior by overriding its methods to handle errors as per your requirements.

### Example: Custom Kafka Error Handler

You can create your custom error handler by extending the default `KafkaErrorHandler` class and overriding the `handle` method to implement custom error-handling logic.

```java 
public class MyKafkaErrorHandler extends KafkaErrorHandler {

    @Override
    public void handle(Exception e, ConsumerRecord<?, ?> record) {
        // Custom error handling logic
        log.error("Error processing record: {}", record, e);
    }
}
```

## 5. Logging

The library includes AOP-based logging for all producer and consumer operations. You can customize the logging format or levels by configuring the logger in your `logback.xml` or other logging framework configurations.

### Example `logback.xml` Configuration:

```xml
<logger name="com.yourorg.kafka" level="INFO"/>
```

## 6. Testing

For unit testing, you can use embedded Kafka or mock Kafka. The library provides utilities for testing, including the `@EmbeddedKafka` annotation. This allows you to run integration tests without requiring an external Kafka instance.

Example test configuration:

```java
@EmbeddedKafka
@SpringBootTest
public class KafkaIntegrationTest {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Test
    public void testSendMessage() {
        // Send a message to Kafka topic
        kafkaProducerService.send("test-topic", "Sample message");
        
        // Add assertions to validate message processing
        // For example, you can verify message content or behavior
    }
}
```


## Conclusion
The Kafka Messaging Library provides a robust and customizable way to integrate Kafka into your microservices, offering simplified producer and consumer handling, error management, and logging. By adding this library to your microservices, you can avoid rework and ensure consistent Kafka integration across your system.






