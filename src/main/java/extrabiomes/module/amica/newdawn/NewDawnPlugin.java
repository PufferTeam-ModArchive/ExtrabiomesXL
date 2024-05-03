package extrabiomes.module.amica.newdawn;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import extrabiomes.Extrabiomes;
import extrabiomes.api.PluginEvent;
import extrabiomes.helpers.LogHelper;

public class NewDawnPlugin {

    private static final String MOD_ID = "newdawn";
    private static NewDawnPluginImpl api;

    @SubscribeEvent
    public void preInit(PluginEvent.Pre event) {
        if (!Extrabiomes.proxy.isModLoaded(MOD_ID)) {
            return;
        }

        LogHelper.fine("Initializing %s plugin.", MOD_ID);
        try {
            api = new NewDawnPluginImpl();
        } catch (final Exception ex) {
            ex.printStackTrace();
            LogHelper.fine("Could not communicate with %s. Disabling plugin.", MOD_ID);
            api = null;
        }
    }

    @SubscribeEvent
    public void init(PluginEvent.Init event) {
        if (api != null) {
            api.init();
        }
    }

    @SubscribeEvent
    public void postInit(PluginEvent.Post event) {
        api = null;
    }

}
