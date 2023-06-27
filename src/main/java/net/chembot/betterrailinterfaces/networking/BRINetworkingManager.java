package net.chembot.betterrailinterfaces.networking;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.gui.BRIGUIHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class BRINetworkingManager
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BetterRailInterfaces.MODID);

    public static void preInit()
    {
        BetterRailInterfaces.logger.info("networking manager preInit");
        NetworkRegistry.INSTANCE.registerGuiHandler(BetterRailInterfaces.instance, new BRIGUIHandler());
    }
}
