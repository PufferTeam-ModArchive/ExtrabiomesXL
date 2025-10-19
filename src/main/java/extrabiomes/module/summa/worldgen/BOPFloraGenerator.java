/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.summa.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import biomesoplenty.common.world.features.WorldGenGrassSplatter;
import cpw.mods.fml.common.IWorldGenerator;
import extrabiomes.lib.BiomeSettings;

public class BOPFloraGenerator implements IWorldGenerator {

    WorldGenGrassSplatter grassSplatter;

    public BOPFloraGenerator() {
        grassSplatter = new WorldGenGrassSplatter();
    }

    @Override
    public void generate(Random random, int x, int z, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        int chunkX = x << 4;
        int chunkZ = z << 4;
        final BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);

        final boolean biomeIsExtremeJungle = BiomeSettings.EXTREMEJUNGLE.getBiome()
            .isPresent()
            && biome == BiomeSettings.EXTREMEJUNGLE.getBiome()
                .get();

        final boolean biomeIsMiniJungle = BiomeSettings.MINIJUNGLE.getBiome()
            .isPresent()
            && biome == BiomeSettings.MINIJUNGLE.getBiome()
                .get();
        final boolean biomeIsTemperateRainforest = BiomeSettings.TEMPORATERAINFOREST.getBiome()
            .isPresent()
            && biome == BiomeSettings.TEMPORATERAINFOREST.getBiome()
                .get();
        final boolean biomeIsForestedHills = BiomeSettings.FORESTEDHILLS.getBiome()
            .isPresent()
            && biome == BiomeSettings.FORESTEDHILLS.getBiome()
                .get();

        if (biomeIsForestedHills) {
            for (int i = 0; i < 50; i++) {
                int randX = chunkX + random.nextInt(16) + 8;
                int randZ = chunkZ + random.nextInt(16) + 8;
                int randY = world.getHeightValue(randX, randZ);
                grassSplatter.generate(world, random, randX, randY, randZ);
                grassSplatter.func_76484_a(world, random, randX, randY, randZ);
            }

        }
    }
}
