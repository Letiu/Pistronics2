package letiu.pistronics.data;

import java.util.List;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.config.ConfigData;

public class CreativeTabData {

	public static void getItemsForTab(List list) {
		
		BlockItemUtil.addToTabList(BlockData.piston, list);
		BlockItemUtil.addToTabList(BlockData.rotator, list);
		BlockItemUtil.addToTabList(BlockData.rodFolder, list);
		BlockItemUtil.addToTabList(BlockData.creativeMachine, list);
		BlockItemUtil.addToTabList(BlockData.camouBlock, list);
		BlockItemUtil.addToTabList(BlockData.slimeBlock, list);
		BlockItemUtil.addToTabList(BlockData.stopper, list);

		if (ConfigData.sortInRows) newLine(list);
		
		BlockItemUtil.addToTabList(BlockData.extension, list);
		BlockItemUtil.addToTabList(BlockData.extensionPart, list);
		
		if (ConfigData.sortInRows) newLine(list);
		
		BlockItemUtil.addToTabList(BlockData.rod, list);
		BlockItemUtil.addToTabList(BlockData.rodPart, list);
		
		if (ConfigData.sortInRows) newLine(list);
		
		BlockItemUtil.addToTabList(BlockData.gear, list);
		BlockItemUtil.addToTabList(BlockData.sailPart, list);
		BlockItemUtil.addToTabList(BlockData.statue, list);
		
		if (ConfigData.sortInRows) newLine(list);
		
		BlockItemUtil.addToTabList(ItemData.camoupaste, list);
		BlockItemUtil.addToTabList(ItemData.pileOfRedstone, list);
		BlockItemUtil.addToTabList(ItemData.glue, list);
		BlockItemUtil.addToTabList(ItemData.super_glue, list);
		BlockItemUtil.addToTabList(ItemData.redioGlue, list);
		BlockItemUtil.addToTabList(ItemData.redioSuperGlue, list);
		BlockItemUtil.addToTabList(ItemData.petrifyExtract, list);
		BlockItemUtil.addToTabList(ItemData.petrifyArrow, list);
		
		if (ConfigData.sortInRows) newLine(list);
		
		BlockItemUtil.addToTabList(ItemData.tool, list);
		BlockItemUtil.addToTabList(ItemData.saw, list);
		BlockItemUtil.addToTabList(ItemData.spade, list);
        BlockItemUtil.addToTabList(ItemData.chisel, list);
		BlockItemUtil.addToTabList(ItemData.bookOfGears, list);
	}
	
	private static void newLine(List list) {
		int spaces = 9 - (list.size() % 9);
		if (spaces != 9) {
			for (int i = 0; i < spaces; i++) list.add(null);
		}
	}
	
}
