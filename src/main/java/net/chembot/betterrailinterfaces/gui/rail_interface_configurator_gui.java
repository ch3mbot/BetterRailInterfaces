package net.chembot.betterrailinterfaces.gui;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class rail_interface_configurator_gui extends GuiScreen
{

    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(BetterRailInterfaces.MODID + ":textures/gui/rail_interface_configurator_gui.png");
    private final InventoryPlayer player;

    protected final int xSize = 256;
    protected final int ySize = 256;

    private Class heldClass;
    private int levelsUp;
    private boolean modEntity;
    private Class modEntLow;
    private int modEntLevelsUp;


    public rail_interface_configurator_gui(InventoryPlayer player, ItemStack configurator, Class clazz, int levelsUp, boolean modEntity, Class modEntLow, int modEntLevelsUp)
    {
        this.player = player;
        this.heldClass = clazz;
        this.levelsUp = levelsUp;
        this.modEntity = modEntity;
        this.modEntLow = modEntLow;
        this.modEntLevelsUp = modEntLevelsUp;

        BetterRailInterfaces.logger.info("rail_interface_configurator_gui constructor");
    }

    public rail_interface_configurator_gui(InventoryPlayer player, ItemStack configurator)
    {
        this.player = player;
        this.heldClass = null;
        this.levelsUp = 0;
        this.modEntity = false;
        this.modEntLow = null;
        this.modEntLevelsUp = 0;

        BetterRailInterfaces.logger.info("rail_interface_configurator_gui constructor");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - this.xSize) / 2;
        int y = (height - this.ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
