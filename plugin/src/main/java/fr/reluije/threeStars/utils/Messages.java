package fr.reluije.threeStars.utils;

import org.bukkit.ChatColor;

public class Messages {

    public static final String FAIL_TO_LOAD = "Failed to load player profile (player: %s)";
    public static final String FAIL_TO_CREATE = "Failed to create player profile (player: %s)";

    public static final String SHOW_USAGE = ChatColor.RED + "Usage: /player <player>";
    public static final String SHOW_NO_PLAYER = ChatColor.RED + "Le joueur %s n'est pas en ligne";
    public static final String SHOW_FAIL_TO_LOAD = ChatColor.RED + "Impossible de récupérer le profile du joueur %s";
    public static final String SHOW_NO_PROFILE = ChatColor.RED + "Aucun profile trouvé pour le joueur %s";
    public static final String SHOW_SUCCESS = ChatColor.GREEN + "Profile: " + ChatColor.WHITE + "%d %s %s";

}
