package letiu.modbase.blocks;

import java.util.ArrayList;

import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;

public class BlockCollector {

	public static ArrayList<BaseBlock> blocks;
	
	public static void init() {
		blocks = new ArrayList<BaseBlock>();
	}
	
	public static void createBlocks() {
		ArrayList<PBlock> pBlocks = BlockData.getBlockData();
		for (PBlock pBlock : pBlocks) {
			BaseBlock block = BlockMaker.makeBlock(pBlock);
			blocks.add(block);
			
			if (block.getItemBlock() == null) {
				BlockMaker.registerBlock(block, pBlock.name);
			}
			else {
				BlockMaker.registerBlockWithItemBlock(block, pBlock.name);
			}
		}
	}
}
