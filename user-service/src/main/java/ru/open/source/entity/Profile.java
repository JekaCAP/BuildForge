package ru.open.source.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Сущность Profile представляет профиль пользователя в системе BuildForge.
 * <p>
 * Каждый профиль связан с одним пользователем ({@link User}) и содержит информацию,
 * необходимую для отображения и настройки аккаунта.
 * </p>
 * <p>
 * Поля профиля:
 * <ul>
 *     <li>{@code user} — ссылка на пользователя, к которому относится профиль.</li>
 *     <li>{@code avatar} — URL или путь к аватару пользователя.</li>
 *     <li>{@code bio} — краткая биография или описание пользователя.</li>
 *     <li>{@code setting} — JSONB-колонка для хранения настроек профиля в формате JSON.</li>
 *     <li>{@code status} — статус профиля в системе.</li>
 * </ul>
 * </p>
 * <p>
 * Используется с JPA/Hibernate для маппинга на таблицу {@code profiles} в базе данных.
 * </p>
 *
 * @author agent
 * @since 21.10.2025
 */
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String avatar;

    private String bio;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @Builder.Default
    private Map<String, Object> setting = new HashMap<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ProfileStatus status = ProfileStatus.PENDING;

    public void markAsDeleted() {
        this.status = ProfileStatus.DELETED;
    }

    public void markAsPending() {
        this.status = ProfileStatus.PENDING;
    }

    public void markAsValid() {
        this.status = ProfileStatus.VALID;
    }

    public void markAsInvalid() {
        this.status = ProfileStatus.INVALID;
    }

    public boolean isDeleted() {
        return this.status == ProfileStatus.DELETED;
    }

    public boolean isActive() {
        return this.status == ProfileStatus.VALID || this.status == ProfileStatus.PENDING;
    }

    public boolean isValid() {
        return this.status == ProfileStatus.VALID;
    }

    public boolean isPending() {
        return this.status == ProfileStatus.PENDING;
    }

    public void updateAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }

    public void updateSettings(Map<String, Object> settings) {
        this.setting = settings != null ? new HashMap<>(settings) : new HashMap<>();
    }

    public void mergeSettings(Map<String, Object> newSettings) {
        if (newSettings != null) {
            if (this.setting == null) {
                this.setting = new HashMap<>();
            }
            this.setting.putAll(newSettings);
        }
    }

    public static Profile create(User user) {
        return Profile.builder()
                .user(user)
                .avatar(null)
                .bio(null)
                .setting(new HashMap<>())
                .status(ProfileStatus.PENDING)
                .build();
    }

    public static Profile create(User user, String avatar, String bio) {
        return Profile.builder()
                .user(user)
                .avatar(avatar)
                .bio(bio)
                .setting(new HashMap<>())
                .status(ProfileStatus.PENDING)
                .build();
    }
}