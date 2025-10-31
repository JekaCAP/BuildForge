package ru.open.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.open.source.entity.Profile;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

}
