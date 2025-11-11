package ru.open.source.service.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.open.source.entity.Profile;
import ru.open.source.mapper.ProfileMapper;
import ru.open.source.repository.ProfileRepository;
import ru.open.source.repository.UserRepository;
import ru.opensource.buildforge.generated.dto.ProfileResponse;
import ru.opensource.buildforge.generated.dto.UpdateProfileDto;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper mapper;

    @Transactional
    @Override
    public void create(UUID userId) {
        profileRepository.validateUserHasNoProfile(userId);
        var user = userRepository.getByIdOrThrow(userId);

        Profile profile = Profile.create(user);
        profileRepository.save(profile);
        log.info("Created profile for user: {}", userId);
    }

    @Transactional
    @Override
    public ProfileResponse update(UUID userId, UpdateProfileDto dto) {
        var profile = profileRepository.getActiveProfileByUserId(userId);
        mapper.updateProfile(profile, dto);
        profileRepository.save(profile);
        log.info("Updated profile for user: {}", userId);
        return mapper.toProfileResponse(profile);
    }

    @Transactional(readOnly = true)
    @Override
    public ProfileResponse get(UUID userId) {
        var profile = profileRepository.getActiveProfileByUserId(userId);
        return mapper.toProfileResponse(profile);
    }

    @Transactional
    @Override
    public void delete(UUID userId) {
        var profile = profileRepository.getActiveProfileByUserId(userId);
        profile.markAsDeleted();
        profileRepository.save(profile);
        log.info("Soft deleted profile for user: {}", userId);
    }

    @Transactional
    @Override
    public void hardDelete(UUID userId) {
        var profile = profileRepository.getByUserIdOrThrow(userId);
        profileRepository.delete(profile);
        log.info("Hard deleted profile for user: {}", userId);
    }

    @Transactional
    @Override
    public void restore(UUID userId) {
        var profile = profileRepository.getDeletedProfileByUserId(userId);
        profile.markAsPending();
        profileRepository.save(profile);
        log.info("Restored profile for user: {}", userId);
    }
}