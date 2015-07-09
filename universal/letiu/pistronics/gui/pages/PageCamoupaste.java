package letiu.pistronics.gui.pages;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BCamoublock;
import letiu.pistronics.blocks.BSailPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;

public class PageCamoupaste extends Page {

	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BCamoublock;
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Camoupaste");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(BlockItemUtil.getStack(ItemData.camoupaste), x + 22, y + 30, 1.8F);
		
		String text1 = "Camoupaste is used to alter the looks of serveral blocks. Rightclicking a";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);

		String text2 = "camoued block with any normal block will make the camoued block take that appearance."
				+ " Camoupaste can be crafted into camoublocks for storage. Camoublocks can also be rightclicked"
				+ " with blocks to mimic the block on the clicked side.";
		
		book.drawString(x + 22, y + 75, 100, 0.7F, text2);
		
		
		// RIGHT SIDE
		
		x += 118;
		
		String text4 = "Camoupaste can be used on the following blocks:";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text4);
		
		int gap = 20;
		
		x -= 33;
		y -= 70;
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		BlockData.statue.getSubBlocks(0, null, list);
		ItemStack statueStack = list.get(0);
		
		ItemStack sailStack = BlockItemUtil.getStack(BlockData.sailPart);
		sailStack.stackTagCompound = BSailPart.getDefaultNBT();
				
		book.drawStack(BlockItemUtil.getStack(BlockData.piston), x + 64 , y + 110, 1F);
		book.drawStack(BlockItemUtil.getStack(BlockData.rotator), x + 64 + gap, y + 110, 1F);
		book.drawStack(BlockItemUtil.getStack(BlockData.rodFolder), x + 64 + gap + gap, y + 110, 1F);
		book.drawStack(BlockItemUtil.getStack(BlockData.creativeMachine), x + 64 + gap + gap + gap, y + 110, 1F);
		
		y += gap;
		
		book.drawStack(BlockItemUtil.getStack(BlockData.extension), x + 64, y + 110, 1F);
		book.drawStack(statueStack, x + 64 + gap, y + 110, 1F);
		book.drawStack(sailStack, x + 64 + gap + gap, y + 110, 1F);
		
		x += 33;
		
		String text5 = "Camoublock:";
		
		book.drawString(x + 51, y + 160, 100, 0.7F, text5);

		book.drawStack(BlockItemUtil.getStack(BlockData.camouBlock), x + 64, y + 170, 1F);
	}
}