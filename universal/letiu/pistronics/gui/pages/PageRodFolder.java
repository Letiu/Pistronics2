package letiu.pistronics.gui.pages;

import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.machines.BRodFolder;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageRodFolder extends Page {
	
	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BRodFolder;
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Rod Folder");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(BlockData.rodFolder), x + 22, y + 30, 1.8F);
		
		String text1 = "Rod Folders store rods and provide them to a connected system. This";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "solves the problem of having like 20 rods under a piston"
				+ " just to build a simple elevator. You can place them directly behind a piston"
				+ " or just anywhere. ";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		y += 100;
		
		String text3 = "Here is an example: ";
				
		book.drawString(x + 22, y + 18, 100, 0.7F, text3);
		
		int[][] blocks1 = {{6, 4, 3, 14}};
		
		book.drawBlockArray(x + 40, y + 32, blocks1, 0.75F);
		
		y -= 100;
		
		// RIGHT SIDE
		
		x += 118;
		
		String text4 = "Rodfolders also transfer redstone power when connected to a redstone extension.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text4);
		
		y += 40;
		
		String text5 = "Like this:";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text5);
		
		int[][] blocks2 = {{32, 32, 32, 17},{18, 18, 19, 30}};
		
		book.drawBlockArray(x + 40, y + 32, blocks2, 0.75F);
	}
}
