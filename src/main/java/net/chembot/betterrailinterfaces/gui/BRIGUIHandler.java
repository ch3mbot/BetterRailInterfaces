package net.chembot.betterrailinterfaces.gui;

import net.chembot.betterrailinterfaces.items.rail_interface_configurator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BRIGUIHandler implements IGuiHandler
{
    public static final int CONFIGURATOR_GUI_ID = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int i1, int i2, int i3)
    {
        if(id == CONFIGURATOR_GUI_ID)
        {
            return null;
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int i1, int i2, int i3)
    {
        if(id == CONFIGURATOR_GUI_ID)
        {
            return new rail_interface_configurator_gui(entityPlayer.inventory, entityPlayer.getHeldItem(EnumHand.MAIN_HAND));
        }
        return null;
    }
}
