package net.chembot.betterrailinterfaces.renders;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemRendering
{
    @SubscribeEvent
    public void onModelRegisterEvent(ModelRegistryEvent event)
    {
        //no individual items yet
    }

    public static void register(Item item, int meta, String model)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(BetterRailInterfaces.modAddress() + model, "inventory"));
        //ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
