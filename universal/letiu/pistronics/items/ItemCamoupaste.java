package letiu.pistronics.items;

import java.util.List;

import letiu.pistronics.data.PItem;
import letiu.pistronics.reference.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemCamoupaste extends PItem {

	public ItemCamoupaste() {
		this.name = "Camou Paste";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.CAMOUPASTE;
	}

	@Override
	public String getIcon(ItemStack stack, int pass) {
		return this.textures[0];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		list.add(EnumChatFormatting.BLUE + "Add this to an "
				+ EnumChatFormatting.GOLD + "extension"
				+ EnumChatFormatting.BLUE + ", "
				+ EnumChatFormatting.GOLD + "machine"
				+ EnumChatFormatting.BLUE + " or "
				+ EnumChatFormatting.GOLD + "statue"
				+ EnumChatFormatting.BLUE + ".");
	}
}
