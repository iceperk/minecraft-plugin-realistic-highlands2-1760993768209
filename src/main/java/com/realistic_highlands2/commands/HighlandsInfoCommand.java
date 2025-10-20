package com.realistic_highlands2.commands;

import com.realistic_highlands2.RealisticHighlands2;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HighlandsInfoCommand implements CommandExecutor {

    private final RealisticHighlands2 plugin;

    public HighlandsInfoCommand(RealisticHighlands2 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("realistic_highlands2.info")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        sender.sendMessage(ChatColor.GOLD + "--- RealisticHighlands2 Info ---");
        sender.sendMessage(ChatColor.YELLOW + "Version: " + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.YELLOW + "Author: " + String.join(", ", plugin.getDescription().getAuthors()));
        sender.sendMessage(ChatColor.YELLOW + "Description: " + plugin.getDescription().getDescription());
        sender.sendMessage(ChatColor.YELLOW + "Plugin Status: " + (plugin.isEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
        sender.sendMessage(ChatColor.GOLD + "----------------------------");

        return true;
    }
}
