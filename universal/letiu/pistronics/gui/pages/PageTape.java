package letiu.pistronics.gui.pages;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageTape extends Page {

//	@Override
//	public boolean isInfoAbout(int ID) {
//		return ID == Blocks.wrapper.blockID;
//	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Tape");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(BlockData.rod), x + 22, y + 30, 1.8F);
		
		String text1 = "Tape can be used to wrap any Block in a invisible box making it";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "rotateable at the cost of functionality. Ever wanted to turn an enchantmenttable upside "
				+ "down? Here you go! Simply rightclick any block while having paper in your inventory (survival).";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		// TODO: !
//		book.drawStack(new ItemStack(Items.tape), x + 32, y + 135, 1F);
//		book.drawStack(new ItemStack(Item.paper), x + 48, y + 135, 1F);
		
		book.drawArrow(x + 64, y + 136, 0, 0.8F);
		
		// TODO: !
//		book.drawStack(new ItemStack(Blocks.box), x + 80, y + 135, 1F);
		
		
		
		// RIGHT SIDE
		
		x += 118;
		
		String text3 = "Holding a roll of tape will reveal all wrapped blocks so you don't lose track. To unwrap a"
				+ " block simply rightclick again and it will return the paper.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text3);
		
		String text4 = "The Block can now be rotated with either The Tool or any kind of rotator.";
				
		book.drawString(x + 22, y + 65, 100, 0.7F, text4);		
	}

}