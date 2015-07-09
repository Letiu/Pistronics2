package letiu.modbase.items;

import java.util.ArrayList;

import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PItem;

public class ItemCollector {
	public static ArrayList<BaseItem> items;
	
	public static void init() {
		items = new ArrayList<BaseItem>();
	}
	
	public static void createItems() {
		ArrayList<PItem> pItems = ItemData.getItemData();
		for (PItem pItem : pItems) {
			BaseItem item = ItemMaker.makeItem(pItem);
			items.add(item);
			ItemMaker.registerItem(item, pItem.name);
		}
	}
}
