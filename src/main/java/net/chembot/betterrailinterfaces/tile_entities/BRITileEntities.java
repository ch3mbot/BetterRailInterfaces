package net.chembot.betterrailinterfaces.tile_entities;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BRITileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(stock_detector_tile_entity.class, BetterRailInterfaces.modAddress() + "stock_detector_tile_entity");
    }
}
