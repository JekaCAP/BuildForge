package ru.open.source.event;

import java.util.UUID;

public record UserCreatedEvent(UUID userId,
                               String email) {
}
