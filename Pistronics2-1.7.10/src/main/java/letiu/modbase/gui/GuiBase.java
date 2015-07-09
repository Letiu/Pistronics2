package letiu.modbase.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

public class GuiBase extends GuiScreen {
	
	public void drawIcon(IIcon icon, int x, int y, float scaling) {
		
		float antiScaling = 1/scaling;
		
		GL11.glScalef(scaling, scaling, scaling);
		
		RenderItem rItem = new RenderItem();
		int localX = (int) (x * antiScaling);
		int localY = (int) (y * antiScaling);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		rItem.renderIcon(localX, localY, icon, 16, 16);
		
		GL11.glScalef(antiScaling, antiScaling, antiScaling);
	}
}
