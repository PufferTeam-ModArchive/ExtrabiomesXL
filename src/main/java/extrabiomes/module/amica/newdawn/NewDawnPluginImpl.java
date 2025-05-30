package extrabiomes.module.amica.newdawn;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.world.biome.BiomeGenBase;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import extrabiomes.helpers.LogHelper;
import extrabiomes.lib.BiomeSettings;
import two.newdawn.API.NewDawnBiome;
import two.newdawn.API.NewDawnBiomeProvider;
import two.newdawn.API.NewDawnBiomeSelector;
import two.newdawn.API.NewDawnRegistry;
import two.newdawn.API.noise.NoiseStretch;
import two.newdawn.API.noise.SimplexNoise;

public class NewDawnPluginImpl implements NewDawnBiomeProvider {

    @SubscribeEvent
    public void init() {
        if (!isEnabled()) return;
        LogHelper.info("Registering NewDawn biome provider...");
        NewDawnRegistry.registerProvider(this);
    }

    private boolean isEnabled() {
        return Loader.isModLoaded("newdawn");
    }

    public static NewDawnBiome getBiomeIfEnabled(BiomeSettings biome) {
        if (biome != null && biome.isEnabled()) {
            final BiomeGenBase gen;
            try {
                gen = biome.getBiome().get();
            } catch (Exception e) {
                return null;
            }
            return NewDawnBiome.copyVanilla(gen);
        }
        return null;
    }

    public static int fuzzValue(int value, Random rng) {
        final double fuzz = 0.2 * value * rng.nextDouble();
        final double result = 0.9 * value + fuzz;
        return (int) result;
    }

    public static NoiseStretch getFuzzyStretch(int size, SimplexNoise noise) {
        final Random rng = noise.getRandom();
        final int sizeX = fuzzValue(size, rng);
        final int sizeZ = fuzzValue(size, rng);
        return noise.generateNoiseStretcher(sizeX, sizeZ, rng.nextDouble(), rng.nextDouble());
    }

    @Override
    public Set<NewDawnBiomeSelector> getBiomeSelectors(SimplexNoise worldNoise) {
        final HashSet<NewDawnBiomeSelector> selectors = new HashSet<>();

        // NB: had to change priority values because the constants are broken in the API
        selectors.add(new EBXLAridSelector(worldNoise, 450));
        selectors.add(new EBXLDampSelector(worldNoise, 500));
        selectors.add(new EBXLNormalSelector(worldNoise, 550));

        return selectors;
    }
}
