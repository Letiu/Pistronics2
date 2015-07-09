package letiu.pistronics.gui.pages;

import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.machines.BPiston;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageMechPiston extends Page {

	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BPiston;
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Mechanic Piston");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(BlockData.piston), x + 22, y + 30, 1.8F);
		
		String text1 = "Mechanic Pistons are modular versions of vanilla pistons.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "When you place them you will notice that they don't have an extension (aka pistonhead). "
				+ " That's because they are modular!";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		book.drawStack(BlockItemUtil.getStack(BlockData.rod), 3, x + 32, y + 110, 1F);
		book.drawStack(BlockItemUtil.getStack(BlockData.extension), 3, x + 48, y + 110, 1F);
		
		book.drawArrow(x + 64, y + 111, 0, 0.8F);
		
		book.drawStack(BlockItemUtil.getStack(BlockData.piston), x + 80, y + 110, 1F);
		
		String text3 = "Rightclicking with a rod or extension will place them inside the piston.";
		
		book.drawString(x + 22, y + 135, 100, 0.7F, text3);
		
		// RIGHT SIDE
		
		x += 118;
		
		String text4 = "Shift-Rightclicking with an empty hand will remove the element.";
				
		book.drawString(x + 22, y + 18, 100, 0.7F, text4);		

		int[][] blocks1 = {{2, 2, 3, 2, 2}};
		
		book.drawBlockArray(x + 40, y + 42, blocks1, 0.75F);
	    //book.drawBlockTexture(x + 40 + 24, y + 45, 0, 0.75F);
	    
	    book.drawArrow(x + 27, y + 42, 0, 0.6F);
	    book.drawArrow(x + 102, y + 42, 0, 0.6F);
		
		String text5 = "Pistons don't want to be empty, so place a rod or a block containing one behind them.";
		
		book.drawString(x + 22, y + 62, 100, 0.7F, text5);	
		
		int[][] blocks2 = {{6, 4, 2, 3, 5}};
		
		book.drawBlockArray(x + 40, y + 93, blocks2, 0.75F);
		book.drawArrow(x + 27, y + 93, 2, 0.6F);
		book.drawArrow(x + 102, y + 93, 0, 0.6F);
		
		String text6 = "Unlike vanilla pistons, mechanic pistons won't retrieve when the power is cut."
				+ " Place another piston facing the opposite direction to push everything back.";
		
		book.drawString(x + 22, y + 113, 100, 0.7F, text6);	
	}

}