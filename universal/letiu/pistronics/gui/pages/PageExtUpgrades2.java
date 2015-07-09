package letiu.pistronics.gui.pages;

import net.minecraft.item.ItemStack;
import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.blocks.BExtension;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageExtUpgrades2 extends Page {

//	@Override
//	public boolean isInfoAbout(int ID) {
//		if (ID == Blocks.mechExtension.blockID 
//			|| ID == Blocks.mechExtensionSticky.blockID
//			|| ID == Blocks.mechExtensionSuperSticky.blockID) {
//			return true;
//		}
//		else return false;
//	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		ItemStack extensionStack = BlockItemUtil.getStack(BlockData.extension);
		extensionStack.stackTagCompound = BExtension.getDefaultNBT();
		extensionStack.stackTagCompound.setBoolean("redstone", true);
		extensionStack.stackTagCompound.setInteger("comp", 4);
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 14, 100, 0.75F, "Comparator Extensions");
		book.drawLine(x + 22, y + 18);
		book.drawFramedStack(extensionStack, 3, x + 22, y + 24, 1F);
		
		String text4 = "Redstone extensions can be configured to only accept/output"; 
		
		
		book.drawString(x + 22 + 25, y + 26, 80, 0.7F, text4);
		String text5 = "certain redstone strengths."
				+ " Simply rightclick a redstone extension with a comparator. The bars on the sides indicate"
				+ " the redstone strength. Note that redstone"
				+ " rods/extensions can still only transfer one signal at a time."
				+ " (In theory you can transfer 4 signals at a time using bitmasks, but that probably wouldn't be worth the effort.)";
		
		book.drawString(x + 22, y + 47, 100, 0.7F, text5);
		
		// RIGHT SIDE
		
		x += 118;
		

		String text8 = "Most upgrades can be combined. Upgrades can either be added in a crafting table"
				+ " (all at once if you want) or by rightclicking the front of the extension with the"
				+ " required item. Sticky extensions can be turned into super sticky once with red dye."
				+ " The Spade can be used to remove upgrades.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text8);

	}

}