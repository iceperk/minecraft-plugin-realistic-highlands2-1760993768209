package com.realistic_highlands2;

import com.realistic_highlands2.commands.HighlandsGenerateBiomeCommand;
import com.realistic_highlands2.commands.HighlandsInfoCommand;
import com.realistic_highlands2.commands.HighlandsReloadCommand;
import com.realistic_highlands2.generator.RealisticWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RealisticHighlands2 extends JavaPlugin {

    private WorldGeneratorConfig config;

    @Override
    public void onEnable() {
        // Load configuration
        saveDefaultConfig();
        this.config = new WorldGeneratorConfig(this);
        config.loadConfig();

        // Register commands
        this.getCommand("highlandsReload").setExecutor(new HighlandsReloadCommand(this));
        this.getCommand("highlandsInfo").setExecutor(new HighlandsInfoCommand(this));
        this.getCommand("highlandsGenerateBiome").setExecutor(new HighlandsGenerateBiomeCommand(this));

        getLogger().info("RealisticHighlands2 has been enabled!");
        getLogger().warning("Remember to set 'generator-settings: ' and 'level-type: FLAT' in your server.properties for custom world generation to work correctly!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RealisticHighlands2 has been disabled!");
    }

    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        // This method is called when a world is created with this plugin as its generator.
        // The 'id' parameter can be used to pass custom settings if needed (e.g., specific world presets).
        // For now, we'll use our default RealisticWorldGenerator.
        getLogger().info("Providing RealisticWorldGenerator for world: " + worldName + " with id: " + id);
        return new RealisticWorldGenerator(this.config);
    }

    public WorldGeneratorConfig getPluginConfig() {
        return config;
    }

    public void reloadPluginConfig() {
        reloadConfig();
        this.config.loadConfig();
        getLogger().info("RealisticHighlands2 configuration reloaded.");
    }
}
