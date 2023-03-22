package extrabiomes.utility;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import extrabiomes.api.Stuff;

public class CreativeTab extends CreativeTabs {

    public CreativeTab(String label) {
        super(label);
    }

    @Override
    public Item getTabIconItem() {
        if (Stuff.scarecrow.isPresent()) return Stuff.scarecrow.get();
        else return null;
    }

}
