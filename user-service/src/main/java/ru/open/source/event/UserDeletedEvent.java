package ru.open.source.event;

import java.util.UUID;

public record UserDeletedEvent(UUID userId) {
}
