package ru.open.source.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.open.source.entity.User;
import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

/**
 * UserMapper — описание интерфейса.
 * <p>
 * TODO: описать, какие обязанности реализует интерфейс.
 * </p>
 *
 * @author agent
 * @since 28.10.2025
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(CreateUserDto userDto);

    void updateUser(@MappingTarget User user, UpdateUserDto userDto);

    UserResponse toUserResponse(User user);
}