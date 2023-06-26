package net.chembot.betterrailinterfaces.tile_entities;

import cam72cam.immersiverailroading.entity.FreightTank;
import cam72cam.mod.entity.CustomEntity;
import cam72cam.mod.entity.ModdedEntity;
import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.StockHelpers;
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

public class fluid_exchanger_tile_entity  extends TileEntity implements ITickable, IFluidHandler, ICapabilityProvider
{
    public static final int CAPACITY = 16000;
    public boolean pulling;
    public FluidTank fluidTank;
    public int ticksAlive;

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

        ticksAlive++;

        if(ticksAlive % 20 == 0)
        {
            pulling = this.getWorld().getBlockState(this.getPos()).getValue(fluid_exchanger.PULLING);
            BetterRailInterfaces.logger.info("pulling: " + pulling);

            List<Entity> entities = StockHelpers.getNearbyStock(this.getWorld(), this.getPos(), 36);
            if(entities.size() == 0)
                return;

            //StockHelpers.LogClassStructure(entities.get(0));

            if(!(entities.get(0) instanceof ModdedEntity))
                return;

            //StockHelpers.LogClassStructure(((ModdedEntity)entities.get(0)).getSelf());

            if(fluidTank.getFluid() != null)
                BetterRailInterfaces.logger.info("fluid tank stack: " + fluidTank.getFluid().amount);


            if(!(((ModdedEntity)entities.get(0)).getSelf() instanceof FreightTank))
                return;

            ModdedEntity modEnt = (ModdedEntity)entities.get(0);
            CustomEntity custEnt = modEnt.getSelf();

            cam72cam.mod.fluid.FluidTank modcoreStockTank =  ((FreightTank)custEnt).theTank;
            FluidTank actualStockTank = modcoreStockTank.internal;

            cam72cam.mod.fluid.FluidStack modcoreFluidStack = modcoreStockTank.getContents();
            FluidStack actualFluidStack = modcoreFluidStack.internal;

            if(pulling && actualFluidStack != null && actualFluidStack.getFluid() != null)
            {
                BetterRailInterfaces.logger.warn("pullin and full and has fluid");
                if(fluidTank.getFluid() == null || actualFluidStack.getFluid() == fluidTank.getFluid().getFluid())
                {
                    BetterRailInterfaces.logger.warn("transferred in theory");
                    int space = fluidTank.getCapacity() - fluidTank.getFluidAmount();
                    FluidStack taken = actualStockTank.drain(space, true);
                    fluidTank.fill(taken, true);
                }
            }
            else if (!pulling && fluidTank.getFluid() != null && fluidTank.getFluid().getFluid() != null)
            {
                BetterRailInterfaces.logger.warn("pushing and full and has fluid");
                if(actualStockTank.getFluid() == null || actualStockTank.getFluid() == fluidTank.getFluid())
                {
                    BetterRailInterfaces.logger.warn("transferred in theory");
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
}
