package letiu.modbase.items;

import letiu.pistronics.data.PItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public interface IBaseItem {

	public PItem getData();
	public void setData(PItem data);
	public Icon getIcon(ItemStack stack, int pass);
}
