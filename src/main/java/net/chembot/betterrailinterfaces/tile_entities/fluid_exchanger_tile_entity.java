package net.chembot.betterrailinterfaces.tile_entities;

import cam72cam.immersiverailroading.entity.FreightTank;
import cam72cam.mod.entity.CustomEntity;
import cam72cam.mod.entity.ModdedEntity;
import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.IEntityFilter;
import net.chembot.betterrailinterfaces.StockHelpers;
import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.chembot.betterrailinterfaces.blocks.fluid_exchanger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class fluid_exchanger_tile_entity  extends TileEntity implements ITickable, IFluidHandler, ICapabilityProvider, IEntityFilter
{
    public static final int CAPACITY = 16000;
    public boolean pulling;
    public FluidTank fluidTank;
    public int ticksAlive;

    private Class filter;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }

    public fluid_exchanger_tile_entity()
    {
        ticksAlive = 0;
    }

    public fluid_exchanger_tile_entity(World worldIn, int meta)
    {
        fluidTank = new FluidTank(CAPACITY);
        ticksAlive = 0;
    }

    @Override
    public boolean shouldRefresh(World worldIn, BlockPos pos, IBlockState p_shouldRefresh_3_, IBlockState p_shouldRefresh_4_) {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        ticksAlive = compound.getInteger("ticksAlive");
        fluidTank = new FluidTank(CAPACITY);
        fluidTank.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ticksAlive", ticksAlive);
        fluidTank.writeToNBT(compound);
        return compound;
    }


    @Override
    public void update()
    {
        //BetterRailInterfaces.logger.info(getWorld().getBlockState(getPos()));

        if(getWorld().getBlockState(getPos()).getBlock() != BRIBlocks.fluid_exchanger)
        {
            getWorld().removeTileEntity(getPos());
        }

        ticksAlive++;

        if(ticksAlive % 20 == 0)
        {
            pulling = this.getWorld().getBlockState(this.getPos()).getValue(fluid_exchanger.PULLING);
            //BetterRailInterfaces.logger.info("pulling: " + pulling);

            List<Entity> entities = StockHelpers.getNearbyStock(this.getWorld(), this.getPos(), 36);
            if(entities.size() == 0)
                return;

            ModdedEntity modEnt;
            CustomEntity custEnt = null;

            int foundIndex = -1;
            for(int i = 0; i < entities.size(); i++)
            {
                modEnt = (ModdedEntity)entities.get(i);
                custEnt = modEnt.getSelf();

                if(custEnt instanceof FreightTank)
                {
                    if(filter != null && this.getFilterClass().isInstance(custEnt))
                    {
                        foundIndex = i;
                    }
                }
            }
            if(foundIndex == -1)
                return;

            cam72cam.mod.fluid.FluidTank modcoreStockTank =  ((FreightTank)custEnt).theTank;
            FluidTank actualStockTank = modcoreStockTank.internal;

            cam72cam.mod.fluid.FluidStack modcoreFluidStack = modcoreStockTank.getContents();
            FluidStack actualFluidStack = modcoreFluidStack.internal;

            if(pulling && actualFluidStack != null && actualFluidStack.getFluid() != null)
            {
                //BetterRailInterfaces.logger.warn("pullin and full and has fluid");
                if(fluidTank.getFluid() == null || actualFluidStack.getFluid() == fluidTank.getFluid().getFluid())
                {
                    //BetterRailInterfaces.logger.warn("transferred in theory");
                    int space = fluidTank.getCapacity() - fluidTank.getFluidAmount();
                    FluidStack taken = actualStockTank.drain(space, true);
                    fluidTank.fill(taken, true);
                }
            }
            else if (!pulling && fluidTank.getFluid() != null && fluidTank.getFluid().getFluid() != null)
            {
                //BetterRailInterfaces.logger.warn("pushing and full and has fluid");
                if(actualStockTank.getFluid() == null || actualFluidStack.getFluid() == fluidTank.getFluid().getFluid())
                {
                    //BetterRailInterfaces.logger.warn("transferred in theory");
                    int space = actualStockTank.getCapacity() - actualStockTank.getFluidAmount();
                    FluidStack given = fluidTank.drain(space, true);
                    actualStockTank.fill(given, true);
                }
            }
        }
//fluidTank.getFluid() != null && (actualStockTank.getFluid() == null || actualStockTank.getFluid() == fluidTank.getFluid())
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[0];
    }

    @Override
    public int fill(FluidStack fluidStack, boolean b) {
        return fluidTank.fill(fluidStack, b);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack fluidStack, boolean b) {
        return fluidTank.drain(fluidStack, b);
    }

    @Nullable
    @Override
    public FluidStack drain(int i, boolean b) {
        return fluidTank.drain(i, b);
    }

    @Override
    public Class getFilterClass()
    {
        return this.filter;
    }

    @Override
    public boolean isFilter()
    {
        return filter != null;
    }

    @Override
    public void setFilterClass(Class clazz)
    {
        this.filter = clazz;
    }
}
