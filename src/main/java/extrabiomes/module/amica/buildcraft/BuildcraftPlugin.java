/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.amica.buildcraft;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import extrabiomes.Extrabiomes;
import extrabiomes.api.PluginEvent;
import extrabiomes.helpers.LogHelper;

public class BuildcraftPlugin {

    private static final String MOD_NAME = "Buildcraft";
    private BuildcraftAPI api = null;

    private void addOilSpawns() {
        if (api != null && api.modifyWorld()) {
            Extrabiomes.proxy.registerWorldGenerator(new OilGenerator(api));
        }
    }

    @SubscribeEvent
    public void init(PluginEvent.Init event) {
        addOilSpawns();
    }

    @SubscribeEvent
    public void postInit(PluginEvent.Post event) {
        api = null;
    }

    @SubscribeEvent
    public void preInit(PluginEvent.Pre event) {
        if (!Loader.isModLoaded("BuildCraft|Energy")) {
            return;
        }

        LogHelper.fine("Initializing %s plugin.", MOD_NAME);
        try {
            api = new BuildcraftAPI();
        } catch (final Exception ex) {
            ex.printStackTrace();
            LogHelper.fine("Could not communicate with %s. Disabling plugin.", MOD_NAME);
            api = null;
        }
    }

}
