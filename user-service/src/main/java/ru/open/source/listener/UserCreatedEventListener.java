package ru.open.source.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.open.source.event.UserCreatedEvent;
import ru.open.source.service.profile.ProfileServiceImpl;

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

    private final ProfileServiceImpl profileService;

    @KafkaListener(topics = "${kafka.topics.users.created}",
            groupId = "${kafka.group.users.created}",
            containerFactory = "userCreatedListenerFactory")
    public void onMessage(UserCreatedEvent event) {
        try {
            log.info("Received UserCreatedEvent for user: {}", event.userId());
            profileService.create(event.userId());
        } catch (Exception e) {
            log.error("Error processing UserCreatedEvent for user: {}", event.userId(), e);
            throw new RuntimeException("Ошибка при обработке UserCreatedEvent", e);
        }
    }
}