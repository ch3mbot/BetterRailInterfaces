package net.chembot.betterrailinterfaces.items;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

import static net.chembot.betterrailinterfaces.BetterRailInterfaces.logger;

public class BRIItems
{
    public static final List<Item> ITEMS = new ArrayList<Item>();

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
