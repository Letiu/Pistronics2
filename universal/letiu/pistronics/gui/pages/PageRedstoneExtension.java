package letiu.pistronics.gui.pages;

import net.minecraft.item.ItemStack;
import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.blocks.BExtension;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageRedstoneExtension extends Page {

//	@Override
//	public boolean isInfoAbout(int ID) {
//		Block block = Block.blocksList[ID];
//		if (block != null && block instanceof BlockRedstoneExt) {k
//			return true;
//		}
//		else return false;
//	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {

		ItemStack extStack = BlockItemUtil.getStack(BlockData.extension);
		extStack.stackTagCompound = BExtension.getDefaultNBT();
		extStack.stackTagCompound.setBoolean("redstone", true);
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 0.9F, "Redstone Extensions");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(extStack, 3, x + 22, y + 30, 1.8F);
		
		String text1 = "Redstone Extensions act as interfaces between redstone rods and normal";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "redstone (read the previous page for info about redstone rods). They will power"
				+ " Blocks in front of them, this is important if you want to make a setup where pistons"
				+ " push other piston. Also note that none sticky extension can be used to connect moving"
				+ " and not moving parts of a system because they will activate";
		
		book.drawString(x + 22, y + 75, 100, 0.7F, text2);
		
		// RIGHT SIDE
		
		x += 118;
		
		String text3 = "rods in front of them even if there is no direct connection.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text3);	
		
		String text4 = "Here is a schematic of a 2D-arm to give you some inspiration.";
		
		book.drawString(x + 22, y + 50, 100, 0.7F, text4);	
		
		int[][] blocks1 = {{32, 20, 44, 43, 18, 47, 32},
						   {32, 32, 37, 38, 45,  9, 32},
						   {32, 32, 33, 33, 17, 32, 32},
						   {32, 32, 39, 41, 17, 32, 32},
						   {23, 26, 33, 24, 17, 27, 16},
						   {32, 32, 46, 46, 30, 32, 32}};
		
		book.drawBlockArray(x + 25, y + 80, blocks1, 0.75F);
	}

}
