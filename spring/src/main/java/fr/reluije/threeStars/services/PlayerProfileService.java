package fr.reluije.threeStars.services;

import fr.reluije.threeStars.dto.PlayerProfileDTO;
import fr.reluije.threeStars.profiles.PlayerProfile;

import java.util.Optional;
import java.util.UUID;

public interface PlayerProfileService {

    boolean existsByUniqueId(UUID uniqueId);

    PlayerProfile createProfile(PlayerProfile profile);

    Optional<PlayerProfile> findByUniqueId(UUID uniqueId);

    PlayerProfileDTO convertToDto(PlayerProfile profile);

    PlayerProfile convertToEntity(PlayerProfileDTO profileDTO);

}
