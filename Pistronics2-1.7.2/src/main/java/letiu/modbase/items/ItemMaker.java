package letiu.modbase.items;

import letiu.modbase.core.ModClass;
import letiu.pistronics.data.PItem;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemMaker {

	/**
	 * Creates a vanilla item based on the data in the PItem object.
	 */
	public static BaseItem makeItem(PItem data) {
		
		// Creating item //
		BaseItem item;
		
		item = new BaseItem();
		
		// Configure item //
		item.data = data;
		item.setMaxStackSize(data.getMaxStackSize());
		if (data.creativeTab) item.setCreativeTab(ModClass.modTap);
		
		return item;
	}
	
	/**
	 * Adds the given Block to the GameRegistry.
	 */
	public static void registerItem(BaseItem item, String name) {
		GameRegistry.registerItem(item, name);
		item.data.item = (IBaseItem) item;
	}
}
