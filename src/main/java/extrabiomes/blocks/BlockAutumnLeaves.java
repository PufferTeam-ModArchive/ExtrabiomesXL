/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.blocks;

import java.util.Optional;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extrabiomes.lib.Element;

public class BlockAutumnLeaves extends BlockLeafEbxl {

    public enum BlockType {

        UMBER(0),
        GOLDENROD(1),
        VERMILLION(2),
        CITRINE(3);

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
            if (Element.SAPLING_AUTUMN_BROWN.isPresent()) {
                UMBER.sapling = Element.SAPLING_AUTUMN_BROWN.get();
            }
            if (Element.SAPLING_AUTUMN_ORANGE.isPresent()) {
                GOLDENROD.sapling = Element.SAPLING_AUTUMN_ORANGE.get();
            }
            if (Element.SAPLING_AUTUMN_PURPLE.isPresent()) {
                VERMILLION.sapling = Element.SAPLING_AUTUMN_PURPLE.get();
            }
            if (Element.SAPLING_AUTUMN_YELLOW.isPresent()) {
                CITRINE.sapling = Element.SAPLING_AUTUMN_YELLOW.get();
            }

            loadedCustomBlocks = true;
        }

        BlockType(int metadata) {
            this.metadata = metadata;
        }

        Block getSaplingBlock() {
            if (!loadedCustomBlocks) loadCustomBlocks();
            return Block.getBlockFromItem(sapling.getItem());
        }

        int getSaplingMetadata() {
            if (!loadedCustomBlocks) loadCustomBlocks();
            return sapling.getItemDamage();
        }

        public int metadata() {
            return metadata;
        }
    }

    public BlockAutumnLeaves() {
        super("leavesbrownautumn", "leavesorangeautumn", "leavesredautumn", "leavesyellowautumn");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return 16777215;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, int x, int y, int z) {
        return 16777215;
    }

    @Override
    protected void func_150124_c(World world, int x, int y, int z, int metadata, int p_150124_6_) {
        if ((metadata & 3) == 0 && world.rand.nextInt(p_150124_6_) == 0) {
            this.dropBlockAsItem(world, x, y, z, new ItemStack(Items.apple, 1, 0));
        }
    }

    @Override
    public int damageDropped(int metadata) {
        final Optional<BlockType> type = Optional.ofNullable(BlockType.fromMetadata(metadata));
        return type.isPresent() ? type.get().getSaplingMetadata() : 0;
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int par3) {
        final Optional<BlockType> type = Optional.ofNullable(BlockType.fromMetadata(metadata));
        return Item.getItemFromBlock(type.isPresent() ? type.get().getSaplingBlock() : Blocks.sapling);
    }

    // Better Foliage (?) compat
    public float getSpawnChanceFallingLeaves(int metadata) {
        return 0.1F;
    }

}
