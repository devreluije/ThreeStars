package fr.reluije.threeStars.listeners;

import fr.reluije.threeStars.ThreeStars;
import fr.reluije.threeStars.dto.PlayerProfileDTO;
import fr.reluije.threeStars.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Level;

public class PlayerListener implements Listener {

    private final ThreeStars plugin;

    public PlayerListener(ThreeStars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        plugin.getWebAccess().getProfileByUniqueId(player.getUniqueId()).whenComplete((profile, error) -> {
            if (error != null) {
                plugin.logError(Messages.FAIL_TO_LOAD.formatted(player.getName()), error);
                return;
            }
            if (profile == null) createProfile(player);
        });
    }

    private void createProfile(Player player) {
        PlayerProfileDTO profile = PlayerProfileDTO.builder().uniqueId(player.getUniqueId()).name(player.getName())
                .build();

        plugin.getWebAccess().createProfile(profile).whenComplete((result, error) -> {
            if (error != null) {
                plugin.logError(Messages.FAIL_TO_CREATE.formatted(player.getName()), error);
                return;
            }
            if (plugin.getWebAccess().isDebug())
                plugin.getLogger().log(Level.INFO, "Profile created " + result);
        });
    }
}
