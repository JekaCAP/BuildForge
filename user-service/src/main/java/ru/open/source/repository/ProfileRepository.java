package ru.open.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.open.source.entity.Profile;
import ru.open.source.entity.User;
import ru.open.source.exception.EntityNotFoundException;

import java.util.UUID;

/**
 * ProfileRepository — описание интерфейса.
 * <p>
 * Репозиторий основанный на JPA, имеет методы для сохранения, получения, удаления и обновления
 * профилей в БД
 * </p>
 *
 * @author agent
 * @since 01.11.2025
 */
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    default Profile getByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Profile with id '%s' not found", id)));
    }
}
