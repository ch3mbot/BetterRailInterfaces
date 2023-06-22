package net.chembot.betterrailinterfaces;

import cam72cam.immersiverailroading.items.ItemTabs;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;

import java.util.Collections;
import java.util.List;

public class ItemStockDetector extends CustomItem
{

    public ItemStockDetector() {
        super(BetterRailInterfaces.MODID, "ItemStockDetector");
    }

    @Override
    public List<CreativeTab> getCreativeTabs() {
        return Collections.singletonList(ItemTabs.MAIN_TAB);
    }
}
