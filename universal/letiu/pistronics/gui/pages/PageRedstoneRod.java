package letiu.pistronics.gui.pages;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageRedstoneRod extends Page {

//	@Override
//	public boolean isInfoAbout(int ID) {
//		return ID == Blocks.redstoneRod.blockID;
//	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		ItemStack rodStack = BlockItemUtil.getStack(BlockData.rod);
		rodStack.stackTagCompound = new NBTTagCompound();
		rodStack.stackTagCompound.setBoolean("redstone", true);
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Redstone Rods");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(rodStack, 3, x + 22, y + 30, 1.8F);
		
		String text1 = "Did you ever wanted to control pistons or other machines you pushed away";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "remotely? Redstone Rods are just for that. They behave exactly like normal rods with the exception that"
				+ " they share redstone signals with each other. To actually activate them you will need a redstone"
				+ " extension or extension part as interface with normal redstone. (see next page)";
		
		book.drawString(x + 22, y + 75, 100, 0.7F, text2);
		
		// RIGHT SIDE
		
		x += 118;
		
		String text4 = "Redstone Rods will remember the input strength. Also they can be used as wires even"
				+ " if you don't plan to move them at all.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text4);	
		
		int[][] blocks1 = {{32, 32, 22, 19, 16},
						   {32, 32, 17, 32, 32},
						   {16, 20, 21, 32, 32}};
		
		book.drawBlockArray(x + 38, y + 70, blocks1, 0.75F);
	}
}
