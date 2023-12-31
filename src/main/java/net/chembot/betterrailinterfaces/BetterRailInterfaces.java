package net.chembot.betterrailinterfaces;

import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.chembot.betterrailinterfaces.items.BRIItems;
import net.chembot.betterrailinterfaces.networking.BRINetworkingManager;
import net.chembot.betterrailinterfaces.registry.BRIRegistryEvent;
import net.chembot.betterrailinterfaces.renders.BlockRendering;
import net.chembot.betterrailinterfaces.renders.ItemRendering;
import net.chembot.betterrailinterfaces.tile_entities.BRITileEntities;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = BetterRailInterfaces.MODID, name = BetterRailInterfaces.NAME, version = BetterRailInterfaces.VERSION, dependencies = BetterRailInterfaces.DEPENDENCIES)
public class BetterRailInterfaces
{
    @SidedProxy(modId = BetterRailInterfaces.MODID, clientSide = "net.chembot.betterrailinterfaces.client.ClientProxy", serverSide = "net.chembot.betterrailinterfaces.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static BetterRailInterfaces instance;

    public static final String MODID = "betterrailinterfaces";
    public static final String NAME = "Better Rail Interfaces";
    public static final String VERSION = "0.0.1";
    public static final String DEPENDENCIES = "required-after:universalmodcore"; //@1.12.2-forge-1.1.4-2b81e7" ;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        logger = event.getModLog();
        logger.info("Better Rail Interfaces preInit");

        BlockRendering.Initialization();
        ItemRendering.Initialization();

        BRINetworkingManager.preInit();

        BRIBlocks.Initialization();
        //craft tweaker i guess?
        //ForgeRegistries.BLOCKS.registerAll(BRIBlocks.GetBlockArray());

        BRIItems.Initialization();

        CommonProxy.registerEvent(BRIRegistryEvent.class);
        //MinecraftForge.EVENT_BUS.register(BRIRegistryEvent.class);

        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        logger.info("Better Rail Interfaces init");

        //tile entity initialize?
        BRITileEntities.init();

        //ForgeRegistries.ITEMS.registerAll(BRIBlocks.GetItemArray());

        //registerEvent entity events and event handler
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

    public static ResourceLocation locate(String location)
    {
        return new ResourceLocation(MODID, location);
    }
    public static String modAddress() { return MODID + ":"; }
}
