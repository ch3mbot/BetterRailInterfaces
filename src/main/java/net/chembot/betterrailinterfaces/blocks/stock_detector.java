package net.chembot.betterrailinterfaces.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.*;

public class stock_detector extends Block
{
    public stock_detector()
    {
        super(Material.IRON);
        setHarvestLevel("pickaxe", 0);
        setHardness(2F);
        setResistance(15F);
    }
}