package com.realistic_highlands2.generator;

import com.realistic_highlands2.WorldGeneratorConfig;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.PerlinNoiseGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RealisticWorldGenerator extends ChunkGenerator {

    private final WorldGeneratorConfig config;
    private PerlinNoiseGenerator heightNoise;
    private PerlinNoiseGenerator biomeNoise;
    private PerlinNoiseGenerator terrainDetailNoise;
    private PerlinNoiseGenerator riverNoise;

    private static final int SEA_LEVEL = 63;

    public RealisticWorldGenerator(WorldGeneratorConfig config) {
        this.config = config;
    }

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        // Initialize noise generators with consistent seeds for predictable terrain
        // or use the world seed for unique worlds.
        long seed = worldInfo.getSeed();
        this.heightNoise = new PerlinNoiseGenerator(seed * 101);
        this.biomeNoise = new PerlinNoiseGenerator(seed * 202);
        this.terrainDetailNoise = new PerlinNoiseGenerator(seed * 303);
        this.riverNoise = new PerlinNoiseGenerator(seed * 404);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;

                // Generate base height using multiple octaves of Perlin noise
                double mainHeight = heightNoise.noise(worldX * config.getHeightFrequency(), worldZ * config.getHeightFrequency(), config.getHeightOctaves(), config.getHeightPersistence(), 0.5, true);
                mainHeight = (mainHeight + 1) / 2; // Normalize to 0-1

                // Introduce terrain detail for jaggedness and variation
                double detail = terrainDetailNoise.noise(worldX * config.getDetailFrequency(), worldZ * config.getDetailFrequency(), config.getDetailOctaves(), config.getDetailPersistence());
                mainHeight += detail * config.getDetailMultiplier();

                // Scale height to world dimensions
                int surfaceHeight = (int) (SEA_LEVEL + mainHeight * config.getMaxTerrainHeight());

                // River generation (simple trench) - use another noise layer
                double riverValue = riverNoise.noise(worldX * config.getRiverFrequency(), worldZ * config.getRiverFrequency(), config.getRiverOctaves(), config.getRiverPersistence());
                if (riverValue > config.getRiverThreshold()) {
                    surfaceHeight -= config.getRiverDepth(); // Create a trench
                }

                // Ensure minimum height (bedrock level)
                if (surfaceHeight < config.getMinWorldHeight()) {
                    surfaceHeight = config.getMinWorldHeight();
                }

                // Fill terrain from bottom to surface
                for (int y = worldInfo.getMinHeight(); y < surfaceHeight; y++) {
                    if (y < SEA_LEVEL - config.getDeepWaterDepth()) {
                        chunkData.setBlock(x, y, z, Material.STONE);
                    } else {
                        chunkData.setBlock(x, y, z, Material.STONE);
                    }
                }

                // Add surface layer and grass where appropriate
                if (surfaceHeight >= SEA_LEVEL) {
                    chunkData.setBlock(x, surfaceHeight, z, Material.GRASS_BLOCK);
                    chunkData.setBlock(x, surfaceHeight - 1, z, Material.DIRT);
                    if (surfaceHeight - 2 >= worldInfo.getMinHeight()) {
                        chunkData.setBlock(x, surfaceHeight - 2, z, Material.DIRT);
                    }
                } else { // Below sea level, potentially water
                    chunkData.setBlock(x, surfaceHeight, z, Material.DIRT);
                    for (int y = surfaceHeight + 1; y <= SEA_LEVEL; y++) {
                        chunkData.setBlock(x, y, z, Material.WATER);
                    }
                }

                // Add bedrock layer
                for (int y = worldInfo.getMinHeight(); y < worldInfo.getMinHeight() + config.getBedrockLayers(); y++) {
                    chunkData.setBlock(x, y, z, Material.BEDROCK);
                }
            }
        }
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        // This method is called after generateNoise to modify the surface.
        // Our `generateNoise` already handles basic surface blocks like GRASS_BLOCK and DIRT.
        // You could add details here like scattered stones, custom flora, or more complex erosion.
        // For now, we rely on the noise generation to create the basic surface. 
        // For instance, adding trees or custom structures would typically happen here or in generateCaves/generateStructures.
    }

    @Override
    public @Nullable BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
        // We'll provide our own biome provider for custom biome distribution.
        return new RealisticBiomeProvider(config, worldInfo.getSeed());
    }

    // You can override generateCaves, generateStructures, etc., for more detailed generation.
}
