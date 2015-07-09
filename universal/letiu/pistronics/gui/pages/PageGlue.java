package letiu.pistronics.gui.pages;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageGlue extends Page {

	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Glue");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(ItemData.glue), x + 22, y + 30, 1.8F);
		
		String text1 = "Glue can be used as substitute for slimeballs in crafting recipes.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		book.drawString(x + 22, y + 90, 100, 1F, "Super Glue");
		book.drawLine(x + 22, y + 96);
		book.drawFramedStack(BlockItemUtil.getStack(ItemData.glue), x + 22, y + 102, 1.8F);
		
		String text2 = "Super Glue is used to make Super Sticky Extension (see Blocks)";
		
		book.drawString(x + 22 + 45, y + 104, 55, 0.7F, text2);
		// RIGHT SIDE
		
		x += 118;
		
		book.drawString(x + 22, y + 18, 100, 1F, "Super Glue");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(ItemData.glue), x + 22, y + 30, 1.8F);
		
		String text3 = "Mechanic Pistons are modular versions of vanilla pistons.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text3);
		
		String text4 = "When you place them you will notice that they don't have an extension (aka pistonhead). "
				+ " That's because they are modular!";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text4);
	}
}
