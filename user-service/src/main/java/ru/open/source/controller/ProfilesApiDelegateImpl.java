package ru.open.source.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.open.source.service.profile.ProfileService;
import ru.opensource.buildforge.generated.api.ProfilesApiDelegate;
import ru.opensource.buildforge.generated.dto.ProfileResponse;
import ru.opensource.buildforge.generated.dto.UpdateProfileDto;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfilesApiDelegateImpl implements ProfilesApiDelegate {

    private final ProfileService service;

    @Override
    public ResponseEntity<ProfileResponse> profileApiUpdateProfile(String userId, UpdateProfileDto updateProfileDto) {
        log.info("Update profile {}", userId);
        return ResponseEntity.ok(service.update(UUID.fromString(userId), updateProfileDto));
    }

    @Override
    public ResponseEntity<ProfileResponse> profileApiGetProfile(String userId) {
        return ResponseEntity.ok(service.get(UUID.fromString(userId)));
    }
}
