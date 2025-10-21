package ru.open.source.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность UserBuild представляет сборку (build) пользователя в системе BuildForge.
 * <p>
 * Каждая сборка принадлежит конкретному пользователю и хранится с уникальным идентификатором.
 * Сборка может быть связана с игрой или конкретным билд-контентом через поле {@code buildId}.
 * </p>
 * <p>
 * Поля класса:
 * <ul>
 *     <li>{@code user} — пользователь, которому принадлежит сборка (связь многие к одному).</li>
 *     <li>{@code buildId} — идентификатор сборки, например, в контексте определённой игры или базы предметов.</li>
 *     <li>{@code createdAt} — дата и время создания записи сборки.</li>
 * </ul>
 * </p>
 * <p>
 * Используется с JPA/Hibernate для маппинга на таблицу {@code user_builds}.
 * Метод {@code @PrePersist} автоматически устанавливает дату создания.
 * </p>
 *
 * <p>Связи с другими сущностями:</p>
 * <ul>
 *     <li>{@link User} — каждый UserBuild связан с одним пользователем.</li>
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
@Table(name = "user_builds")
public class UserBuild {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private UUID buildId;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}