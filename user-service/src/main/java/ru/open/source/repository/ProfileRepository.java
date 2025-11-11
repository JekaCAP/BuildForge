package ru.open.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.open.source.entity.Profile;
import ru.open.source.entity.ProfileStatus;
import ru.open.source.exception.EntityNotFoundException;
import ru.open.source.exception.ProfileValidationException;

import java.util.List;
import java.util.Optional;
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

    Optional<Profile> findByUserId(UUID userId);

    List<Profile> findByStatus(ProfileStatus status);

    default Profile getByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile with id '%s' not found".formatted(id)));
    }

    default Profile getByUserIdOrThrow(UUID userId) {
        return findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Profile for user '%s' not found".formatted(userId)));
    }

    default Profile getActiveProfileByUserId(UUID userId) {
        Profile profile = getByUserIdOrThrow(userId);
        if (profile.isDeleted()) {
            throw new ProfileValidationException("Profile for user '%s' is deleted".formatted(userId));
        }
        return profile;
    }

    default Profile getDeletedProfileByUserId(UUID userId) {
        Profile profile = getByUserIdOrThrow(userId);
        if (!profile.isDeleted()) {
            throw new ProfileValidationException("Profile for user '%s' is not deleted".formatted(userId));
        }
        return profile;
    }

    default void validateUserHasNoProfile(UUID userId) {
        if (findByUserId(userId).isPresent()) {
            throw new ProfileValidationException("User '%s' already has a profile".formatted(userId));
        }
    }

    default boolean existsActiveByUserId(UUID userId) {
        return findByUserId(userId)
                .map(profile -> !profile.isDeleted())
                .orElse(false);
    }
}