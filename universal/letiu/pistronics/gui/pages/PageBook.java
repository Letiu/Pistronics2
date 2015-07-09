package letiu.pistronics.gui.pages;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageBook extends Page {

	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "The Book of Gears");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(ItemData.bookOfGears), x + 22, y + 30, 1.8F);
		
		String text1 = "The book of gears is your guide through this mod. You can turn";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);

		String text2 = "pages by either clicking on the page or use your left/right keys (\"A\" and \"D\" by"
				+ " default). You can also click on blocks in the world to open the corresponding page if available."
				+ " The book will remember the last page you have opened unless you use shift-righclick.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
	}
}