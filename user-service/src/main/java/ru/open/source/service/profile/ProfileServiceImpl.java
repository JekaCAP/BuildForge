package ru.open.source.service.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.open.source.entity.Profile;
import ru.open.source.entity.ProfileStatus;
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
        var user = userRepository.getByIdOrThrow(userId);
        Profile profile = Profile.builder()
                .user(user)
                .status(ProfileStatus.PENDING)
                .build();
        profileRepository.save(profile);
    }

    @Transactional
    @Override
    public ProfileResponse update(UUID userId, UpdateProfileDto profileDto) {
        var user = userRepository.getByIdOrThrow(userId);
        var profile = user.getProfile();

        mapper.updateProfile(profile, profileDto);
        return mapper.toProfileResponse(profile);
    }

    @Transactional(readOnly = true)
    @Override
    public ProfileResponse get(UUID userId) {
        var profile = userRepository.getByIdOrThrow(userId).getProfile();
        return mapper.toProfileResponse(profile);
    }

    @Transactional
    @Override
    public void delete(UUID userId) {
        var profile = userRepository.getByIdOrThrow(userId).getProfile();
        profileRepository.delete(profile);
        log.info("Удален профиль с id: {}", profile.getId());
    }
}
