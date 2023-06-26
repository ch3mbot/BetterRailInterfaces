package net.chembot.betterrailinterfaces;

import cam72cam.immersiverailroading.entity.EntityRollingStock;
import cam72cam.mod.entity.ModdedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class StockHelpers
{


    public static List<Entity> getNearbyStock(World worldIn, BlockPos pos, int rangeSqr)
    {
        return worldIn.getEntities(Entity.class, x ->
        {
            if(x instanceof ModdedEntity){
                if(((ModdedEntity)x).getSelf() instanceof EntityRollingStock)
                {
                    if(pos.distanceSq(x.getPosition()) < rangeSqr)
                    {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    public static void LogClassStructure(Object entity)
    {
        String log = "";
        Class base = entity.getClass();
        log += base.getSimpleName();
        Class superClass = base.getSuperclass();
        while (superClass != null)
        {
            log += "\nextends " + superClass.getSimpleName();
            log += " implements ";
            for (Class c : superClass.getInterfaces())
            {
                log += c.getSimpleName() + ", ";
            }

            superClass = superClass.getSuperclass();
        }

        BetterRailInterfaces.logger.info(log);
    }
}
