package ru.open.source.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserCreatedEvent {
    UUID userId;
}
