// **
// * This work is licensed under the Creative Commons
// * Attribution-ShareAlike 3.0 Unported License. To view a copy of this
// * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
// */

package extrabiomes.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Sets;

import cpw.mods.fml.common.registry.LanguageRegistry;
import extrabiomes.Extrabiomes;
import extrabiomes.api.UseLogTurnerEvent;
import extrabiomes.helpers.ToolTipStringFormatter;

public class LogTurner extends ItemTool {

    public LogTurner() {
        super(0.0f, ToolMaterial.WOOD, Sets.newHashSet());
        setMaxDamage(0);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "logturner");
    }

    @Override
    public boolean isItemTool(ItemStack stack) {
        return true;
    }

    @Override
    public boolean onItemUse(ItemStack itemUsed, EntityPlayer player, World world, int x, int y, int z, int side,
        float xOffset, float yOffset, float zOffset) {
        if (!player.canPlayerEdit(x, y, z, side, itemUsed)) return false;

        final UseLogTurnerEvent event = new UseLogTurnerEvent(player, itemUsed, world, x, y, z);
        if (Extrabiomes.proxy.postEventToBus(event)) return false;
        if (event.isHandled()) return true;

        final Block block = world.getBlock(x, y, z);
        final int metadata = world.getBlockMetadata(x, y, z);

        final ItemStack is = new ItemStack(block, 1, metadata);
        final int[] oreId = OreDictionary.getOreIDs(is);
        final int logOreId = OreDictionary.getOreID("logWood");

        boolean containsLogOreId = false;
        for (int id : oreId) {
            if (id == logOreId) {
                containsLogOreId = true;
                break;
            }
        }
        if (!containsLogOreId) return false;

        final Block wood = Blocks.log;

        world.playSoundEffect(
            x + 0.5F,
            y + 0.5F,
            z + 0.5F,
            wood.stepSound.getStepResourcePath(),
            (wood.stepSound.getVolume() + 1.0F) / 2.0F,
            wood.stepSound.getPitch() * 1.55F);

        if (!world.isRemote) {
            int orientation = metadata & 12;
            final int type = metadata & 3;

            orientation = orientation == 0 ? 4 : orientation == 4 ? 8 : 0;
            world.setBlock(x, y, z, block, type | orientation, 3);
        }
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean sneaking) {
        String unlocalizedName = getUnlocalizedName() + ".description";
        String localizedName = LanguageRegistry.instance()
            .getStringLocalization(unlocalizedName);

        if (!localizedName.equals(unlocalizedName)) {
            if (tooltip.isEmpty() || ((String) tooltip.get(0)).length() <= 20) {
                ToolTipStringFormatter.Format(localizedName, tooltip);
            } else {
                ToolTipStringFormatter.Format(localizedName, tooltip, ((String) tooltip.get(0)).length() + 5);
            }
        }
    }

}
