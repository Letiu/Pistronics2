package letiu.pistronics.gui.pages;

import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BPartblock;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageParts extends Page {

	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BPartblock;
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Parts");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(BlockData.rodPart), x + 22, y + 30, 1.8F);
		
		String text1 = "Parts can be added together to create custom blocks.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "Up to 6 parts (one for each) side can be put together, just rightclick with a part"
				+ " on another one or rod or extension and it will be added. Adding parts to a rod or extension will turn"
				+ " them into parts themself.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		// RIGHT SIDE
		
		x += 118;
		
		String text4 = "Rightclicking with a rod or extension on another compatible block will also turn"
				+ " them directly into parts.";
				
		book.drawString(x + 22, y + 18, 100, 0.7F, text4);		

		
		String text5 = "For example you could add 3 rods together to form a part block connecting to all"
				+ " 6 sides!";
		
		book.drawString(x + 22, y + 62, 100, 0.7F, text5);	
		
		y += 45;
		
		String text6 = "Parts can be removed with a saw.";
		
		book.drawString(x + 22, y + 62, 100, 0.7F, text6);	
	}
}
