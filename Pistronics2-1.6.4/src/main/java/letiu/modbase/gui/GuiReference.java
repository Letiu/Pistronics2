package letiu.modbase.gui;

import net.minecraft.client.Minecraft;

public class GuiReference {

	public static int getInventoryKey() {
		return Minecraft.getMinecraft().gameSettings.keyBindInventory.keyCode;
	}
	
	public static int getLeftKey() {
		return Minecraft.getMinecraft().gameSettings.keyBindLeft.keyCode;
	}
	
	public static int getRightKey() {
		return Minecraft.getMinecraft().gameSettings.keyBindRight.keyCode;
	}
	
}
