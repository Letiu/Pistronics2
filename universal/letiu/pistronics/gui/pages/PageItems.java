package letiu.pistronics.gui.pages;

import letiu.pistronics.gui.GuiBookOfGears;

public class PageItems extends Page {

	@Override
	public void draw(GuiBookOfGears book, int x, int y) {

		// RIGHT SIDE
		
		x += 118;
		
		book.drawString(x + 50, y + 55, 100, 1.4F, "Items");
		book.drawLine(x + 48, y + 63, 43);
		
	}

}
