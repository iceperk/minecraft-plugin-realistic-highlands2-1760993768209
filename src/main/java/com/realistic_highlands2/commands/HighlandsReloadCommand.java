package com.realistic_highlands2.commands;

import com.realistic_highlands2.RealisticHighlands2;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HighlandsReloadCommand implements CommandExecutor {

    private final RealisticHighlands2 plugin;

    public HighlandsReloadCommand(RealisticHighlands2 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("realistic_highlands2.reload")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        plugin.reloadPluginConfig();
        sender.sendMessage(ChatColor.GREEN + "RealisticHighlands2 configuration reloaded successfully!");
        return true;
    }
}
