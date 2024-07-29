package fr.reluije.threeStars.commands;

import fr.reluije.threeStars.ThreeStars;
import fr.reluije.threeStars.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Level;

public class PlayerCommand implements TabExecutor {

    private final ThreeStars plugin;

    public PlayerCommand(ThreeStars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Messages.SHOW_USAGE);
            return true;
        }
        Player player = Bukkit.getPlayerExact(args[0]);

        if (player == null) {
            sender.sendMessage(Messages.SHOW_NO_PLAYER.formatted(args[0]));
            return true;
        }
        plugin.getWebAccess().getProfileByUniqueId(player.getUniqueId()).whenComplete((profile, error) -> {
            if (error != null) {
                plugin.getLogger().log(Level.WARNING, Messages.FAIL_TO_LOAD.formatted(player.getName()), error);
                sender.sendMessage(Messages.SHOW_FAIL_TO_LOAD.formatted(player.getName()));
                return;
            }
            if (profile == null) sender.sendMessage(Messages.SHOW_NO_PROFILE.formatted(player.getName()));
            else {
                sender.sendMessage(
                        Messages.SHOW_SUCCESS.formatted(profile.id(), profile.name(), profile.uniqueId().toString()));
            }
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String lastArg = args.length == 0 ? "" : args[args.length - 1].toLowerCase();
        int size = args.length == 0 ? 0 : args.length - 1;

        if (size == 0) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(lastArg)).toList();
        }
        return List.of();
    }
}
