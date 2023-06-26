package net.chembot.betterrailinterfaces.items;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class rail_interface_configurator extends Item
{
    public rail_interface_configurator()
    {
        setUnlocalizedName("RailInterfaceConfigurator");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        BetterRailInterfaces.logger.info("right clicked with rail interface configurator");
        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
