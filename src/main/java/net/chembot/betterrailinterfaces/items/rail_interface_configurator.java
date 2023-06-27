package net.chembot.betterrailinterfaces.items;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.gui.BRIGUIHandler;
import net.chembot.betterrailinterfaces.gui.rail_interface_configurator_gui;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class rail_interface_configurator extends Item
{
    private Class heldClass;
    private int levelsUp;
    private boolean modEntity;
    private Class modEntLow;
    private int modEntLevelsUp;

    public rail_interface_configurator()
    {
        setUnlocalizedName("RailInterfaceConfigurator");
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.SEARCH);
        heldClass = null;
        levelsUp = 0;
        modEntity = false;
        modEntLow = null;
        modEntLevelsUp = 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        BetterRailInterfaces.logger.info("right clicked with rail interface configurator");

        if(player.isSneaking())
        {
            if(!worldIn.isRemote)
            {
                BetterRailInterfaces.logger.info("attempting cast");
                RayTraceResult result = rayTrace(worldIn, player, true);

                if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY)
                {
                    Entity targetEntity = result.entityHit;
                    this.heldClass = targetEntity.getClass();
                    BetterRailInterfaces.logger.info("class now held: " + heldClass.getName());
                }
            }
        }
        else
        {
            if(!worldIn.isRemote)
            {
                BetterRailInterfaces.logger.info("attempting gui open");
                //player.openGui(BetterRailInterfaces.instance, BRIGUIHandler.CONFIGURATOR_GUI_ID, worldIn, 0, 0, 0);
                if(heldClass!= null)
                {
                    Minecraft.getMinecraft().displayGuiScreen(new rail_interface_configurator_gui(player.inventory, player.getHeldItem(EnumHand.MAIN_HAND), heldClass, levelsUp, modEntity, modEntLow, modEntLevelsUp));
                }
                else
                {
                    Minecraft.getMinecraft().displayGuiScreen(new rail_interface_configurator_gui(player.inventory, player.getHeldItem(EnumHand.MAIN_HAND)));
                }
            }
        }

        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
