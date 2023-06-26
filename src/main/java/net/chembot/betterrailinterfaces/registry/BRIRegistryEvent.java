package net.chembot.betterrailinterfaces.registry;

import cam72cam.mod.render.ItemRender;
import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.chembot.betterrailinterfaces.items.BRIItems;
import net.chembot.betterrailinterfaces.renders.BlockRendering;
import net.chembot.betterrailinterfaces.renders.ItemRendering;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class BRIRegistryEvent {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BRIBlocks.RegisterBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        BRIBlocks.RegisterItems(event.getRegistry());
        BRIItems.RegisterItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        BetterRailInterfaces.logger.warn("model registry 2 called");
        BlockRendering.registerModels(event);
        ItemRendering.registerModels(event);
    }
/*
    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        BetterRailInterfaces.logger.warn("model registry 1 called");
        BlockRendering.registerModels(event);
    }

    @SubscribeEvent
    public static void registerModel(ModelRegistryEvent event) {
        BetterRailInterfaces.logger.warn("model registry 3 called");
        BlockRendering.registerModels(event);
    }
    */
}
