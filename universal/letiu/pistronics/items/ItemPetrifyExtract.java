package letiu.pistronics.items;

import java.util.List;

import letiu.pistronics.data.PItem;
import letiu.pistronics.reference.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemPetrifyExtract extends PItem {

	public ItemPetrifyExtract() {
		this.name = "Petrify Extract";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.PETRIFY_EXTRACT;
	}

	@Override
	public String getIcon(ItemStack stack, int pass) {
		return this.textures[0];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		list.add("Used to make petrify arrows.");
	}
}
