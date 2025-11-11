package ru.open.source.service.profile;

import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.ProfileResponse;
import ru.opensource.buildforge.generated.dto.UpdateProfileDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

import java.util.UUID;

/**
 * Сервис для управления профилями пользователей.
 * Предоставляет методы для создания, обновления и получения информации о профилях.
 *
 * @author mrnght
 * @since 01.11.2025
 */
public interface ProfileService {

    /**
     * Создаёт новый профиль для пользователя.
     * @param userId объект {@link UUID} - уникальный идентификатор пользователя
     */
    void create(UUID userId);

    /**
     * Находит профиль по идентификатору пользователя
     * @param userId объект {@link UUID} - уникальный идентификатор пользователя
     * @return объект {@link ProfileResponse}, представляющий профиль
     */
    ProfileResponse get(UUID userId);

    /**
     * Обновляет пользователя на основе переданных данных.
     * @param userId идентификатор пользователя, которому принадлежит профиль
     * @param profileDto объект {@link UpdateProfileDto}, содержащий информацию для обновления профиля
     * @return объект {@link ProfileResponse}, представляющий обновленный профиль
     */
    ProfileResponse update(UUID userId, UpdateProfileDto profileDto);

    /**
     * Меняем статус профиля по идентификатору пользователя на удалённый
     * @param userId уникальный идентификатор пользователя
     */
    void delete(UUID userId);

    /**
     * Удаляет профиль по идентификатору пользователя
     * @param userId уникальный идентификатор пользователя
     */
    void hardDelete(UUID userId);

    /**
     * Меняем статус профиля по идентификатору пользователя на активный
     * @param userId уникальный идентификатор пользователя
     */
    void restore(UUID userId);
}
