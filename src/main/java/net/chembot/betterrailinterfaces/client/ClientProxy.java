package net.chembot.betterrailinterfaces.client;

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
        //block rendering (and block item)
        registerEvent(BlockRendering.class);
        //item rendering
        registerEvent(ItemRendering.class);
    }

    @Override
    public void init()
    {

    }

    @Override
    public void postInit()
    {

    }

    //unnecessary
    @Override
    public EntityPlayer getThePlayer()
    {
        return Minecraft.getMinecraft().player;
    }
}
