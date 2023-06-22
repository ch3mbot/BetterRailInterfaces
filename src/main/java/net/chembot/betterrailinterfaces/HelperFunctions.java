package net.chembot.betterrailinterfaces;

import cam72cam.mod.entity.CustomEntity;
import cam72cam.mod.entity.Entity;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

import cam72cam.immersiverailroading.entity.EntityRollingStock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class HelperFunctions
{
    public static <T extends EntityRollingStock> List<T> getAllStock(Class<T> type, net.minecraft.world.World world)
    {
        List<net.minecraft.entity.Entity> entities = world.getEntities(net.minecraft.entity.Entity.class, stock -> {
            return type.isInstance(new Entity(stock));
        });
        List<T> stocks = new ArrayList<T>();
        entities.forEach(entity -> { stocks.add((T)(new Entity(entity))); });
        return stocks;
    }

    public static <T extends EntityRollingStock> List<T> getAllNearbyStock(Class<T> type, net.minecraft.world.World world, Vec3d position, double range)
    {
        List<T> allStock = getAllStock(type, world);
        List<T> closeStock = new ArrayList<T>();
        allStock.forEach(stock -> {
            cam72cam.mod.math.Vec3d temp = stock.getPosition();
            Vec3d stockPos = new Vec3d(temp.x, temp.y, temp.z);
            double distsqr = position.squareDistanceTo(stockPos);
            if(distsqr <= range * range) closeStock.add(stock);
        });
        return closeStock;
    }
}