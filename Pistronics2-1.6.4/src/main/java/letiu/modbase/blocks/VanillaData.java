package letiu.modbase.blocks;

import java.util.TreeMap;

import net.minecraft.block.material.Material;

public class VanillaData {

	public static TreeMap<String, Material> materialData;
	
	public static void init() {
		materialData = new TreeMap<String, Material>();
		materialData.put("wood", Material.wood);
		materialData.put("iron", Material.iron);
		materialData.put("rock", Material.rock);
		materialData.put("cloth", Material.cloth);
	}
	
}
