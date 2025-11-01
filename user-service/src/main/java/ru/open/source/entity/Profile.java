package ru.open.source.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String avatar;

    private String bio;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> setting = new HashMap<>();

    @Enumerated(EnumType.STRING)
    private ProfileStatus status = ProfileStatus.PENDING;
}