package com.realistic_highlands2.commands;

import com.realistic_highlands2.RealisticHighlands2;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HighlandsGenerateBiomeCommand implements CommandExecutor, TabCompleter {

    private final RealisticHighlands2 plugin;

    public HighlandsGenerateBiomeCommand(RealisticHighlands2 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("realistic_highlands2.generatebiome")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /highlandsGenerateBiome <biome_name>");
            return true;
        }

        Player player = (Player) sender;
        String biomeName = args[0].toUpperCase();
        Biome targetBiome;
        try {
            targetBiome = Biome.valueOf(biomeName);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "Invalid biome name: " + biomeName + ". Please provide a valid Minecraft biome.");
            return true;
        }

        // This command is primarily for demonstration/debugging how the generator *might* use biomes.
        // Directly generating a chunk with a specific biome that overrides the entire worldgen process
        // is complex and not directly supported by a simple command for a custom generator.
        // A custom chunk generator determines biomes during generation based on its noise maps and logic.
        // This command will give a console message of what would happen if we *could* force it.
        player.sendMessage(ChatColor.YELLOW + "Attempting to (conceptually) generate a chunk with biome: " + targetBiome.name() + " at your location.");
        player.sendMessage(ChatColor.GRAY + "Note: This command is for testing purposes and does not instantly override custom world generation logic for existing chunks. It's more of a conceptual test.");
        plugin.getLogger().info("Player " + player.getName() + " requested to generate a conceptual chunk with biome: " + targetBiome.name() + " at X: " + player.getLocation().getChunk().getX() + ", Z: " + player.getLocation().getChunk().getZ());

        // In a real scenario, you'd have to trigger a custom generator pass for this chunk
        // with the desired biome, which is beyond a simple command's scope and requires
        // deeper integration with world generation internals.

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            String partialBiomeName = args[0].toLowerCase();
            return Arrays.stream(Biome.values())
                    .map(Biome::name)
                    .filter(name -> name.toLowerCase().contains(partialBiomeName))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
