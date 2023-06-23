package net.chembot.betterrailinterfaces.tile_entities;

import cam72cam.immersiverailroading.entity.EntityRollingStock;
import cam72cam.immersiverailroading.entity.LocomotiveSteam;
import cam72cam.mod.entity.CustomEntity;
import cam72cam.mod.entity.ModdedEntity;
import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.blocks.BRIBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
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
        changeq = false;
    }

    /*
    @Override
    public boolean shouldRefresh(World p_shouldRefresh_1_, BlockPos p_shouldRefresh_2_, IBlockState p_shouldRefresh_3_, IBlockState p_shouldRefresh_4_) {
        return false;
    }
    */

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

    @Override
    public void update()
    {
        //only run this on server
        if(!world.isRemote)
            return;

        //log was created
        if(ticksAlive == 0)
        {
            BetterRailInterfaces.logger.info("newly made");
            //initialize to match the block
            detected = getState().getValue(POWERED);
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

            if(changed)
            {
                //queue up neighbour change
                changeq = true;

                //set block state
                world.setBlockState(getPos(), BRIBlocks.stock_detector.getDefaultState().withProperty(POWERED, newDetected), 2);

                //old state and new state?
                world.notifyBlockUpdate(getPos(), BRIBlocks.stock_detector.getDefaultState().withProperty(POWERED, oldDetected), BRIBlocks.stock_detector.getDefaultState().withProperty(POWERED, newDetected), 2);

                //mark in need of chunk save
                markDirty();

                BetterRailInterfaces.logger.info("Stock detector changed, found " + entities.size() + " close entities, newDetec: " + newDetected);
            }
        }
        if(ticksAlive % (20) == 1 && changeq)
        {
            world.notifyNeighborsOfStateChange(getPos(), getWorld().getBlockState(getPos()).getBlock(), true);
            changeq = false;
        }
        ticksAlive++;
    }
}
