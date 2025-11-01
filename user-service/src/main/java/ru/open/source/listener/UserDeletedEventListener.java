package ru.open.source.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;
import ru.open.source.event.UserDeletedEvent;
import ru.open.source.service.profile.ProfileServiceImpl;

import java.util.UUID;

/**
 * Kafka consumer для обработки событий удаления пользователя.
 * <p>
 * Подписан на топик, указанный в {@code kafka.topics.users.deleted}.
 * </p>
 *
 * @author mrnght
 * @since 01.11.2025
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDeletedEventListener implements MessageListener<String, String> {

    private final ObjectMapper objectMapper;
    private final ProfileServiceImpl profileService;

    @Override
    @KafkaListener(topics = "${kafka.topics.users.deleted}",
            groupId = "${kafka.group.users.deleted}",
            containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> record) {
        try {
            var event = objectMapper.readValue(record.value(), UserDeletedEvent.class);
            UUID userId = event.getUserId();
            profileService.delete(userId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при десериализации сообщения", e);
        }
    }
}
