package letiu.pistronics.gui.pages;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import letiu.pistronics.gui.GuiBookOfGears;

public abstract class Page {
	
	public abstract void draw(GuiBookOfGears book, int x, int y);
	
	public boolean isInfoAbout(World world, int x, int y, int z) {
		return false;
	}
}
