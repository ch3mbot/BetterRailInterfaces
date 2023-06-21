package net.chembot.betterrailinterfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommonProxy
{
    public void preInit() {}

    public void init() {}

    public void postInit() {}

    public EntityPlayer getThePlayer() { return null; }

    public void sendMessage(EntityPlayer player, ITextComponent message) { }

    @SuppressWarnings("deprecation")
    public static void registerEvent(Object event)
    {
        FMLCommonHandler.instance().bus().register(event);
        MinecraftForge.EVENT_BUS.register(event);
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }
}
