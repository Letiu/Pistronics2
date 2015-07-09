package letiu.pistronics.gui.pages;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BStatue;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.gui.GuiBookOfGears;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PageStatue extends Page {

	@Override
	public boolean isInfoAbout(World world, int x, int y, int z) {
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		return block != null && block instanceof BStatue;
	}
	
	@Override
	public void draw(GuiBookOfGears book, int x, int y) {
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		BlockData.statue.getSubBlocks(0, null, list);
		ItemStack statueStack = list.get(0);
		
		// LEFT SIDE
		
		book.drawString(x + 22, y + 18, 100, 1F, "Statues");
		book.drawLine(x + 22, y + 24);
		book.drawFramedStack(statueStack, x + 22, y + 30, 1.8F);
		
		String text1 = "Killing a living creature with a petrify arrow creates a statue.";
		
		book.drawString(x + 22 + 45, y + 32, 55, 0.7F, text1);
		
		String text2 = "Statues can be picked up and placed anywhere you like. They can be manually rotated"
				+ " with the tool. The size of a statue can be altered by feeding it food (good food will make it"
				+ " grow, bad food will make it shrink).";
		
		book.drawString(x + 22, y + 72, 100, 0.7F, text2);
		
		x += 12;
		
		// TODO: !
//		book.drawStack(BlockItemUtil.getStack(ItemReference.), x + 30, y + 132, 1F);
//		book.drawStack(new ItemStack(Item.rottenFlesh), x + 48, y + 132, 1F);
//		book.drawStack(new ItemStack(Item.poisonousPotato), x + 66, y + 132, 1F);
		
		x -= 12;
		
		// RIGHT SIDE
		
		x += 118;
		
		String text3 = "Statues can also be camoued. Using a bucket of milk on a camoued statue will restore "
				+ "the original texture of the creature.";
		
		book.drawString(x + 22, y + 18, 100, 0.7F, text3);
	}

}