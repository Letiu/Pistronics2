package letiu.modbase.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import net.minecraftforge.common.Configuration;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.config.ConfigObject;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;

public class ConfigHandler {


	public static TreeMap<String, Integer> idMap = new TreeMap<String, Integer>();
	
	public static void init(File configFile) {
		
		// create configuration object from the given configuration file
		Configuration configuration = new Configuration(configFile);
	
		String TWEAKS = "TWEAKS";
		String BLOCKS = "BLOCKS";
		String ITEMS = "ITEMS";
		String ID_OFFSET = "A_ID_OFFSETS";
		
		int idCount = 500;
		
		try {
			// Load the configuration file
			configuration.load();
			
			int blockOffset = configuration.get(ID_OFFSET, "BlockIDOffset", 0).getInt();
			int itemOffset = configuration.get(ID_OFFSET, "ItemIDOffset", 0).getInt() - 256;

			// Read in properties from configuration file
			ArrayList<PBlock> blocks = BlockData.getBlockData();
			for (PBlock block : blocks) {
				idMap.put(block.name, configuration.get(BLOCKS, block.name, idCount++).getInt() + blockOffset);
			}
			
			idCount = 10000;
			
			ArrayList<PItem> items = ItemData.getItemData();
			for (PItem item : items) {
				idMap.put(item.name, configuration.get(ITEMS, item.name, idCount++).getInt() + itemOffset);
			}
			
			Set keys = ConfigData.getKeySet();
			
			for (Object key : keys) {
				ConfigObject co = ConfigData.getConfigObject((String) key);
				if (co.value instanceof Integer) {
					co.value = configuration.get(TWEAKS, (String) key, (Integer) co.value, co.comment).getInt();
				}
				else if (co.value instanceof Boolean) {
					co.value = configuration.get(TWEAKS, (String) key, (Boolean) co.value, co.comment).getBoolean((Boolean) co.value);
				}
				else if (co.value instanceof Double) {
					co.value = configuration.get(TWEAKS, (String) key, (Double) co.value, co.comment).getDouble(6D);
				}
				else if (co.value instanceof String) {
					co.value = configuration.get(TWEAKS, (String) key, (String) co.value, co.comment).getString();
				}
			}
		}
		catch(Exception e) {
			// Log the exception
		}
		finally {
			// Save the configuration file
			configuration.save();
		}
	}
	
}
