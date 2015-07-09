package letiu.pistronics.gui.pages;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.machines.BCreativeMachine;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageCreativeMachine extends Page {

	protected static final ResourceLocation cmButtons = new ResourceLocation("pistronics", "textures/blocks/gui/cmButtons.png");
	
	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BCreativeMachine;
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Creative Machine");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(BlockData.creativeMachine), x + 22, y + 30, 1.8F);
		
		String text1 = "This is a configureable piston and rotator in one block.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "The first two buttons and the slider are easy to understand. "
				+ "The rest is used to configure the blocks actions for specific redstone strengths. The \"*\" acts as "
				+ "a wildcard and is always used when no other action for this strength has been specified. "
				+ "To do so choose a strength with the \"<\" and ";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		// RIGHT SIDE
		
		x += 118;
		y += 5;
		
		book.drawTexture(cmButtons, x + 22, y + 50, 0, 0, 256, 256, 0, 0.6F);
		
		x += 27;
		y += 15;
		
		book.drawVerticalLine(x, y, 35);
		book.drawLine(x + 5, y - 4, 4);
		book.drawString(x + 10, y - 3, 100, 0.7F, "push/rotate");
	
		x += 16;
		y += 13;
		
		book.drawVerticalLine(x, y, 22);
		book.drawLine(x + 5, y - 3, 4);
		book.drawString(x + 10, y - 2, 100, 0.7F, "direction");
		
		x += 40;
		y += 17;
		
		book.drawVerticalLine(x, y, 5);
		book.drawString(x - 30, y - 7, 100, 0.7F, "redstone strength");
		
		y += 40;
		
		book.drawVerticalLine(x, y, 10);
		book.drawLine(x + 1, y + 6, 4);
		book.drawString(x - 24, y + 6, 100, 0.7F, "speed");
		
		x += 20;
		
		book.drawVerticalLine(x, y, 22);
		book.drawLine(x + 1, y + 18, 4);
		book.drawString(x - 27, y + 19, 100, 0.7F, "on/off");
		
		x -= 100;
		
		String text3 = "\">\" button and activate it with the button on the right. "
				+ "By default the creative machine is only available in creative mode.";
		
		book.drawString(x + 20, y + 30, 100, 0.7F, text3);
		
	}
}
