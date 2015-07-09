package letiu.pistronics.gui.pages;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PagePetrifyArrow extends Page {

	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Petrify Arrows");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(ItemData.petrifyArrow), x + 22, y + 30, 1.8F);
		
		String text1 = "Arrows to turn anything living into stone! Problem is that most creatures";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);

		String text2 = "don't want to be petrified. In order to turn them into cute statues your poke.. "
				+ "*cough* arrow needs to kill them. Be careful this is a very dangerous process and might damage "
				+ "nearby blocks.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		String text3 = "Petrify arrows will be prioritized over normal arrows if they are on your hotbar.";
		
		book.drawString(x + 22, y + 130, 100, 0.7F, text3);
		
		// RIGHT SIDE
		
		x += 118;
		
		String text4 = "Petrify arrows can also be used in close combat without a bow, but that doesn't seem "
				+ "to be an efficient way of using them.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text4);
	}
}