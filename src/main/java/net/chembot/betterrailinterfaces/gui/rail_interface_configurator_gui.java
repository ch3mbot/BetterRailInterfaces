package net.chembot.betterrailinterfaces.gui;

import net.chembot.betterrailinterfaces.BetterRailInterfaces;
import net.chembot.betterrailinterfaces.StockHelpers;
import net.chembot.betterrailinterfaces.items.rail_interface_configurator;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

@SideOnly(Side.CLIENT)
public class rail_interface_configurator_gui extends GuiScreen
{

    public static final ResourceLocation BG_TEXTURE_LEFT = new ResourceLocation(BetterRailInterfaces.MODID + ":textures/gui/rail_interface_configurator_gui_left.png");
    public static final ResourceLocation BG_TEXTURE_RIGHT = new ResourceLocation(BetterRailInterfaces.MODID + ":textures/gui/rail_interface_configurator_gui_right.png");
    private final InventoryPlayer player;
    private final ItemStack itemconfigurator;

    protected final int xSize = 384;
    protected final int ySize = 192;

    private Class heldClass;
    private int levelsUp;
    private boolean modEntity;
    private Class modEntLow;
    private int modEntLevelsUp;

    private int currentMax;

    private int visibleTime;

    public rail_interface_configurator_gui(InventoryPlayer player, ItemStack configurator, Class clazz, int levelsUp, boolean modEntity, Class modEntLow, int modEntLevelsUp)
    {
        this.player = player;
        this.itemconfigurator = configurator;
        this.heldClass = clazz;
        this.levelsUp = levelsUp;
        this.modEntity = modEntity;
        this.modEntLow = modEntLow;
        this.modEntLevelsUp = modEntLevelsUp;
        this.currentMax = 0;

        BetterRailInterfaces.logger.info("rail_interface_configurator_gui constructor");
    }

    public rail_interface_configurator_gui(InventoryPlayer player, ItemStack configurator)
    {
        this.player = player;
        this.itemconfigurator = configurator;
        this.heldClass = null;
        this.levelsUp = 0;
        this.modEntity = false;
        this.modEntLow = null;
        this.modEntLevelsUp = 0;
        this.currentMax = 0;

        this.visibleTime = 0;

        BetterRailInterfaces.logger.info("rail_interface_configurator_gui constructor");
    }

    @Override
    public void initGui() {
        //super.initGui();
//        Keyboard.enableRepeatEvents(true);
//        this.buttonList.clear();

        // Add buttons or other GUI elements using addButton() method
        this.buttonList.clear();

        int windowMidX = width / 2;
        int windowMidY = height / 2;
        int tsdw = xSize / 2;
        int tsdh = ySize / 2;
        int edgeBuff = 8;
        int leftX = windowMidX - tsdw + edgeBuff;
        int rightX = windowMidX + tsdw - edgeBuff;
        int bottomY = windowMidY + tsdh - edgeBuff;
        int topY = windowMidY - tsdh + edgeBuff;

        addButton(betterConst(1, leftX, topY, 50, 20, "child"));
        addButton(betterConst(2, rightX - 60, topY, 60, 20, "parent"));

        addButton(betterConst(3, leftX, bottomY - 20, 120, 20, "load from offhand"));
        addButton(betterConst(4, rightX - 80, bottomY - 20, 80, 20, "confirm"));

    }

    private GuiButton betterConst(int id, int x, int y, int width, int height, String text)
    {
        return new GuiButton(id, x, y, width, height, text);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        ++this.visibleTime;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        BetterRailInterfaces.logger.info("levelsUp: " + levelsUp + ", currentMax: " + currentMax);
        if(button.id == 1 && levelsUp > 0)
        {
            this.levelsUp--;
        }
        else if((button.id == 2) && (levelsUp < currentMax))
        {
            this.levelsUp++;
        }
        else if (button.id == 3)
        {
            ItemStack stack = player.offHandInventory.get(0);
            Class clazz = StockHelpers.GetClass(StockHelpers.StackToBadStack(stack));
            heldClass = clazz;
            ((rail_interface_configurator)itemconfigurator.getItem()).heldClass = clazz;
        }
        else if (button.id == 4)
        {
            Class temp = heldClass;
            for(int i = 0; i < levelsUp; i++)
            {
                temp = temp.getSuperclass();
            }
            ((rail_interface_configurator)itemconfigurator.getItem()).heldClass = temp;
            BetterRailInterfaces.logger.info("set class: " + temp.getSimpleName());
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        this.drawDefaultBackground();

        mc.getTextureManager().bindTexture(BG_TEXTURE_LEFT);
        int x = (width - this.xSize) / 2;
        int y = (height - this.ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, 256, ySize);

        mc.getTextureManager().bindTexture(BG_TEXTURE_RIGHT);
        drawTexturedModalRect(x + 256, y, 0, 0, 256, ySize);


//        int xPos = (this.width - this.fontRenderer.getStringWidth("test idk")) / 2;
//        int yPos = this.height / 2 + 25;
//        this.drawString(this.fontRenderer, "test idk", xPos, yPos, TextFormatting.WHITE.getColorIndex());
        drawFullClasses();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void drawFullClasses()
    {
        if(heldClass == null)
        {
            this.drawCenteredString(this.fontRenderer, "select class", width / 2, height / 2 + 8 - ySize / 2, 0xFFFFFF);
            return;
        }
        List<String> classbits = StockHelpers.DecomposeClass(this.heldClass);
        int y = 25;
        this.currentMax = classbits.size() - 1;
        for (int i = 0; i < classbits.size(); i++)
        {
            //this.drawString(this.fontRenderer, classbits.get(i), width / 2 - xSize / 2 + 8 + 50, height / 2 + 8 - ySize / 2 + y, i == this.levelsUp ? 0x6677FF : 0x242424);
            fontRenderer.drawString(classbits.get(i), width / 2 - xSize / 2 + 8, height / 2 + 8 - ySize / 2 + y,  i == this.levelsUp ? 0x6677FF : 0x242424, false);
            y += 12;
        }
    }
}
