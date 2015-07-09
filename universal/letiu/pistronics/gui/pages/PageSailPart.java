package letiu.pistronics.gui.pages;

import net.minecraft.item.ItemStack;
import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.blocks.BSailPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageSailPart extends Page {
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		ItemStack sailStack = BlockItemUtil.getStack(BlockData.sailPart);
		sailStack.stackTagCompound = BSailPart.getDefaultNBT();
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Sail Parts");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(sailStack, x + 22, y + 30, 1.8F);
		
		String text1 = "Sail Parts are made by simply right-clicking a wool block with a saw.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "Sailsparts behave just like any other parts with the exception that they need 2 rodparts"
				+ " to support them.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		String text3 = "Rightclicking a sailpart will rotate it. Sailparts can also be dyed and camoued."
				+ " Go build yourself a windmill!";
		
		book.drawString(x + 22, y + 100, 100, 0.7F, text3);
	}

}