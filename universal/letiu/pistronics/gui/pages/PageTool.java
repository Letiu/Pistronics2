package letiu.pistronics.gui.pages;

import net.minecraft.item.ItemStack;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.ItemReference;
import letiu.pistronics.blocks.BSailPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageTool extends Page {

	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "The Tool");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(ItemData.tool), x + 22, y + 30, 1.8F);
		
		String text1 = "The Tool is a tool to rotate stuff. Rightclick on a block and it will rotate";

		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);

		String text2 = "The way it is rotated depends on the side you clicked on."
				+ " The Tool can also be used to rotate statues on the Y-Axis.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		String text3 = "Pressing shift while using the Tool will invert the direction.";
		
		book.drawString(x + 22, y + 110, 100, 0.7F, text3);
		
		// RIGHT SIDE
		
		ItemStack sailStack = BlockItemUtil.getStack(BlockData.sailPart, 4, 0);
		sailStack.stackTagCompound = BSailPart.getDefaultNBT();
		
		x += 118;
		
		book.drawString(x + 22, y + 18, 100, 1F, "Saw");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(ItemData.saw), x + 22, y + 30, 1.8F);
		
		String text4 = "The Saw can be used to remove single parts from part blocks. Just";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text4);

		String text5 = "right-click them. The Saw is also used to craft sail parts and gears!";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text5);
		
		x -= 5;
		
		book.drawStack(BlockItemUtil.getStack(ItemReference.WOOL), x + 32, y + 110, 1F);
		
		book.drawArrow(x + 51, y + 111, 13, 0.8F);
		
		book.drawStack(BlockItemUtil.getStack(ItemData.saw), x + 64, y + 110, 1F);
		
		book.drawArrow(x + 80, y + 111, 0, 0.8F);
		
		book.drawStack(sailStack, x + 96, y + 110, 1F);
		
		x += 5;
	}
}
