package letiu.modbase.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class GuiPContainer extends GuiContainer {

	public GuiPContainer(Container par1Container) {
		super(par1Container);
	}

	protected FontRenderer getFontRenderer() {
		return fontRendererObj;
	}
}
