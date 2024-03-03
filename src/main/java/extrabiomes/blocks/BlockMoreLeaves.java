/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.blocks;

import java.util.Optional;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import extrabiomes.lib.Element;

public class BlockMoreLeaves extends BlockLeafEbxl {

    public enum BlockType {

        SAKURA_BLOSSOM(0);

        private final int metadata;
        private ItemStack sapling = new ItemStack(Blocks.sapling);
        private static boolean loadedCustomBlocks = false;

        static BlockType fromMetadata(int metadata) {
            for (final BlockType type : BlockType.values()) {
                if (type.metadata() == (metadata & 3)) return type;
            }

            return null;
        }

        private static void loadCustomBlocks() {
            if (Element.SAPLING_SAKURA_BLOSSOM.isPresent())
                SAKURA_BLOSSOM.sapling = Element.SAPLING_SAKURA_BLOSSOM.get();

            loadedCustomBlocks = true;
        }

        BlockType(int metadata) {
            this.metadata = metadata;
        }

        Item getSaplingItem() {
            if (!loadedCustomBlocks) {
                loadCustomBlocks();
            }
            return (sapling.getItem());
        }

        int getSaplingMetadata() {
            if (!loadedCustomBlocks) {
                loadCustomBlocks();
            }
            return sapling.getItemDamage();
        }

        public int metadata() {
            return metadata;
        }
    }

    public BlockMoreLeaves() {
        super("leavessakura");
    }

    @Override
    public int getRenderColor(int metadata) {
        return 0xffffff;
    }

    @Override
    public int colorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z) {
        return getRenderColor(iBlockAccess.getBlockMetadata(x, y, z) & 3);
    }

    @Override
    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
    }

    @Override
    public int damageDropped(int metadata) {
        final Optional<BlockType> type = Optional.ofNullable(BlockType.fromMetadata(metadata));
        return type.isPresent() ? type.get().getSaplingMetadata() : 0;
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int par3) {
        final Optional<BlockType> type = Optional.ofNullable(BlockType.fromMetadata(metadata));
        return type.isPresent() ? type.get().getSaplingItem() : Item.getItemFromBlock(Blocks.sapling);
    }

    //Better Foliage (?) compat
    public float getSpawnChanceFallingLeaves(int metadata) {
        return 0.01F;
    }

}
