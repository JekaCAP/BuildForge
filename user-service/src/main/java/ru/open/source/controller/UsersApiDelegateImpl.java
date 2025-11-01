package ru.open.source.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.open.source.service.user.UserService;
import ru.opensource.buildforge.generated.api.UsersApiDelegate;
import ru.opensource.buildforge.generated.dto.CreateUserDto;
import ru.opensource.buildforge.generated.dto.UpdateUserDto;
import ru.opensource.buildforge.generated.dto.UserResponse;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersApiDelegateImpl implements UsersApiDelegate {

    private final UserService service;

    @Override
    public ResponseEntity<UserResponse> userApiCreateUser(CreateUserDto createUserDto) {
        log.info("Create user: {}", createUserDto.getUsername());
        var response = service.create(createUserDto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserResponse> userApiUpdateUser(String userId,
                                                          UpdateUserDto updateUserDto) {
        log.info("Update user: {}", updateUserDto.getUsername());
        var response = service.update(UUID.fromString(userId), updateUserDto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserResponse> userApiGetUser(String userId) {
        var response = service.getById(UUID.fromString(userId));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> userApiDeleteUser(String userId) {
        service.delete(UUID.fromString(userId));
        log.info("Delete user with id: {}", userId);
        return ResponseEntity.ok().build();
    }
}