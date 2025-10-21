package ru.open.source.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String avatar;

    private String bio;

    @Column(columnDefinition = "jsonb")
    private String setting;
}