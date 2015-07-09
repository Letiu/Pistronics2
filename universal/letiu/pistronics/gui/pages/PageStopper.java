package letiu.pistronics.gui.pages;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BSlimeblock;
import letiu.pistronics.blocks.BStopper;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;
import net.minecraft.world.World;

public class PageStopper extends Page {

	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BStopper;
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Stopper");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(BlockData.stopper), x + 22, y + 30, 1.8F);
		
		String text1 = "Stoppers can't be moved. Seriously that's it. Really.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		/*String text2 = "real need to use them all their behaviour can be mimicked with extension"
				+ " parts but they might come in handy if you want to connect something fast (and cheap) at"
				+ " the cost of customizabilty and looks. They can also be used as storage blocks for slime/glue.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		x += 12;
		
		// TODO: !
		
		
		x -= 12;
		
		// RIGHT SIDE
		
		x += 118;
		
		x += 9;
		y += 10;
		
		book.drawStack(BlockItemUtil.getStack(BlockData.slimeBlock, 1, 0), x + 48, y + 32, 1.5F);
		book.drawStack(BlockItemUtil.getStack(BlockData.slimeBlock, 1, 1), x + 48, y + 64, 1.5F);
		book.drawStack(BlockItemUtil.getStack(BlockData.slimeBlock, 1, 2), x + 48, y + 96, 1.5F);
		
//		
//		String text3 = "Statues can also be camoued. Using a bucket of milk on a camoued statue will restore "
//				+ "the original texture of the creature.";
//		
//		book.drawString(x + 22, y + 18, 100, 0.7F, text3);*/
	}

}