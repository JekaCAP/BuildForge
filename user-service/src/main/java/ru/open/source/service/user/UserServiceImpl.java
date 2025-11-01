package ru.open.source.service.user;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.open.source.entity.User;
import ru.open.source.mapper.UserMapper;
import ru.open.source.repository.UserRepository;
import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse create(CreateUserDto createUserDto) {

        var user = userMapper.toEntity(createUserDto);

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
    }
}