package letiu.pistronics.gui;

import letiu.modbase.gui.GuiCustomButton;
import letiu.modbase.gui.GuiCustomSlider;
import letiu.modbase.gui.GuiPContainer;
import letiu.modbase.network.PacketHandler;
import letiu.pistronics.packets.CreativeMachinePacket;
import letiu.pistronics.tiles.TileCreativeMachine;
import letiu.pistronics.util.CMRedstoneCommand;
import letiu.pistronics.util.Vector2;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiCreativeMachine extends GuiPContainer {

	protected static final ResourceLocation machineGui = new ResourceLocation("pistronics", "textures/blocks/gui/empty.png");
	
	protected TileCreativeMachine tile;
	
	protected GuiCustomButton modeButton, dirButton, continousButton, rscLeftButton, rscRightButton, activateButton;
	protected GuiCustomSlider speedSlider;
	
	protected int commandIndex = 0;
	
	public GuiCreativeMachine (InventoryPlayer inventoryPlayer, TileCreativeMachine tile) {
	    super(new ContainerCreativeMachine(inventoryPlayer, tile));
	    this.tile = tile;
	}
	
	protected void actionPerformed(GuiButton guibutton) {
        //id is the id you give your button
        switch(guibutton.id) {
        case 2:
        	tile.getCommands()[commandIndex].mode = modeButton.isInverted() ? 2 : 1;
        	modeButton.setInverted(tile.getCommands()[commandIndex].mode == 1);
        	break;
        case 3:
        	tile.getCommands()[commandIndex].direction = !tile.getCommands()[commandIndex].direction;
	    	dirButton.setInverted(tile.getCommands()[commandIndex].direction);
	    	break;
		case 4:
			// TODO
	    	break;
        case 5: commandIndex = (commandIndex + 15) % 16; 
        		setButtonValues(); break;
        case 6: commandIndex = (commandIndex + 1) % 16; 
        		setButtonValues(); break;
        case 7: 
        	if (tile.getCommands()[commandIndex] == null) {
        		tile.getCommands()[commandIndex] = new CMRedstoneCommand();
        		activateButton.setInverted(true);
        	}
        	else {
        		tile.getCommands()[commandIndex] = null;
        		activateButton.setInverted(false);
        	}
        	setButtonValues();
        }
	}
	
	@Override
	public void initGui() {
		super.initGui();

		int x = (width - xSize) / 2;
	    int y = (height - ySize) / 2;
	    
	    int uiWidth = 120;
	    
	    int xOffset = (xSize - uiWidth) / 2;
	    int yOffset = 45;
	    
		speedSlider = new GuiCustomSlider(1, x + xOffset, y + yOffset, 120, 20, 10, 3, "Speed");
		buttonList.add(speedSlider);
		
		yOffset -= 25;
		
		modeButton = new GuiCustomButton(2, x + xOffset, y + yOffset, 20, 20, "r", new Vector2(0, 0), new Vector2(20, 20));
		buttonList.add(modeButton);
		
		xOffset += 25;
		
		dirButton = new GuiCustomButton(3, x + xOffset, y + yOffset, 20, 20, "r", new Vector2(40, 20), new Vector2(60, 20));
		buttonList.add(dirButton);
		
		xOffset += 60;
		
		rscLeftButton = new GuiCustomButton(5, x + xOffset, y + yOffset, 10, 20, "<", new Vector2(80, 20), new Vector2(80, 20));
		buttonList.add(rscLeftButton);
		
		xOffset += 25;
		
		rscRightButton = new GuiCustomButton(6, x + xOffset, y + yOffset, 10, 20, ">", new Vector2(90, 20), new Vector2(90, 20));
		buttonList.add(rscRightButton);
		
		xOffset += 13;
		yOffset += 25;
		
		activateButton = new GuiCustomButton(7, x + xOffset, y + yOffset, 20, 20, "r", new Vector2(100, 20), new Vector2(120, 20));
		buttonList.add(activateButton);
		
		
		/*
		continousButton = new GuiCustomButton(4, x + xOffset, y + yOffset, 20, 20, "r", new Vector2(40, 0), new Vector2(60, 0));
		continousButton.setInverted(tile.getMode() == 1);
		buttonList.add(continousButton);
		*/
		
		setButtonValues();
	}
	
	private void setButtonValues() {
		CMRedstoneCommand command = tile.getCommands()[commandIndex];
		
		if (command != null) {
			speedSlider.enabled = true;
			modeButton.enabled = true;
			dirButton.enabled = true;
			
			speedSlider.setFloatValue(command.speed / 10F);
			speedSlider.setOption(command.speed);
			modeButton.setInverted(command.mode == 1);
			dirButton.setInverted(command.direction);
			activateButton.setInverted(true);
		}
		else {
			speedSlider.enabled = false;
			modeButton.enabled = false;
			dirButton.enabled = false;
			
			speedSlider.setFloatValue(Math.round(CMRedstoneCommand.DEFAULT_SPEED / 10F));
			speedSlider.setOption(CMRedstoneCommand.DEFAULT_SPEED);
			modeButton.setInverted(true);
			dirButton.setInverted(CMRedstoneCommand.DEFAULT_DIR);
			activateButton.setInverted(false);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	    //draw text and stuff here
	    //the parameters for drawString are: string, x, y, color
		
	    getFontRenderer().drawString("Creative Machine", 8, 6, 4210752);
	    
	    getFontRenderer().drawString(((commandIndex == 0) ? "*" : "" + commandIndex), 128 + ((commandIndex > 9) ? -3 : 0), 26, 0);
	    
//	    //draws "Inventory" or your regional equivalent
	    getFontRenderer().drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
	    //draw your Gui here, only thing you need to change is the path
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.mc.getTextureManager().bindTexture(machineGui);
	    //this.mc.renderEngine.bindTexture(new ResourceLocation("pistronics:));
	    int x = (width - xSize) / 2;
	    int y = (height - ySize) / 2;
	    this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void mouseMovedOrUp(int x, int y, int buttonID) {
    	super.mouseMovedOrUp(x, y, buttonID);
    	
    	if (buttonID == 0 && tile.getCommands()[commandIndex] != null) {
    		this.speedSlider.mouseReleased(x, y);
    		tile.getCommands()[commandIndex].speed = speedSlider.option;
    		
    		int dimID = tile.tileEntity.getWorldObj().provider.dimensionId;
    		
    		PacketHandler.sendToServer(new CreativeMachinePacket(tile, dimID));
    	}
    	
    }
}
