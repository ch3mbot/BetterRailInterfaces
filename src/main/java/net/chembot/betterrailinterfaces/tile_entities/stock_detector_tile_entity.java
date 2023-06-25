package net.chembot.betterrailinterfaces.tile_entities;

import cam72cam.immersiverailroading.entity.EntityRollingStock;
import cam72cam.immersiverailroading.entity.LocomotiveSteam;
import cam72cam.mod.entity.CustomEntity;
import cam72cam.mod.entity.ModdedEntity;
import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.chembot.betterrailinterfaces.blocks.stock_detector;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static net.chembot.betterrailinterfaces.blocks.stock_detector.POWERED;

public class stock_detector_tile_entity extends TileEntity implements ITickable
{
    public int ticksAlive = 0;
    public boolean detected;

    public stock_detector_tile_entity(World worldIn, int meta)
    {
        ticksAlive = 0;
        changeq = true;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        ticksAlive = 0; //compound.getInteger("ticksAlive");
        detected = compound.getBoolean("detected");
        changeq = true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("ticksAlive", ticksAlive);
        compound.setBoolean("detected", detected);
        return compound;
    }

    @Override
    public boolean shouldRefresh(World worldIn, BlockPos pos, IBlockState p_shouldRefresh_3_, IBlockState p_shouldRefresh_4_) {
        return true; // worldIn.getBlockState(pos).getBlock() == Blocks.AIR;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        if(pkt.getNbtCompound()!= null)
        {
            this.detected = pkt.getNbtCompound().getBoolean("detected");
        }
    }

    private boolean changeq;

    private List<Entity> getNearbyStock()
    {
        return getWorld().getEntities(Entity.class, x ->
        {
            if(x instanceof ModdedEntity){
                if(((ModdedEntity)x).getSelf() instanceof EntityRollingStock)
                {
                    if(getPos().distanceSq(x.getPosition()) < 64)
                    {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    private IBlockState getState()
    {
        return getWorld().getBlockState(getPos());
    }

    public void SendSynch(){

    }



    @Override
    public void update()
    {
        //only run this on server I DO NOT CARE SYNCH DOESNT WORK (try packets later)
        //if(!world.isRemote)
            //return;

        //log was created
        if(ticksAlive == 0)
        {
            //initialize to match the block
            detected = getState().getValue(POWERED);
            //BetterRailInterfaces.logger.info("newly made and set to " + detected);
        }

        //every second do check
        if(ticksAlive % (20) == 0)
        {
            //get entities
            List<Entity> entities = getNearbyStock();

            //set up old and new detected
            boolean newDetected = entities.size() > 0;
            boolean oldDetected = detected;
            detected = newDetected;

            boolean changed = oldDetected != newDetected;
            boolean stateDiffers = getState().getValue(POWERED) != oldDetected;

            if(stateDiffers)
            {
                BetterRailInterfaces.logger.error("previous state does not match blockstate, state: " + !oldDetected + ", old: " + oldDetected);

            }

            if(changed || stateDiffers)
            {
                //queue up neighbour change
                changeq = true;

                //set block state
                this.getWorld().setBlockState(getPos(), BRIBlocks.stock_detector.getDefaultState().withProperty(POWERED, newDetected), 2);

                this.getWorld().notifyNeighborsOfStateChange(this.getPos(), this.getWorld().getBlockState(this.getPos()).getBlock(), false);

                //this.getWorld().notifyBlockUpdate(this.getPos(), getState(), getState(), 2);

                //BetterRailInterfaces.logger.warn("power: " + getState().getValue(POWERED));

                //old state and new state?
                //this.getWorld().notifyBlockUpdate(getPos(), this.getWorld().getBlockState(this.getPos()), this.getWorld().getBlockState(this.getPos()), 3);

                //mark in need of chunk save
                markDirty();

                changeq = true;
                BetterRailInterfaces.logger.info("Stock detector changed, found " + entities.size() + " close entities, newDetec: " + newDetected);
            }
        }
        if(ticksAlive % (20) == 1 && changeq)
        {
            //this.getWorld().notifyBlockUpdate(getPos(), this.getWorld().getBlockState(this.getPos()), this.getWorld().getBlockState(this.getPos()), 3);
            //this.getWorld().notifyNeighborsOfStateChange(this.getPos(), this.getWorld().getBlockState(this.getPos()).getBlock(), true);
            this.getWorld().notifyBlockUpdate(this.getPos(), getState(), getState(), 3);
            //BetterRailInterfaces.logger.info("changeq ran.");
            changeq = false;
        }
        ticksAlive++;
    }
}
