package com.realistic_highlands2;

import org.bukkit.configuration.file.FileConfiguration;

public class WorldGeneratorConfig {

    private final RealisticHighlands2 plugin;
    private FileConfiguration config;

    // Noise parameters for height
    private double heightFrequency; // How spread out the terrain features are
    private int heightOctaves;      // Number of Perlin noise layers for detail
    private double heightPersistence; // How much impact each successive octave has
    private int maxTerrainHeight;    // Max peak height above sea level
    private int minWorldHeight;      // Minimum bedrock layer height
    private int bedrockLayers;       // Number of bedrock layers at the bottom

    // Noise parameters for terrain detail (jaggedness)
    private double detailFrequency;
    private int detailOctaves;
    private double detailPersistence;
    private double detailMultiplier;

    // Noise parameters for rivers
    private double riverFrequency;
    private int riverOctaves;
    private double riverPersistence;
    private double riverThreshold; // Value above which a river trench is dug
    private int riverDepth;        // How deep the river trench is
    private int deepWaterDepth; // How deep water goes before turning to stone (for lakes/oceans)

    // Noise parameters for biome distribution
    private double biomeFrequency;
    private int biomeOctaves;
    private double biomePersistence;

    // Other environmental settings
    private int snowLevelHeight; // Y-level at which snow starts accumulating on high peaks

    public WorldGeneratorConfig(RealisticHighlands2 plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        this.config = plugin.getConfig();

        this.heightFrequency = config.getDouble("generator.height.frequency", 0.005);
        this.heightOctaves = config.getInt("generator.height.octaves", 8);
        this.heightPersistence = config.getDouble("generator.height.persistence", 0.5);
        this.maxTerrainHeight = config.getInt("generator.height.maxTerrainHeight", 150);
        this.minWorldHeight = config.getInt("generator.general.minWorldHeight", -64);
        this.bedrockLayers = config.getInt("generator.general.bedrockLayers", 5);

        this.detailFrequency = config.getDouble("generator.detail.frequency", 0.02);
        this.detailOctaves = config.getInt("generator.detail.octaves", 4);
        this.detailPersistence = config.getDouble("generator.detail.persistence", 0.3);
        this.detailMultiplier = config.getDouble("generator.detail.multiplier", 20.0);

        this.riverFrequency = config.getDouble("generator.river.frequency", 0.001);
        this.riverOctaves = config.getInt("generator.river.octaves", 3);
        this.riverPersistence = config.getDouble("generator.river.persistence", 0.4);
        this.riverThreshold = config.getDouble("generator.river.threshold", 0.6);
        this.riverDepth = config.getInt("generator.river.depth", 15);
        this.deepWaterDepth = config.getInt("generator.general.deepWaterDepth", 5);

        this.biomeFrequency = config.getDouble("generator.biome.frequency", 0.002);
        this.biomeOctaves = config.getInt("generator.biome.octaves", 5);
        this.biomePersistence = config.getDouble("generator.biome.persistence", 0.6);

        this.snowLevelHeight = config.getInt("generator.environment.snowLevelHeight", 120);

        plugin.getLogger().info("Loaded generator configuration.");
    }

    public double getHeightFrequency() { return heightFrequency; }
    public int getHeightOctaves() { return heightOctaves; }
    public double getHeightPersistence() { return heightPersistence; }
    public int getMaxTerrainHeight() { return maxTerrainHeight; }
    public int getMinWorldHeight() { return minWorldHeight; }
    public int getBedrockLayers() { return bedrockLayers; }

    public double getDetailFrequency() { return detailFrequency; }
    public int getDetailOctaves() { return detailOctaves; }
    public double getDetailPersistence() { return detailPersistence; }
    public double getDetailMultiplier() { return detailMultiplier; }

    public double getRiverFrequency() { return riverFrequency; }
    public int getRiverOctaves() { return riverOctaves; }
    public double getRiverPersistence() { return riverPersistence; }
    public double getRiverThreshold() { return riverThreshold; }
    public int getRiverDepth() { return riverDepth; }
    public int getDeepWaterDepth() { return deepWaterDepth; }

    public double getBiomeFrequency() { return biomeFrequency; }
    public int getBiomeOctaves() { return biomeOctaves; }
    public double getBiomePersistence() { return biomePersistence; }

    public int getSnowLevelHeight() { return snowLevelHeight; }
}
