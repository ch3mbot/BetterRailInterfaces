package net.chembot.betterrailinterfaces.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BRIClientEvents
{
    private final Minecraft mc = FMLClientHandler.instance().getClient();

    @SubscribeEvent
    public static void LeftClickBlock()
    {

    }
}
