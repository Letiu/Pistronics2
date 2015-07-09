package letiu.modbase.blocks;

import letiu.modbase.config.ConfigHandler;
import letiu.modbase.core.ModClass;
import letiu.modbase.items.BaseItemBlock;
import letiu.pistronics.data.PBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockMaker {
	
	/**
	 * Creates a vanilla block based on the data in the PBlock object.
	 */
	public static BaseBlock makeBlock(PBlock data) {
		
		// Creating block
		BaseBlock block;
		if (data.hasTileEntity()) {
			block = new TileBlock(ConfigHandler.idMap.get(data.name), VanillaData.materialData.get(data.material));
		}
		else {
			block = new BaseBlock(ConfigHandler.idMap.get(data.name), VanillaData.materialData.get(data.material));
		}
		
		// Configure block
		block.setBlockData(data);
		block.setUnlocalizedName(data.name);
		block.setHardness(data.hardness);
		block.setResistance(data.resistance);
		//block.setOpacity(data.isOpaque());
		if (data.creativeTab) block.setCreativeTab(ModClass.modTap);
		LanguageRegistry.addName(block, data.name);
		
		return block;
	}
	
	/**
	 * Adds the given Block to the GameRegistry.
	 */
	public static void registerBlock(BaseBlock block, String name) {
		GameRegistry.registerBlock(block, name);
		block.data.block = block;
	}
	
	/**
	 * Adds a special ItemBlock to the given block.
	 */
	public static void registerBlockWithItemBlock(BaseBlock block, String name) {
		GameRegistry.registerBlock(block, BaseItemBlock.class, name);
		block.data.block = block;
	}
}
