package letiu.pistronics.gui.pages;

import letiu.pistronics.gui.GuiBookOfGears;

public class PageBlocks extends Page {

	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// RIGHT SIDE
		
		x += 118;
		
		book.drawString(x + 45, y + 55, 100, 1.4F, "Blocks");
		book.drawLine(x + 43, y + 63, 52);
		
	}
}
