package ru.open.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.open.source.entity.User;
import ru.open.source.exception.DataValidationException;
import ru.open.source.exception.EntityNotFoundException;

import java.util.UUID;

/**
 * UserRepository — описание интерфейса.
 * <p>
 * Репозиторий основанный на JPA, имеет методы для сохранения, получения, удаления и обновления
 * пользователей в БД
 * </p>
 *
 * @author mrnght
 * @since 31.10.2025
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    default User getByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id '%s' not found".formatted(id)));
    }

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    default void validateEmailNotExists(String email) {
        if (existsByEmail(email)) {
            throw new DataValidationException("User with email '%s' already exists".formatted(email));
        }
    }

    default void validateUsernameNotExists(String username) {
        if (existsByUsername(username)) {
            throw new DataValidationException("User with username '%s' already exists".formatted(username));
        }
    }
}