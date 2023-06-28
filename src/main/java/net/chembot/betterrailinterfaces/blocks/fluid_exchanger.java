package net.chembot.betterrailinterfaces.blocks;

import cam72cam.immersiverailroading.items.BaseItemRollingStock;
import cam72cam.immersiverailroading.registry.EntityRollingStockDefinition;
import cam72cam.immersiverailroading.render.item.StockItemModel;
import cam72cam.mod.entity.Player;
import cam72cam.mod.item.CustomItem;
import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.IEntityFilter;
import net.chembot.betterrailinterfaces.StockHelpers;
import net.chembot.betterrailinterfaces.items.rail_interface_configurator;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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

        worldIn.removeTileEntity(pos);
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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float a, float b, float c)
    {
        if(hand == EnumHand.OFF_HAND)
            return false;

        ItemStack stack = player.inventory.getCurrentItem();
        if(stack.getItem() == Items.AIR)
        {
            return false;
        }

        StockHelpers.LogClassStructure(stack.getItem());
        //BetterRailInterfaces.logger.warn(StockHelpers.GetClass(StockHelpers.StackToBadStack(stack)));
        if(stack.getItem() instanceof rail_interface_configurator)
        {
            BetterRailInterfaces.logger.warn("right clicked with configurator");
            TileEntity ent = worldIn.getTileEntity(pos);
            if(ent instanceof IEntityFilter)
            {
                IEntityFilter filter = (IEntityFilter) ent;
                filter.setFilterClass(((rail_interface_configurator)stack.getItem()).heldClass);
            }
        }

        return false;
    }
}
