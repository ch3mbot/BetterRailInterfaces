package net.chembot.betterrailinterfaces;

import cam72cam.immersiverailroading.entity.EntityRollingStock;
import cam72cam.immersiverailroading.items.BaseItemRollingStock;
import cam72cam.immersiverailroading.items.ItemRollingStock;
import cam72cam.immersiverailroading.library.Gauge;
import cam72cam.immersiverailroading.registry.DefinitionManager;
import cam72cam.immersiverailroading.registry.EntityRollingStockDefinition;
import cam72cam.mod.entity.ModdedEntity;
import cam72cam.mod.entity.Player;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.serialization.TagField;
import cam72cam.mod.serialization.TagMapped;
import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

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
        String log = "actual class: " + entity.getClass().getName() + "\n";
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

    public static ItemStack StackToBadStack(net.minecraft.item.ItemStack stack)
    {
        ItemStack irStack = new ItemStack(stack);
        return irStack;
    }

    public static Class GetClass(ItemStack stack)
    {

        BetterRailInterfaces.logger.warn("started attempt conversion");

        ItemRollingStock.Data data = new ItemRollingStock.Data(stack);
        BetterRailInterfaces.logger.warn("converted to stack with no error");

        EntityRollingStockDefinition def = data.def;
        BetterRailInterfaces.logger.warn("got def with no error");

        try
        {
            Field privateField = EntityRollingStockDefinition.class.getDeclaredField("type");
            privateField.setAccessible(true);
            Class stockClass = (Class)(privateField.get(def));
            BetterRailInterfaces.logger.warn("ripped class from def without error");
            return stockClass;
        }
        catch (NoSuchFieldException | SecurityException | IllegalAccessException | NullPointerException e)
        {
            BetterRailInterfaces.logger.error(e);
            return null;
        }
    }

    public static List<String> DecomposeClass(Object obj)
    {
        return DecomposeClass(obj.getClass());
    }

    public static List<String> DecomposeClass(Class clazz)
    {
        List<String> strList = new ArrayList<String>();
        String first = clazz.getSimpleName();
        first += ListInterfaces(clazz);
        strList.add(first);

        Class superClass = clazz.getSuperclass();
        while(superClass != null)
        {
            String nxt = superClass.getSimpleName();
            nxt += ListInterfaces(superClass);
            strList.add(nxt);
            superClass = superClass.getSuperclass();
        }

        return strList;
    }

    public static String ListInterfaces(Class clazz)
    {
        String str = " implements ";
        if (clazz.getInterfaces().length == 0)
        {
            return "";
        }
        for (Class c : clazz.getInterfaces())
        {
            str += c.getSimpleName() + ", ";
        }
        return (str.substring(0, str.length() - 2));
    }

}

