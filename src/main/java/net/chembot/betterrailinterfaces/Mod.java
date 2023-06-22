package net.chembot.betterrailinterfaces;

import cam72cam.mod.ModCore;

@net.minecraftforge.fml.common.Mod(modid = Mod.MODID, name = "Better Rail Interfaces", version = "0.0.1", dependencies = "required-after:immersiverailroading@[1.9.0,1.9.5)", acceptedMinecraftVersions = "[1.12,1.13)")
public class Mod {
    public static final String MODID = "betterrailinterfaces";

    static {
        try {
            ModCore.register(new net.chembot.betterrailinterfaces.BetterRailInterfaces());
        } catch (Exception e) {
            throw new RuntimeException("Could not load mod " + MODID, e);
        }
    }
}
