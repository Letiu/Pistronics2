package letiu.pistronics.gui.pages;

import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.machines.BRotator;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageMechRotator extends Page {

	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BRotator;
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Mechanic Rotator");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(BlockData.rotator), x + 22, y + 30, 1.8F);
		
		String text1 = "As the name suggest rotators rotate Blocks! And not only";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "one block but an entire structure connected by rods and extensions. "
				+ " By default they will rotate clockwise, if you want to rotate back you will need a second one"
				+ " facing the opposite direction.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		
		int[][] blocks1 = {{32, 32, 10, 32, 32},
						   { 8,  6, 12,  5,  8},
						   {32, 32, 11, 32, 32}};
		
		book.drawBlockArray(x + 40, y + 130, blocks1, 0.75F);
		
		// RIGHT SIDE
		
		x += 118;
		
		String text4 = "There are a few blocks which will change their orientation (metadata) if rotated."
				+ " This includes most vanilla blocks aswell as all Pistroncis blocks.";
				
		book.drawString(x + 22, y + 18, 100, 0.7F, text4);	
		
		// TODO: !
//		book.drawStack(new ItemStack(Block.pistonBase), x + 25, y + 70, 0.9F);
//		book.drawStack(new ItemStack(Block.dropper), x + 45, y + 70, 0.9F);
//		book.drawStack(new ItemStack(Block.dispenser), x + 65, y + 70, 0.9F);
//		book.drawStack(new ItemStack(Blocks.rodFolder), x + 85, y + 70, 0.9F);
		
		String text5 = "If nothing happens something is in the way. Go find it!";
				
		book.drawString(x + 22, y + 90, 100, 0.7F, text5);	
		
			

	}
	
}
