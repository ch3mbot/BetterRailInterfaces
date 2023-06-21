package net.chembot.betterrailinterfaces.registry;

import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BRIRegistryEvent {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BRIBlocks.RegisterBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        BRIBlocks.RegisterItems(event.getRegistry());
    }
}
