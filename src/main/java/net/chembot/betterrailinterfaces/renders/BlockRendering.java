package net.chembot.betterrailinterfaces.renders;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockRendering
{
    @SubscribeEvent
    public void onModelRegisterEvent(ModelRegistryEvent event)
    {
        //register(BRIBlocks.stock_detector, "stock_detector");
        //should exist? maybe it doesnt exist and getItemFromBlock returns null
        registerItem(BRIBlocks.ITEMS.get(0), "stock_detector");

        //meta loop (no meta)
        //entity rendering registry render tile entities() (no tile entities)
    }

    public static void register(Block block, String model)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(BetterRailInterfaces.modAddress() + model, "inventory"));
    }

    public static void registerItem(Item item, String model)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(BetterRailInterfaces.modAddress() + model, "inventory"));

    }
}
