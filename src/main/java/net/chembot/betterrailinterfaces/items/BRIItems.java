package net.chembot.betterrailinterfaces.items;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.renders.ItemRendering;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

import static net.chembot.betterrailinterfaces.BetterRailInterfaces.logger;

public class BRIItems
{
    public static Item item_configurator;
    public static List<Item> ITEMS = new ArrayList<Item>();

    public static void Initialization()
    {
        register("rail_interface_configurator", new rail_interface_configurator());
    }

    public static Item register(String name, Item item)
    {
        item.setUnlocalizedName(name); //has mod name?
        item.setRegistryName(BetterRailInterfaces.locate(name));
        item.setCreativeTab(CreativeTabs.SEARCH);
        ITEMS.add(item);

        //this should really be called from the register model event, but there doesn't seem to be any way to find out how to subscribe to it
        ItemRendering.AddEntry(item, name);

        return item;
    }

    public static void RegisterItems(IForgeRegistry<Item> itemRegistry)
    {
        Item[] items = new Item[ITEMS.size()];
        for (int i = 0; i < ITEMS.size(); i++)
        {
            items[i] = ITEMS.get(i);
        }
        logger.info("Registering " + ITEMS.size() + " items");
        itemRegistry.registerAll(items);
    }
}
