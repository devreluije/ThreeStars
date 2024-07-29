package fr.reluije.threeStars.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerProfileRepository extends JpaRepository<PlayerProfile, Long> {

    boolean existsByUniqueId(UUID uniqueId);

    Optional<PlayerProfile> findByUniqueId(UUID uniqueId);

}
