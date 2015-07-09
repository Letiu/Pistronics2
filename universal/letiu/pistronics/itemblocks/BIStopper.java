package letiu.pistronics.itemblocks;

import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PItemBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class BIStopper extends PItemBlock {

	@Override
	public String getUnlocalizedName(String defaultName, ItemStack stack) {
		return defaultName + "." + BlockData.stopper.name;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		list.add(EnumChatFormatting.BLUE + "Just what it sounds like.");
	}
}
