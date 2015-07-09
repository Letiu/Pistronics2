package letiu.pistronics.gui;

import java.util.List;

import letiu.modbase.gui.GuiBase;
import letiu.modbase.gui.GuiReference;
import letiu.pistronics.gui.pages.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

public class GuiBookOfGears extends GuiBase {

	protected static final ResourceLocation bookTexture = new ResourceLocation("pistronics", "textures/blocks/gui/BookOfGears.png");
	protected static final ResourceLocation frameTexture = new ResourceLocation("pistronics", "textures/blocks/gui/frame02.png");
	protected static final ResourceLocation arrowTexture = new ResourceLocation("pistronics", "textures/blocks/gui/testIcon.png");
	protected static final ResourceLocation blockTextures = new ResourceLocation("pistronics", "textures/blocks/gui/blockTextures.png");
	
	protected static int index;
	
	protected FontRenderer fontRenderer;
	protected RenderBlocks renderBlocks;
	
	protected int xSize = 256, ySize = 200;
	protected Page[] pages;
	protected EntityPlayer player;
	
	public GuiBookOfGears(EntityPlayer player) {

		this.player = player;
		init();
		
		if (player.isSneaking()) {
			index = 0;
		}
	}
	
	public GuiBookOfGears(World world, int x, int y, int z, EntityPlayer player) {

		this.player = player;
		init();
		
		int i = getPageFor(world, x, y, z);
		if (i != 0) {
			this.index = i;
		}
	}
	
	private void init() {
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
		this.renderBlocks = new RenderBlocks();
		
		pages = new Page[23];
		int i = 0;
		pages[i++] = new PageBook();
		// TODO: INDEX 
		pages[i++] = new PageBlocks();
		pages[i++] = new PageMechPiston();
		pages[i++] = new PageRod();
		pages[i++] = new PageExtension();
		pages[i++] = new PageExtUpgrades1();
		pages[i++] = new PageExtUpgrades2();
		pages[i++] = new PageMechRotator();
		pages[i++] = new PageParts();
		pages[i++] = new PageRodFolder();
		pages[i++] = new PageCreativeMachine();
		pages[i++] = new PageRedstoneRod();
		pages[i++] = new PageRedstoneExtension();
		pages[i++] = new PageGear();
		pages[i++] = new PageSailPart();
		pages[i++] = new PageStatue();
		pages[i++] = new PageSlimeblock();
		pages[i++] = new PageStopper();
		pages[i++] = new PageItems();
		pages[i++] = new PageTool();
		pages[i++] = new PageSpade();
		pages[i++] = new PageCamoupaste();
		pages[i++] = new PagePetrifyArrow();
		//pages[i++] = new PageTape();
	}
	
	private int getPageFor(World world, int x, int y, int z) {
		for (int i = 0; i < pages.length; i++) {
			if (pages[i].isInfoAbout(world, x, y, z)) {
				return i;
			}
		}
		return 0;
	}
	
	
//	private void saveIndex() {
//		ItemStack item = player.getCurrentEquippedItem();
//		if (item != null && CompareUtil.compareIDs(item, ItemData.bookOfGears.item)) {
//			if (item.stackTagCompound == null) {
//				 item.stackTagCompound = new NBTTagCompound();
//			}
//			item.stackTagCompound.setInteger("index", index);
//		}
//	}
//	
//	private void loadIndex() {
//		ItemStack item = player.getCurrentEquippedItem();
//		if (item != null && CompareUtil.compareIDs(item, ItemData.bookOfGears.item)) {
//			if (item.stackTagCompound == null) {
//				 item.stackTagCompound = new NBTTagCompound();
//				 index = 0;
//			}
//			else {
//				this.index = item.stackTagCompound.getInteger("index");
//			}
//		}
//	}
	
	@Override
	public void initGui() {
		super.initGui();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	protected void keyTyped(char par1, int par2) {
		
		if (par2 == 1 || par2 == GuiReference.getInventoryKey()) {
			this.mc.thePlayer.closeScreen();
		}
		else if (par2 == GuiReference.getLeftKey()) {
			if (index > 0) {
				index--;
			}
		}
		else if (par2 == GuiReference.getRightKey()) {
			if (index < pages.length - 1) {
				index++;
			}
		}
	}
	
	protected void onPageChanged() {
	
	}
	 
	@Override
	protected void mouseMovedOrUp(int x, int y, int buttonID) {
		super.mouseMovedOrUp(x, y, buttonID);
		if (buttonID == 0) {
			int xBook = (width - xSize) / 2;
		    int yBook = (height - ySize) / 2;
			if (x > xBook && x < xBook + 128 && y > yBook && y < yBook + 180) {
				if (index > 0) {
					index--;
					onPageChanged();
				}
			}
			if (x >= xBook + 128 && x < xBook + 256 && y > yBook && y < yBook + 180) {
				if (index < pages.length - 1) {
					index++;
					onPageChanged();
				}
			}
		}	
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.mc.getTextureManager().bindTexture(bookTexture);
	    int x = (width - xSize) / 2;
	    int y = (height - ySize) / 2;
	    this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	    
	    pages[index].draw(this, x, y);
	    
		super.drawScreen(par1, par2, par3);
	}
	
	public void drawArrow(int x, int y, int dir, float scaling) {
		float antiScaling = 1/scaling;
		
		GL11.glScalef(scaling, scaling, scaling);
		
		int localX = (int) (x * antiScaling);
		int localY = (int) (y * antiScaling);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(arrowTexture);
		int xOffset = (dir <= 12) ? 80 : 0;
		this.drawTexturedModalRect(localX, localY, xOffset + 20 * dir, 20 * (dir / 12), 20, 20);
		
		GL11.glScalef(antiScaling, antiScaling, antiScaling);
	}
	
	public void drawLine(int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(bookTexture);
		this.drawTexturedModalRect(x - 2, y, 10, 0, 100, 4);
	}
	
	public void drawLine(int x, int y, int length) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(bookTexture);
		this.drawTexturedModalRect(x - 2, y, 10, 0, length, 4);
	}
	
