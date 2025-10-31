package ru.open.source.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.open.source.event.UserCreatedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreatedEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value(value = "${kafka.topics.follower}")
    private String topicName;

    @Async
    @Retryable(retryFor = {TimeoutException.class, SerializationException.class, AuthorizationException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void publish(UserCreatedEvent event) {
        kafkaTemplate.send(topicName, event);
    }
}
