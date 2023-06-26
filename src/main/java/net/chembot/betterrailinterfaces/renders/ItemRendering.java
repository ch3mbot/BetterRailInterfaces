package net.chembot.betterrailinterfaces.renders;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemRendering
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

        for (int i = 0; i < items.size(); i++)
        {
            registerItem(items.get(i), 0, names.get(i));
            BetterRailInterfaces.logger.info("Registered " + names.get(i) + " item model");
        }
    }

    public static void registerItem(Item item, int meta, String model)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(BetterRailInterfaces.modAddress() + model, "inventory"));
        //ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