	public void drawVerticalLine(int x, int y, int length) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(bookTexture);
		this.drawTexturedModalRect(x, y, 0, 10, 4, length);
	}
	
	public void drawFramedStack(ItemStack stack, int x, int y, float scaling) {
		drawFramedStack(stack, 0, x, y, scaling);
	}
	
	public void drawFramedStack(ItemStack stack, int meta, int x, int y, float scaling) {
		drawFrame(x, y, scaling / 3F);
		int localX = x + (int) (3F * scaling);
		int localY = y + (int) (3F* scaling);
		drawStack(stack, meta, localX, localY, scaling);
	}
	
	public void drawFrame(int x, int y, float scaling) {
		
		float antiScaling = 1/scaling;
		
		GL11.glScalef(scaling, scaling, scaling);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(frameTexture);
		this.drawTexturedModalRect((int) (x * antiScaling), (int) (y * antiScaling), 0, 0, 64, 64);
		
		GL11.glScalef(antiScaling, antiScaling, antiScaling);
	}
	
	public void drawTexture(ResourceLocation texture, int x, int y, int u, int v, int width, int height, int dir, float scaling) {
		float antiScaling = 1/scaling;
		
		GL11.glScalef(scaling, scaling, scaling);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect((int) (x * antiScaling), (int) (y * antiScaling), u, v, width, height);
		
		GL11.glScalef(antiScaling, antiScaling, antiScaling);
	}
	
	public void drawBlockTexture(int x, int y, int id, float scaling) {
		int u = (id * 16) % 256;
		int v = (id / 16) * 16;
		this.drawTexture(blockTextures, x, y, u, v, 16, 16, id, scaling);
	}
	
	public void drawBlockArray(int x, int y, int[][] ids, float scaling) {
		
		float antiScaling = 1/scaling;
		
		GL11.glScalef(scaling, scaling, scaling);
		
		int localX = (int) (x * antiScaling);
		int localY = (int) (y * antiScaling);
		
		for (int xID = 0; xID < ids.length; xID++) {
			for (int yID = 0; yID < ids[0].length; yID++) {
				drawBlockTexture(localX + 16 * yID, localY + 16 * xID, ids[xID][yID], 1F);
			}
		}
		
		GL11.glScalef(antiScaling, antiScaling, antiScaling);
	}
	
	public void drawStack(ItemStack stack, int x, int y, float scaling) {
		drawStack(stack, 0, x, y, scaling);
	}
	
	public void drawStack(ItemStack stack, int meta, int x, int y, float scaling) {
		
		float antiScaling = 1/scaling;

		GL11.glScalef(scaling, scaling, scaling);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				
		int localX = (int) (x * antiScaling);
		int localY = (int) (y * antiScaling);
		
		if (!ForgeHooksClient.renderInventoryItem(renderBlocks, this.mc.getTextureManager(), stack, true, 200, localX, localY)) {
			RenderItem rItem = new RenderItem();
			rItem.renderItemIntoGUI(fontRenderer, this.mc.getTextureManager(), stack, localX, localY);			
		}

		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glScalef(antiScaling, antiScaling, antiScaling);
		
   		GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
	}

	public void drawString(int x, int y, int width, float scaling, String text) {
		float antiScaling = 1/scaling;
		
		GL11.glScalef(scaling, scaling, scaling);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		List<String> list = fontRenderer.listFormattedStringToWidth(text, (int) (width * antiScaling));
		for (int i = 0; i < list.size(); i++) {
			int localX = (int) (x * antiScaling);
			int localY = (int) ((y * antiScaling) + i * (fontRenderer.FONT_HEIGHT + 1));
			fontRenderer.drawString(list.get(i), localX, localY, 4210752);
		}
		GL11.glScalef(antiScaling, antiScaling, antiScaling);
		
	}
	
	
//	@Override
//	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
//	    //draw text and stuff here
//	    //the parameters for drawString are: string, x, y, color
//	    fontRenderer.drawString("Rod Folder", 8, 6, 4210752);
//	    //draws "Inventory" or your regional equivalent
//	    fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
//	}
//
//	@Override
//	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
//	    //draw your Gui here, only thing you need to change is the path
//	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//	    this.mc.getTextureManager().bindTexture(advPistonGui);
//	    //this.mc.renderEngine.bindTexture(new ResourceLocation("pistronics:));
//	    int x = (width - xSize) / 2;
//	    int y = (height - ySize) / 2;
//	    this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
//	}
}
