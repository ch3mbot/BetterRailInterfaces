package net.chembot.betterrailinterfaces.blocks;

import net.chembot.betterrailinterfaces.tile_entities.fluid_exchanger_tile_entity;
import net.chembot.betterrailinterfaces.tile_entities.stock_detector_tile_entity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;
import java.util.Random;


public class fluid_exchanger extends Block implements ITileEntityProvider
{

    public static final PropertyBool PULLING = PropertyBool.create("pulling");
    public fluid_exchanger()
    {
        super(Material.IRON);
        setHarvestLevel("pickaxe", 0);
        setHardness(2F);
        setResistance(15F);
        setDefaultState(getDefaultState().withProperty(PULLING, Boolean.valueOf(false)));
        setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {PULLING});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(PULLING, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(PULLING)? 1 : 0;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new fluid_exchanger_tile_entity(worldIn, meta);
    }

    @Override
    public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_)
    {
        return Item.getItemFromBlock(BRIBlocks.fluid_exchanger);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        /*
        TileEntity tileentity = worldIn.getTileEntity(pos);
        worldIn.removeTileEntity(pos);

        if (tileentity instanceof stock_detector_tile_entity)
        {
            super.breakBlock(worldIn, pos, state);
        }*/
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!worldIn.isRemote)
        {
            if (worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, BRIBlocks.fluid_exchanger.getDefaultState().withProperty(PULLING, true), 3);
            }
            else
            {
                worldIn.setBlockState(pos, BRIBlocks.fluid_exchanger.getDefaultState().withProperty(PULLING, false), 3);
            }
        }
    }
}