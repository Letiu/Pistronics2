package letiu.modbase.items;

import letiu.pistronics.data.PItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface IBaseItem {

	public PItem getData();
	public void setData(PItem data);
	public IIcon getIcon(ItemStack stack, int pass);
}
