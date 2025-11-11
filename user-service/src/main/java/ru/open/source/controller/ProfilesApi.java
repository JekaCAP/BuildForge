package ru.open.source.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.open.source.service.profile.ProfileService;
import ru.opensource.buildforge.generated.api.ProfilesApiDelegate;
import ru.opensource.buildforge.generated.dto.CreateProfileDto;
import ru.opensource.buildforge.generated.dto.ProfileResponse;
import ru.opensource.buildforge.generated.dto.UpdateProfileDto;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfilesApi implements ProfilesApiDelegate {

    private final ProfileService service;

    @Override
    public ResponseEntity<ProfileResponse> profileApiCreateProfile(CreateProfileDto createProfileDto) {
        log.warn("Attempt to create profile directly via API — operation not allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @Override
    public ResponseEntity<ProfileResponse> profileApiGetProfile(String userId) {
        log.info("Get profile for user: {}", userId);
        return ResponseEntity.ok(service.get(UUID.fromString(userId)));
    }

    @Override
    public ResponseEntity<ProfileResponse> profileApiUpdateProfile(String userId, UpdateProfileDto updateProfileDto) {
        log.info("Update profile for user: {}", userId);
        return ResponseEntity.ok(service.update(UUID.fromString(userId), updateProfileDto));
    }

    @Override
    public ResponseEntity<Void> profileApiDeleteProfile(String userId) {
        log.info("Soft delete profile for user: {}", userId);
        service.delete(UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> profileApiHardDeleteProfile(String userId) {
        log.info("Hard delete profile for user: {}", userId);
        service.hardDelete(UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ProfileResponse> profileApiRestoreProfile(String userId) {
        log.info("Restore profile for user: {}", userId);
        service.restore(UUID.fromString(userId));
        return ResponseEntity.ok(service.get(UUID.fromString(userId)));
    }
}