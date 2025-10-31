package ru.open.source.service.profile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.open.source.entity.Profile;
import ru.open.source.entity.ProfileStatus;
import ru.open.source.repository.ProfileRepository;
import ru.open.source.repository.UserRepository;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

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
}
