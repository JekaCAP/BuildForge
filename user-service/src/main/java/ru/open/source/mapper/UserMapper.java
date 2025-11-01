package ru.open.source.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.open.source.entity.User;
import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

/**
 * Mapper для преобразования между сущностью {@link User} и DTO.
 * <p>
 * Предоставляет методы для конвертации данных при создании, обновлении и отображении пользователей.
 * </p>
 *
 * @author JekaCAP
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Преобразует DTO создания юзера в сущность {@link User}.
     */
    User toEntity(CreateUserDto userDto);

    /**
     * Преобразует сущность {@link User} в DTO для отображения.
     */
    void updateUser(@MappingTarget User user, UpdateUserDto userDto);

    /**
     * Обновляет сущность {@link User} на основе данных из DTO обновления.
     */
    UserResponse toUserResponse(User user);
}