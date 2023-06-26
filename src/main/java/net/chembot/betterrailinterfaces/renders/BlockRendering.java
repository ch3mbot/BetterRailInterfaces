package net.chembot.betterrailinterfaces.renders;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockRendering
{
    public static List<Item> items;
    public static List<String> names;

    public static void Initialization()
    {
        items = new ArrayList<Item>();
        names = new ArrayList<String>();
    }

    public static void AddEntry(Item item, String name)
    {
        items.add(item);
        names.add(name);
    }

    public static void registerModels(ModelRegistryEvent event)
    {
        BetterRailInterfaces.logger.info("Registering item models");

        //register(BRIBlocks.stock_detector, "stock_detector");
        //should exist? maybe it doesnt exist and getItemFromBlock returns null
        for (int i = 0; i < items.size(); i++)
        {
            registerItem(items.get(i), names.get(i));
            BetterRailInterfaces.logger.info("Registered " + names.get(i) + " item model");
        }
        //registerItem(BRIBlocks.ITEMS.get(0), "stock_detector");
        //registerItem(BRIBlocks.ITEMS.get(1), "fluid_exchanger");

        //meta loop (no meta)
        //entity rendering registry render tile entities() (no tile entities)
    }

    public static void registerBlock(Block block, String model)
    {
        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(BetterRailInterfaces.modAddress() + model, "inventory"));
    }

    public static void registerItem(Item item, String model)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(BetterRailInterfaces.modAddress() + model, "inventory"));

    }
}
