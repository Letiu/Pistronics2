package letiu.pistronics.itemblocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import letiu.pistronics.data.PItemBlock;

public class BISlimeblock extends PItemBlock {

	private static final String[] names = {"slime", "glue", "superglue"};
	
	@Override
	public String getUnlocalizedName(String defaultName, ItemStack stack) {
		int itemDamage = stack.getItemDamage();
		if (itemDamage < 0 || itemDamage > names.length) return defaultName;
		return defaultName + "." + names[stack.getItemDamage()];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		String color = "";
		switch (stack.getItemDamage()) {
		case 0: color += EnumChatFormatting.GREEN; break;
		case 1: color += EnumChatFormatting.YELLOW; break;
		case 2: color += EnumChatFormatting.RED; break;
		}
		
		list.add(color + "Sticky on all sides! °O°");
	}
	
	@Override
	public boolean getHasSubtypes() {
		return true;
	}
	
	@Override
	public int getDamageValue(int damageValue) {
		return damageValue;
	}
}
