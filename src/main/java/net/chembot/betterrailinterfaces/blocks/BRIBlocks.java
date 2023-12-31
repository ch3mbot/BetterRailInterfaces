package net.chembot.betterrailinterfaces.blocks;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.blocks.stock_detector;

import net.chembot.betterrailinterfaces.registry.BRIRegistryEvent;
import net.chembot.betterrailinterfaces.renders.BlockRendering;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

import static net.chembot.betterrailinterfaces.BetterRailInterfaces.logger;

public class BRIBlocks
{
    public static Block stock_detector;
    public static Block fluid_exchanger;
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static void Initialization()
    {
        stock_detector = register("stock_detector", new stock_detector());
        fluid_exchanger = register("fluid_exchanger", new fluid_exchanger());
    }

    public static Block register(String name, Block block)
    {

        block.setUnlocalizedName(name); //has mod name?
        block.setRegistryName(BetterRailInterfaces.locate(name));
        block.setCreativeTab(CreativeTabs.SEARCH);
        BLOCKS.add(block);

        Item blockItem = new ItemBlock(block);
        blockItem.setUnlocalizedName(name); //has mod name?
        blockItem.setRegistryName(block.getRegistryName());
        ITEMS.add(blockItem);

        //this should really be called from the register model event, but there doesn't seem to be any way to find out how to subscribe to it

        BlockRendering.AddEntry(blockItem, name);

        return block;
    }

    public static void RegisterBlocks(IForgeRegistry<Block> blockRegistry)
    {
        Block[] blocks = new Block[BLOCKS.size()];
        for (int i = 0; i < BLOCKS.size(); i++)
        {
            blocks[i] = BLOCKS.get(i);
        }
        logger.info("Registering " + BLOCKS.size() + " blocks");
        blockRegistry.registerAll(blocks);
    }

    public static void RegisterItems(IForgeRegistry<Item> itemRegistry)
    {
        logger.info("Registering " + ITEMS.size() + " block items");
        Item[] items = new Item[ITEMS.size()];
        for (int i = 0; i < ITEMS.size(); i++)
        {
            items[i] = ITEMS.get(i); //doing items individually did nothing
        }
        itemRegistry.registerAll(items);
    }
}
