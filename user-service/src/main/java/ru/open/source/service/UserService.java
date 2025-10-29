package ru.open.source.service;

import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

import java.util.UUID;

/**
 * Сервис для управления пользователями.
 * Предоставляет методы для создания, обновления и получения информации о пользователях.
 *
 * @author agent
 * @since 28.10.2025
 */
public interface UserService {

    UserResponse create(CreateUserDto createUserDto);

    UserResponse  update(UUID id, UpdateUserDto updateUserDto);

    UserResponse getById(UUID id);

    void delete(UUID id);
}