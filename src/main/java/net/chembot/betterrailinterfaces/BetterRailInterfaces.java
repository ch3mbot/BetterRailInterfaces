package net.chembot.betterrailinterfaces;

import cam72cam.mod.ModCore;
import cam72cam.mod.ModEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BetterRailInterfaces extends ModCore.Mod {

    public static final String MODID = "BetterRailInterfaces";

    @Override
    public String modID() {
        return MODID;
    }

    @Override
    public void commonEvent(ModEvent event) {
        switch (event) {
            case CONSTRUCT:
                //Register network packets, blocks, items, guis, and so on here
                BRIBlocks.register();
                BRIItems.register();
                break;
            case INITIALIZE:
                //Do config stuff here
                break;
            case SETUP:
                break;
            case FINALIZE:
                break;
            case START:
                break;
            case RELOAD:
                break;
        }
    }

    @Override
    public void clientEvent(ModEvent event) {
        switch (event) {
            case CONSTRUCT:
                //Register keys as well as the rendering of blocks, items, and so on here
                break;
            case INITIALIZE:
                break;
            case SETUP:
                break;
            case FINALIZE:
                break;
            case START:
                break;
            case RELOAD:
                break;
        }
    }

    @Override
    public void serverEvent(ModEvent event) {
        switch (event) {
            case CONSTRUCT:
                break;
            case INITIALIZE:
                break;
            case SETUP:
                break;
            case FINALIZE:
                break;
            case START:
                break;
            case RELOAD:
                break;
        }
    }
}