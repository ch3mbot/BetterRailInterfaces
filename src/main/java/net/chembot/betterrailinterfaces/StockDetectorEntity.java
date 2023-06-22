package net.chembot.betterrailinterfaces;

import cam72cam.immersiverailroading.entity.EntityMoveableRollingStock;
import cam72cam.mod.block.BlockEntityTickable;
import cam72cam.mod.block.IRedstoneProvider;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.util.Facing;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class StockDetectorEntity extends BlockEntityTickable implements IRedstoneProvider
{
    private boolean outputting;
    private int ticksExisted;

    public StockDetectorEntity()
    {
        outputting = false;
        ticksExisted = 0;
    }

    @Override
    public void update()
    {
        if (getWorld().isClient)
            return;
        ticksExisted++;
        if ((this.ticksExisted > 10) && (this.ticksExisted % 20 == 0))
        {
            List<EntityMoveableRollingStock> foundstock = HelperFunctions.getAllNearbyStock(EntityMoveableRollingStock.class, getWorld().internal, new Vec3d(getPos().internal()), 16.0D);
            if(foundstock == null)
                outputting = false;
            else
                outputting = (foundstock.size() > 0);
            markDirty();
        }
    }

    @Override
    public ItemStack onPick()
    {
        return null;
    }

    @Override
    public int getStrongPower(Facing from)
    {
        return outputting? 15 : 0;
    }

    @Override
    public int getWeakPower(Facing from)
    {
        return outputting? 15 : 0;
    }
}