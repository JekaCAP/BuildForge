package ru.open.source.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;
import ru.open.source.event.UserCreatedEvent;
import ru.open.source.service.profile.ProfileServiceImpl;

import java.util.UUID;

/**
 * Kafka consumer для обработки событий создания пользователя.
 * <p>
 * Подписан на топик, указанный в {@code kafka.topics.users.created}.
 * </p>
 *
 * @author mrnght
 * @since 01.11.2025
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedEventListener {

    private final ObjectMapper objectMapper;
    private final ProfileServiceImpl profileService;

    @KafkaListener(topics = "${kafka.topics.users.created}",
            groupId = "${kafka.group.users.created}",
            containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> record) {
        try {
            var event = objectMapper.readValue(record.value(), UserCreatedEvent.class);
            UUID userId = event.userId();
            profileService.create(userId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при десериализации сообщения", e);
        }
    }
}