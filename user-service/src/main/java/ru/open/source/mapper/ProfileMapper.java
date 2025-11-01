package ru.open.source.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.open.source.entity.Profile;
import ru.opensource.buildforge.generated.dto.CreateProfileDto;
import ru.opensource.buildforge.generated.dto.ProfileResponse;
import ru.opensource.buildforge.generated.dto.UpdateProfileDto;

/**
 * Mapper для преобразования между сущностью {@link Profile} и DTO.
 * <p>
 * Предоставляет методы для конвертации данных при создании, обновлении и отображении профилей.
 * </p>
 *
 * @author mrnght
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProfileMapper {
    /**
     * Преобразует DTO создания профиля в сущность {@link Profile}.
     */
    Profile toEntity(CreateProfileDto profileDto);

    /**
     * Преобразует сущность {@link Profile} в DTO для отображения.
     */
    void updateProfile(@MappingTarget Profile profile, UpdateProfileDto profileDto);

    /**
     * Обновляет сущность {@link Profile} на основе данных из DTO обновления.
     */
    ProfileResponse toProfileResponse(Profile profile);
}
