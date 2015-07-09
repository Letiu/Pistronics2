package letiu.modbase.core;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.CreativeTabData;
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
	public Item getTabIconItem() {
		return TabReference.getCreativeTabIconItem();
	}
	
	@SideOnly(Side.CLIENT)
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
