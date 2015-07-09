package letiu.pistronics.gui.pages;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BGear;
import letiu.pistronics.blocks.BGearPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageGear extends Page {
	
	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && (block instanceof BGear || block instanceof BGearPart);
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		ItemStack gearStack = BlockItemUtil.getStack(BlockData.gear);
		gearStack.stackTagCompound = BGear.getDefaultNBT();
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Gears");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(gearStack, x + 22, y + 30, 1.8F);
		
		String text1 = "Gears come in 3 sizes: 1x1, 3x3 and 5x5 and are currently";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "only used for crafting. Gears are made by rightclicking the"
				+ " center of a plank cuboid with a saw.";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
//		String text3 = "Rightclicking a sailpart will rotate it. Sailparts can also be dyed and camoued."
//				+ " Go build yourself a windmill!";
//		
//		book.drawString(x + 22, y + 100, 100, 0.7F, text3);
		
		x += 118;
		
		
		y += 18;
		
//		ItemStack plankStack = BlockItemUtil.getStack(ItemReference.PLANKS);
//		int gap = 8;
//		
//		int[][] blocks2 = new int[5][5];
		
		book.drawStack(BlockItemUtil.getStack(ItemData.saw), x + 62, y, 1F);
		
		x += 42;
		y += 20;
		
		for (int a = 0; a < 5; a++) {
			for (int b = 0; b < 5; b++) {
//				blocks2[a][b] = 48;
				book.drawBlockTexture(x + a * 12, y + b * 12, 48, 0.6F);
			}
		}
			
		x -= 43;
		
//		book.drawBlockArray(x + 38, y + 130, blocks2, 0.5F);
		book.drawArrow(x + 66, y + 67, 1, 0.6F);
//		}
		
		gearStack.stackTagCompound.setInteger("size", 5);
		book.drawStack(gearStack, x + 62, y + 85, 1.5F);
//		for (int a = 0; a < 5; a++) {
//			for (int b = 0; b < 5; b++) {
//				book.drawStack(plankStack, x + (a * gap), y + ((a + b) * gap), 0.8F);
//			}
//		}
//		
//		book.drawStack(BlockItemUtil.getStack(BlockData.slimeBlock, 1, 0), x + 48, y + 32, 1.5F);
//		book.drawStack(BlockItemUtil.getStack(BlockData.slimeBlock, 1, 1), x + 48, y + 64, 1.5F);
//		book.drawStack(BlockItemUtil.getStack(BlockData.slimeBlock, 1, 2), x + 48, y + 96, 1.5F);
		
	}

}