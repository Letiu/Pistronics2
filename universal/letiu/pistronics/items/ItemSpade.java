package letiu.pistronics.items;

import java.util.List;

import letiu.pistronics.data.PItem;
import letiu.pistronics.reference.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemSpade extends PItem {

	public ItemSpade() {
		this.name = "Spade";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.SPADE;
	}

	@Override
	public String getIcon(ItemStack stack, int pass) {
		return this.textures[0];
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		list.add(EnumChatFormatting.BLUE + "Can be used to remove stuff from ");
		list.add(EnumChatFormatting.GOLD + "rods"
				+ EnumChatFormatting.BLUE + ", "
				+ EnumChatFormatting.GOLD + "extension"
				+ EnumChatFormatting.BLUE + ", "
				+ EnumChatFormatting.GOLD + "machines"
				+ EnumChatFormatting.BLUE + " and "
				+ EnumChatFormatting.GOLD + "statues"
				+ EnumChatFormatting.BLUE + ".");
	}
}
