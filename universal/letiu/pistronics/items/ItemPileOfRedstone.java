package letiu.pistronics.items;

import java.util.List;

import letiu.pistronics.data.PItem;
import letiu.pistronics.reference.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemPileOfRedstone extends PItem {

	public ItemPileOfRedstone() {
		this.name = "Pile of Redstone";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.PILE_OF_REDSTONE;
	}

	@Override
	public String getIcon(ItemStack stack, int pass) {
		return this.textures[0];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		list.add(EnumChatFormatting.ITALIC + "A good way to make your chests");
		list.add(EnumChatFormatting.ITALIC + "look less empty.");
	}
}