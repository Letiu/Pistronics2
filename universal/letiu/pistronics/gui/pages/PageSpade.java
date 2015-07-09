package letiu.pistronics.gui.pages;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageSpade extends Page {

	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Spade");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(ItemData.spade), x + 22, y + 30, 1.8F);
		
		String text1 = "The Spade is used to remove additions from blocks. Additions include:";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);

		String text2 = "glue, redstone, camoupaste, camoutexture and color.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
//		
//		String text3 = "Petrify arrows will be prioritized over normal arrows if they are on your hotbar.";
//		
//		book.drawString(x + 22, y + 130, 100, 0.7F, text3);
//		
		// RIGHT SIDE

		x += 118;

        book.drawString(x + 22, y + 18, 100, 1F, "Chisel");
        book.drawLine(x + 22, y + 24);
        book.drawFramedStack(BlockItemUtil.getStack(ItemData.chisel), x + 22, y + 30, 1.8F);

        String text3 = "Do you think statues look weird? Do they look rough in your perfectly";

        book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text3);

        String text4 = "detailed world? Yes? Then I have the perfect tool for you. Rightclick " +
                "a statue to increase the level of detail and shift rightclick to make it rougher!";

        book.drawString(x + 22, y + 72, 100, 0.7F, text4);
	}
}