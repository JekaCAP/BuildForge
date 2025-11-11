package ru.open.source.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.open.source.entity.RoleName;
import ru.open.source.entity.User;
import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;
import ru.opensource.buildforge.generated.dto.UserRole;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

/**
 * Mapper для преобразования между сущностью {@link User} и DTO.
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    /**
     * Преобразует DTO создания юзера в сущность {@link User}.
     */
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target = "builds", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(CreateUserDto userDto);

    /**
     * Обновляет сущность {@link User} на основе DTO обновления.
     */
    @Mapping(target = "profile", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserDto userDto);

    /**
     * Преобразует сущность {@link User} в DTO для отображения.
     */
    @Mapping(target = "createdAt", expression = "java(map(user.getCreatedAt()))")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse toUserResponse(User user);

    default OffsetDateTime map(LocalDateTime value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }

    default LocalDateTime map(OffsetDateTime value) {
        return value != null ? value.toLocalDateTime() : null;
    }

    default List<UserRole> mapRoles(Set<RoleName> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(this::mapRoleToDto)
                .toList();
    }

    default UserRole mapRoleToDto(RoleName role) {
        return switch (role) {
            case ROLE_USER -> UserRole.USER;
            case ROLE_ADMIN -> UserRole.ADMIN;
        };
    }
}
