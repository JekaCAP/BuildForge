package ru.open.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.open.source.entity.User;
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
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id '%s' not found", id)));
    }

    UUID id(UUID id);
}