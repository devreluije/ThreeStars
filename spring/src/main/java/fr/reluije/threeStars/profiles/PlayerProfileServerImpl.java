package fr.reluije.threeStars.profiles;

import fr.reluije.threeStars.dto.PlayerProfileDTO;
import fr.reluije.threeStars.exceptions.CreateException;
import fr.reluije.threeStars.exceptions.ExecutionException;
import fr.reluije.threeStars.services.PlayerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerProfileServerImpl implements PlayerProfileService {

    @Autowired
    private PlayerProfileRepository repository;

    @Override
    public boolean existsByUniqueId(UUID uniqueId) {
        try {
            return repository.existsByUniqueId(uniqueId);
        } catch (Throwable e) {
            throw new ExecutionException("Failed to check existence of player profile", e);
        }
    }

    @Override
    public PlayerProfile createProfile(PlayerProfile profile) {
        if (existsByUniqueId(profile.getUniqueId()))
            throw new CreateException("Profile %s already exists".formatted(profile.getUniqueId().toString()));
        try {
            return repository.save(profile);
        } catch (Throwable e) {
            throw new ExecutionException("Failed to save player profile", e);
        }
    }

    @Override
    public Optional<PlayerProfile> findByUniqueId(UUID uniqueId) {
        try {
            return repository.findByUniqueId(uniqueId);
        } catch (Throwable e) {
            throw new ExecutionException("Failed to fetch player profile", e);
        }
    }

    @Override
    public PlayerProfileDTO convertToDto(PlayerProfile profile) {
        return new PlayerProfileDTO(profile.getId(), profile.getUniqueId(), profile.getName());
    }

    @Override
    public PlayerProfile convertToEntity(PlayerProfileDTO profileDTO) {
        return new PlayerProfile(profileDTO.id(), profileDTO.uniqueId(), profileDTO.name());
    }
}
