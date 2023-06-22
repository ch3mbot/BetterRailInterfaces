package net.chembot.betterrailinterfaces;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import  net.minecraft.util.ITickable;

import javax.annotation.Nullable;

public class StockDetector extends Block
{
    public StockDetector(Material mat) {
        super(mat);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return null;
    }
}
