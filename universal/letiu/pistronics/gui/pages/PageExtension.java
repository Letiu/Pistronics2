package letiu.pistronics.gui.pages;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BExtension;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageExtension extends Page {

	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BExtension;
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		ItemStack extensionStack = BlockItemUtil.getStack(BlockData.extension);
		extensionStack.stackTagCompound = BExtension.getDefaultNBT();
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Extensions");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(extensionStack, 3, x + 22, y + 30, 1.8F);
		
		String text1 = "If you want to push or pull other Blocks you will need an extension.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "Extension can push up to 13 Blocks in front of them. Unlike "
				+ "vanilla piston you can also push Blocks with TileEntities like chests.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		int[][] blocks1 = {{8, 7, 8, 8, 6, 4, 2}};
		
		book.drawBlockArray(x + 38, y + 110, blocks1, 0.75F);
		book.drawArrow(x + 25, y + 110, 2, 0.6F);
		
//		String text3 = "Pushing a block inside a hopper from the top will break the block"
//				+ " and place it into the hopper.";
		
		String text3 = "Extensions can be upgraded with various items to change their behaviour and look.";
		
		book.drawString(x + 22, y + 130, 100, 0.7F, text3);
		
		// RIGHT SIDE
		
		x += 118;
		
		book.drawString(x + 22, y + 14, 100, 0.75F, "Sticky Extensions");
		book.drawLine(x + 22, y + 18);
		extensionStack.stackTagCompound.setBoolean("sticky", true);
		book.drawFramedStack(extensionStack, 3, x + 22, y + 24, 1F);
		
		String text4 = "Same as vanilla sticky piston, they will pull";
		
		book.drawString(x + 22 + 25, y + 26, 80, 0.7F, text4);
		
		String text5 = "a single block when moved backwards.";
		
		book.drawString(x + 22, y + 47, 100, 0.7F, text5);
		
		book.drawString(x + 22, y + 76, 100, 0.75F, "Super Sticky Extensions");
		book.drawLine(x + 22, y + 80);
		extensionStack.stackTagCompound.setBoolean("super_sticky", true);
		book.drawFramedStack(extensionStack, 3, x + 22, y + 86, 1F);
		
		String text6 = "Super Sticky Extension will also drag blocks";
		
		book.drawString(x + 22 + 25, y + 88, 80, 0.7F, text6);
		
		String text7 = "if the extension is not parallel to the move direction.";
		
		book.drawString(x + 22, y + 109, 100, 0.7F, text7);
		
		int[][] blocks2 = {{32, 32, 32, 8}, {2, 3, 2, 9}};
		
		book.drawBlockArray(x + 38, y + 130, blocks2, 0.76F);
		book.drawArrow(x + 87, y + 130, 0, 0.6F);
	}

}