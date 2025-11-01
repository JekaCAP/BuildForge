package ru.open.source.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import ru.open.source.service.profile.ProfileService;
import ru.opensource.buildforge.generated.api.ProfilesApiDelegate;
import ru.opensource.buildforge.generated.dto.ProfileResponse;
import ru.opensource.buildforge.generated.dto.UpdateProfileDto;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ProfilesApiDelegateImpl implements ProfilesApiDelegate {

    private final ProfileService service;

    public ResponseEntity<ProfileResponse> profileApiUpdateProfile(String userId, UpdateProfileDto updateProfileDto) {
        log.info("Update profile {}", userId);
        return ResponseEntity.ok(service.update(UUID.fromString(userId), updateProfileDto));
    }

    public ResponseEntity<ProfileResponse> profileApiGetProfile(String userId) {
        return ResponseEntity.ok(service.get(UUID.fromString(userId)));
    }
}
