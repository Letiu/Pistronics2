package letiu.modbase.gui;

import net.minecraft.client.Minecraft;

public class GuiReference {

	public static int getInventoryKey() {
		return Minecraft.getMinecraft().gameSettings.keyBindInventory.getKeyCode();
	}
	
	public static int getLeftKey() {
		return Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode();
	}
	
	public static int getRightKey() {
		return Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode();
	}
	
}
