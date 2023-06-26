package net.chembot.betterrailinterfaces.client;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.CommonProxy;
import net.chembot.betterrailinterfaces.renders.BlockRendering;
import net.chembot.betterrailinterfaces.renders.ItemRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        BetterRailInterfaces.logger.info("Client side preinit");
        //block rendering (and block item)
        registerEvent(BlockRendering.class);
        //item rendering
        registerEvent(ItemRendering.class);

        //entity rendering (no entities yet)
        //clientevents register (no client events yet)
    }

    @Override
    public void init()
    {
        //generateteturepack? (unnecessary)

        //block rendering register colors (?)
        //item rendering register colors (?)

        //initialize player layers (no changes)
        //intialize keybinds (no binds)

        //register gui (no gui)
        //register music handler (no music)
    }

    @Override
    public void postInit()
    {

    }

    //unnecessary
    @Override
    public void sendMessage(EntityPlayer receiver, ITextComponent message)
    {
        if (this.getThePlayer() == receiver)
        {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(message);
        }
    }

    @Override
    public EntityPlayer getThePlayer()
    {
        return Minecraft.getMinecraft().player;
    }

    //where did this come from
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

}
