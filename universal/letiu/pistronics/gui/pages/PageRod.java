package letiu.pistronics.gui.pages;

import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BRod;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageRod extends Page {

	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BRod;
	}

	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Rods");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(BlockData.rod), 3, x + 22, y + 30, 1.8F);
		
		String text1 = "Rods can be used to make pistons the size you want them to.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "They will connect to other rods, extensions and parts from two sides like this.";
		
		book.drawString(x + 22, y + 72, 60, 0.7F, text2);
		
		int[][] blocks1 = {{32, 2, 32}, {2, 2, 5}, {32, 2, 32}};
		
		book.drawBlockArray(x + 53 + 29, y + 94 - 22, blocks1, 0.75F);
		book.drawArrow(x + 66 + 28, y + 94 - 22, 4, 0.6F);
		book.drawArrow(x + 66 + 28, y + 118 - 22, 4, 0.6F);
		
		String text4 = "Rods can be placed inside all pistonshaped machines.";
		
		book.drawString(x + 22, y + 120, 100, 0.7F, text4);		

		book.drawStack(BlockItemUtil.getStack(BlockData.piston), x + 30, y + 137, 0.9F);
		book.drawStack(BlockItemUtil.getStack(BlockData.rotator), x + 50, y + 137, 0.9F);
		book.drawStack(BlockItemUtil.getStack(BlockData.rodFolder), x + 70, y + 137, 0.9F);
		book.drawStack(BlockItemUtil.getStack(BlockData.creativeMachine), x + 90, y + 137, 0.9F);
		
		
		// RIGHT SIDE
		
		x += 118;
		
		String text5 = "Redstone can be added by either crafting or right- clicking. See \"Redstone Rods\" chapter.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text5);
		
		y += 40;
		
		String text6 = "Rightclicking a rod with a woodenslab turns it into an extension.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text6);
		
		y += 30;
		
		String text7 = "For more advanced systems also read the \"Parts\" chapter.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text7);	
	}
	
}
