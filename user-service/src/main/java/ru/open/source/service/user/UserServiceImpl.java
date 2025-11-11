package ru.open.source.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.open.source.entity.Profile;
import ru.open.source.entity.RoleName;
import ru.open.source.entity.User;
import ru.open.source.event.UserDeletedEvent;
import ru.open.source.mapper.UserMapper;
import ru.open.source.publisher.UserDeletedEventPublisher;
import ru.open.source.repository.UserRepository;
import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

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
        userRepository.validateEmailNotExists(createUserDto.getEmail());
        userRepository.validateUsernameNotExists(createUserDto.getUsername());

        var user = userMapper.toEntity(createUserDto);
        user.setHashedPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.addRole(RoleName.ROLE_USER);

        Profile profile = Profile.create(
                user,
                createUserDto.getProfile() != null ? createUserDto.getProfile().getAvatar() : null,
                createUserDto.getProfile() != null ? createUserDto.getProfile().getBio() : null
        );

        user.setProfile(profile);
        userRepository.save(user);

        log.info("Created user: {} with email: {}", user.getUsername(), user.getEmail());
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse update(UUID id, UpdateUserDto updateUserDto) {
        User user = userRepository.getByIdOrThrow(id);

        if (updateUserDto.getEmail() != null && !updateUserDto.getEmail().equals(user.getEmail())) {
            userRepository.validateEmailNotExists(updateUserDto.getEmail());
        }

        userMapper.updateUser(user, updateUserDto);
        userRepository.save(user);

        log.info("Updated user: {}", id);
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
        User user = userRepository.getByIdOrThrow(id);
        userRepository.delete(user);
        deletedEventPublisher.publish(new UserDeletedEvent(id));
        log.info("Deleted user: {}", id);
    }
}