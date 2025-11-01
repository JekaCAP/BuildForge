package ru.open.source.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.open.source.entity.User;
import ru.open.source.entity.RoleName;
import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper для преобразования между сущностью {@link User} и DTO.
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    /**
     * Преобразует DTO создания юзера в сущность {@link User}.
     */
    @Mapping(target = "profile.id", ignore = true)
    @Mapping(target = "profile.user", ignore = true)
    @Mapping(target = "profile.status", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target = "builds", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(CreateUserDto userDto);

    /**
     * Обновляет сущность {@link User} на основе DTO обновления.
     */
    void updateUser(@MappingTarget User user, UpdateUserDto userDto);

    /**
     * Преобразует сущность {@link User} в DTO для отображения.
     */
    @Mapping(target = "createdAt", expression = "java(map(user.getCreatedAt()))")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse toUserResponse(User user);

    /**
     * Конвертация LocalDateTime → OffsetDateTime
     */
    default OffsetDateTime map(LocalDateTime value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }

    /**
     * Конвертация OffsetDateTime → LocalDateTime
     */
    default LocalDateTime map(OffsetDateTime value) {
        return value != null ? value.toLocalDateTime() : null;
    }

    /**
     * Конвертация коллекции enum RoleName из Set<UserEntity> → List<UserResponse>
     */
    default List<ru.opensource.buildforge.generated.dto.RoleName> mapRoles(Set<RoleName> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(role -> ru.opensource.buildforge.generated.dto.RoleName.valueOf(role.name()))
                .collect(Collectors.toList());
    }
}