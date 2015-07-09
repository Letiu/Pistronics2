package letiu.pistronics.gui;

import letiu.modbase.gui.GuiPContainer;
import letiu.pistronics.tiles.TileRodFolder;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiRodfolder extends GuiPContainer {

	protected static final ResourceLocation advPistonGui = new ResourceLocation("pistronics", "textures/blocks/gui/rodfolder.png");
	
	protected TileRodFolder tile;
	
	public GuiRodfolder (InventoryPlayer inventoryPlayer, TileRodFolder tile) {
	    super(new ContainerRodfolder(inventoryPlayer, tile));
	    this.tile = tile;
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton) {
	
	}
	@Override
	public void initGui() {
		super.initGui();
	}

//	@Override
//	public void drawScreen(int par1, int par2, float par3) {
//		GL11.glPushMatrix();
//		
//		GL11.glScalef(4F, 4F, 4F);
//		GL11.glTranslatef(-200, -100, 0);
//		
//		super.drawScreen(par1, par2, par3);
//		
//		GL11.glPopMatrix();
//	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	    //draw text and stuff here
	    //the parameters for drawString are: string, x, y, color
		getFontRenderer().drawString("Rod Folder", 8, 6, 4210752);
//	    //draws "Inventory" or your regional equivalent
	    getFontRenderer().drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
	    //draw your Gui here, only thing you need to change is the path
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.mc.getTextureManager().bindTexture(advPistonGui);
	    //this.mc.renderEngine.bindTexture(new ResourceLocation("pistronics:));
	    int x = (width - xSize) / 2;
	    int y = (height - ySize) / 2;
	    this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
