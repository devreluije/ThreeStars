package fr.reluije.threeStars.profiles;

import fr.reluije.threeStars.dto.PlayerProfileDTO;
import fr.reluije.threeStars.services.PlayerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerProfileController {

    @Autowired
    private PlayerProfileService service;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PlayerProfileDTO create(@RequestBody PlayerProfileDTO playerProfileDTO) {
        PlayerProfile entity = service.convertToEntity(playerProfileDTO);
        PlayerProfile result = service.createProfile(entity);

        return service.convertToDto(result);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerProfileDTO> get(@PathVariable("id") UUID uniqueId) {
        return ResponseEntity.of(service.findByUniqueId(uniqueId).map(service::convertToDto));
    }
}
