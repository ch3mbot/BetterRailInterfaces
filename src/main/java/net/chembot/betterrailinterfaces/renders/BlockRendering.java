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
        //didnt work with block
        //register(BRIBlocks.stock_detector, "stock_detector");
        
        //should exist
        registerItem(BRIBlocks.ITEMS.get(0), "stock_detector");
    }

    //didn't work
    public static void register(Block block, String model)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(BetterRailInterfaces.modAddress() + model, "inventory"));
    }

    //tried without using getItemFromBlock, didn't work either
    public static void registerItem(Item item, String model)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(BetterRailInterfaces.modAddress() + model, "inventory"));

    }
}
