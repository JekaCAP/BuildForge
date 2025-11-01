package ru.open.source.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.open.source.entity.Profile;
import ru.open.source.entity.ProfileStatus;
import ru.open.source.entity.User;
import ru.open.source.event.UserDeletedEvent;
import ru.open.source.mapper.UserMapper;
import ru.open.source.publisher.UserDeletedEventPublisher;
import ru.open.source.repository.UserRepository;
import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDeletedEventPublisher deletedEventPublisher;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse create(CreateUserDto createUserDto) {
        var user = userMapper.toEntity(createUserDto);

        user.setHashedPassword(passwordEncoder.encode(createUserDto.getPassword()));

        Profile profile = Profile.builder()
                .user(user)
                .avatar(createUserDto.getProfile() != null ? createUserDto.getProfile().getAvatar() : null)
                .bio(createUserDto.getProfile() != null ? createUserDto.getProfile().getBio() : null)
                .setting(new HashMap<>())
                .status(ProfileStatus.PENDING)
                .build();

        user.setProfile(profile);

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse update(UUID id, UpdateUserDto updateUserDto) {
        User user = userRepository.getByIdOrThrow(id);

        userMapper.updateUser(user, updateUserDto);

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(UUID id) {
        var user = userRepository.getByIdOrThrow(id);
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
        deletedEventPublisher.publish(new UserDeletedEvent(id));
    }
}