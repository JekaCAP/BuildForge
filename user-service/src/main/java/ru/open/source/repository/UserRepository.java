package ru.open.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.open.source.entity.User;
import ru.open.source.exception.EntityNotFoundException;

import java.util.UUID;

/**
 * UserRepository — описание интерфейса.
 * <p>
 * TODO: описать, какие обязанности реализует интерфейс.
 * </p>
 *
 * @author agent
 * @since 28.10.2025
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    default User getByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id '%s' not found", id)));
    }
}