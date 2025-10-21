package ru.open.source.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Сущность User представляет пользователя системы BuildForge.
 * <p>
 * Содержит основную информацию о пользователе, включая учетные данные,
 * роли, дату создания и обновления, а также связи с профилем и пользовательскими сборками.
 * </p>
 * <p>
 * Поля класса:
 * <ul>
 *     <li>{@code username} — уникальное имя пользователя для логина.</li>
 *     <li>{@code email} — уникальный email, используется для идентификации и восстановления пароля.</li>
 *     <li>{@code hashedPassword} — хэш пароля пользователя, хранится безопасно.</li>
 *     <li>{@code createdAt} — дата и время создания записи пользователя.</li>
 *     <li>{@code updatedAt} — дата и время последнего обновления записи.</li>
 *     <li>{@code profile} — профиль пользователя (1 к 1), хранит дополнительные данные, например аватар и настройки.</li>
 *     <li>{@code builds} — набор сборок пользователя (1 ко многим), хранит информацию о созданных билдах.</li>
 *     <li>{@code roles} — роли пользователя ({@link RoleName}), определяют права доступа.</li>
 * </ul>
 * </p>
 * <p>
 * Используется с JPA/Hibernate для маппинга на таблицу {@code users}.
 * Методы {@code @PrePersist} и {@code @PreUpdate} автоматически устанавливают
 * и обновляют значения {@code createdAt} и {@code updatedAt}.
 * </p>
 *
 * <p>Связи с другими сущностями:</p>
 * <ul>
 *     <li>{@link Profile} — связь один к одному через поле {@code profile}.</li>
 *     <li>{@link UserBuild} — связь один ко многим через поле {@code builds}.</li>
 * </ul>
 *
 * @author agent
 * @since 21.10.2025
 */
@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserBuild> builds = new HashSet<>();

    @ElementCollection(targetClass = RoleName.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<RoleName> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}