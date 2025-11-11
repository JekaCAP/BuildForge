package ru.open.source.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.open.source.event.UserDeletedEvent;
import ru.open.source.service.profile.ProfileServiceImpl;

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
public class UserDeletedEventListener {

    private final ProfileServiceImpl profileService;

    @KafkaListener(topics = "${kafka.topics.users.deleted}",
            groupId = "${kafka.group.users.deleted}",
            containerFactory = "userDeletedListenerFactory")
    public void onMessage(UserDeletedEvent event) {
        try {
            log.info("Received UserDeletedEvent for user: {}", event.userId());
            profileService.delete(event.userId());
        } catch (Exception e) {
            log.error("Error processing UserDeletedEvent for user: {}", event.userId(), e);
            throw new RuntimeException("Ошибка при обработке UserDeletedEvent", e);
        }
    }
}
