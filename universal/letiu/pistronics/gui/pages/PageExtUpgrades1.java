package letiu.pistronics.gui.pages;

import net.minecraft.item.ItemStack;
import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.blocks.BExtension;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageExtUpgrades1 extends Page {

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
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 14, 100, 0.75F, "Camou Extensions");
		book.drawLine(x + 22, y + 18);
		extensionStack.stackTagCompound.setBoolean("camou", true);
		book.drawFramedStack(extensionStack, 3, x + 22, y + 24, 1F);
		extensionStack.stackTagCompound.setBoolean("camou", false);
		
		String text4 = "Camou Extensions can mimic the Texture of any normal block. Good for hiding stuff.";
		
		
		book.drawString(x + 22 + 25, y + 26, 80, 0.7F, text4);
//		String text5 = "a single block when moved backwards.";
//		
//		book.drawString(x + 22, y + 47, 100, 0.7F, text5);
		
		book.drawString(x + 22, y + 76, 100, 0.75F, "Super Sticky Extensions");
		book.drawLine(x + 22, y + 80);
		extensionStack.stackTagCompound.setBoolean("redstone", true);
		book.drawFramedStack(extensionStack, 3, x + 22, y + 86, 1F);
		
		
		String text6 = "Enables extensions to interact with redstone. There is whole chapter about this.";
		
		book.drawString(x + 22 + 25, y + 88, 80, 0.7F, text6);
		
//		String text7 = "if the extension is not parallel to the move direction.";
//		
//		book.drawString(x + 22, y + 109, 100, 0.7F, text7);
		
		// RIGHT SIDE
		
		x += 118;
		
		book.drawString(x + 22, y + 14, 100, 0.75F, "Redioactive Extensions");
		book.drawLine(x + 22, y + 18);
		extensionStack.stackTagCompound.setBoolean("sticky", true);
		extensionStack.stackTagCompound.setBoolean("redio", true);
		book.drawFramedStack(extensionStack, 3, x + 22, y + 24, 1F);
		
		String text8 = "Can only be used with redstone extensions. Only sticky when powered.";
		
		book.drawString(x + 22 + 25, y + 26, 80, 0.7F, text8);
//		
//		String text9 = "a single block when moved backwards.";
		
//		book.drawString(x + 22, y + 47, 100, 0.7F, text9);
		
		book.drawString(x + 22, y + 76, 100, 0.75F, "Super Redio. Extensions");
		book.drawLine(x + 22, y + 80);
		extensionStack.stackTagCompound.setBoolean("super_sticky", true);
		book.drawFramedStack(extensionStack, 3, x + 22, y + 86, 1F);
		
		String text10 = "Same as above but super sticky.";
		
		book.drawString(x + 22 + 25, y + 88, 80, 0.7F, text10);
//		
//		String text11 = "if the extension is not parallel to the move direction.";
//		
//		book.drawString(x + 22, y + 109, 100, 0.7F, text11);
		
//		int[][] blocks2 = {{32, 32, 32, 8}, {2, 3, 2, 9}};
//		
//		book.drawBlockArray(x + 38, y + 130, blocks2, 0.76F);
//		book.drawArrow(x + 87, y + 130, 0, 0.6F);
	}

}