/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.fabrica.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemWoodSlab extends ItemSlab {

    private static BlockSlab singleSlab, doubleSlab;

    static void setSlabs(BlockSlab singleSlab, BlockSlab doubleSlab) {
        ItemWoodSlab.singleSlab = singleSlab;
        ItemWoodSlab.doubleSlab = doubleSlab;
    }

    public ItemWoodSlab(Block block) {
        super(block, singleSlab, doubleSlab, block.equals(doubleSlab));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return singleSlab.func_150002_b(itemStack.getItemDamage());
    }

}
