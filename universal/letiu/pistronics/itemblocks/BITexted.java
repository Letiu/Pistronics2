package letiu.pistronics.itemblocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import letiu.pistronics.data.PItemBlock;

public class BITexted extends PItemBlock {

	protected String[] text;
	
	public BITexted(String... text) {
		this.text = text;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		for (String s : text) {
			list.add(s);
		}
	}
}
