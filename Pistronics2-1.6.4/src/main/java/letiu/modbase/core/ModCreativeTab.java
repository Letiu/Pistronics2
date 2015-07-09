package letiu.modbase.core;

import java.util.List;

import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.CreativeTabData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.reference.ModInformation;
import letiu.pistronics.reference.TabReference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModCreativeTab extends CreativeTabs {

	private ItemStack iconStack;
	
	public ModCreativeTab() {
		super(ModInformation.NAME);
	}
	
	@Override
	public ItemStack getIconItemStack() {
		if (this.iconStack == null) {
            this.iconStack = TabReference.getCreativeTabIconStack();
        }

        return this.iconStack;
    }

	@Override
	public void displayAllReleventItems(List list) {
		if (ConfigData.sortTab) CreativeTabData.getItemsForTab(list);
		else super.displayAllReleventItems(list);
	}
}
