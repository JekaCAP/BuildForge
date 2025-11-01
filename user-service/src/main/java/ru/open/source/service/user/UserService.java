package ru.open.source.service.user;

import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

import java.util.UUID;

/**
 * Сервис для управления пользователями.
 * Предоставляет методы для создания, обновления и получения информации о пользователях.
 *
 * @author mrnght
 * @since 30.10.2025
 */
public interface UserService {

    /**
     * Создаёт нового пользователя на основе переданных данных.
     * @param createUserDto объект {@link CreateUserDto}, содержащий информацию для создания пользователя
     * @return объект {@link UserResponse}, представляющий созданного пользователя
     */
    UserResponse create(CreateUserDto createUserDto);

    /**
     * Обновляет пользователя на основе переданных данных.
     * @param id идентификатор обновляемого пользователя
     * @param updateUserDto объект {@link UpdateUserDto}, содержащий информацию для обновления пользователя
     * @return объект {@link UserResponse}, представляющий обновленного пользователя
     */
    UserResponse  update(UUID id, UpdateUserDto updateUserDto);

    /**
     * Находит пользователя по идентификатору
     * @param id уникальный идентификатор
     * @return объект {@link UserResponse}, представляющий пользователя
     */
    UserResponse getById(UUID id);

    /**
     * Удаляет пользователя по идентификатору
     * @param id уникальный идентификатор
     */
    void delete(UUID id);
}