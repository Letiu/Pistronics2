package letiu.pistronics.config;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import cpw.mods.fml.common.registry.GameRegistry;
import letiu.pistronics.blocks.BStopper;
import net.minecraft.block.Block;
import letiu.pistronics.util.BlockProxy;

public class ConfigData {

	/** Whether machines emit smoke particles on failure */
	public static boolean machineSmoke = true;
	
	/** Maximum amount of Blocks moved simultaneously by one machine */
	public static int maxSystemSize = 1000; 
	
	public static double toolSpeed = 6D;
	
	public static boolean moveAttached = true;
	
	private static String blackListString = "bedrock, multipart, cccomputer";
	private static ArrayList<String> blackList = new ArrayList<String>();
	
	private static String whiteListString = "";
	private static ArrayList<String> whiteList = new ArrayList<String>();
	
	private static String modBlackListString = "";
	private static ArrayList<String> modBlackList = new ArrayList<String>();
	
	private static String modWhiteListString = "";
	private static ArrayList<String> modWhiteList = new ArrayList<String>();
	
	public static boolean creativeMachineRecipe = false;
	
	public static boolean sortTab = true;
	public static boolean sortInRows = false;
	
	public static boolean predictPlacement = true;
	
	public static int statueScaleMin = 10;
	public static int statueScaleMax = 500;
	public static int statueScaleStep = 10;
	
	public static boolean renderStatuesInInventory = true;
	public static boolean renderStatuesAsBoxes = false;
	
	public static boolean statueSmoke = true;
	
	public static boolean opaqueSlimeblocks = false;

    public static boolean showAllExtensions = true;
    public static boolean showAllExtensionParts = false;
    public static boolean showAllSails = false;

    public static boolean alternateSlimeblockRecipe = false;
	
	private static final TreeMap<String, ConfigObject> configDataMap = new TreeMap<String, ConfigObject>();
	
	public static void init() {
		// INTEGERS //
		configDataMap.put("maxBlocksMoving", new ConfigObject(maxSystemSize, "Maximum amount that can be moved at once."));
		configDataMap.put("statueScaleMin", new ConfigObject(statueScaleMin, "Minimum size for statues in percent. Cannot be lower than 1."));
		configDataMap.put("statueScaleMax", new ConfigObject(statueScaleMax, "Maximum size for statues in percent."));
		configDataMap.put("statueScaleStep", new ConfigObject(statueScaleStep, "Step by which statue scale is increased/decreased."));
		
		// BOOLEANS //
		configDataMap.put("machineSmoke", new ConfigObject(machineSmoke, "Whether machines emit smoke particles on failure."));
		configDataMap.put("moveAttached", new ConfigObject(moveAttached, "Whether blocks like torches that are attached to moving blocks should be moved."));
		configDataMap.put("creativeMachineRecipe", new ConfigObject(creativeMachineRecipe, "Enables recipe for creative machine."));
		
		configDataMap.put("sortTab", new ConfigObject(sortTab, "Sorts the creativeTab (ID independed)."));
		configDataMap.put("sortInRows", new ConfigObject(sortInRows, "If the creativeTab is sorted, this enables the use of new lines for every itemgroup."));
		configDataMap.put("predictPlacement", new ConfigObject(predictPlacement, "Tries to predict the way you want to place rods and extensions."));
		
		configDataMap.put("renderStatuesInInventory", new ConfigObject(renderStatuesInInventory, "Whether or not statues will be rendered 3D inside the inventory."));
		configDataMap.put("renderStatuesAsBoxes", new ConfigObject(renderStatuesAsBoxes, "Prevents statues from rendering normally. Statues will be displayed as boxes. Use this if you can't enter your world after petrifying an entitiy."));
		
		configDataMap.put("statueSmoke", new ConfigObject(statueSmoke, "Whether statues emit smoke particles upon creation."));
		
		configDataMap.put("opaqueSlimeblocks", new ConfigObject(opaqueSlimeblocks, "Enables this to render Slimeblocks as solid nontransparent blocks."));

		configDataMap.put("showAllExtensions", new ConfigObject(showAllExtensions, "Whether to show all available Extensions in Creative Menu"));
        configDataMap.put("showAllExtensionParts", new ConfigObject(showAllExtensionParts, "Whether to show all available Extensions Parts in Creative Menu"));
		configDataMap.put("showAllSails", new ConfigObject(showAllSails, "Whether to show all available Sails in Creative Menu"));

        configDataMap.put("alternateSlimeblockRecipe", new ConfigObject(alternateSlimeblockRecipe, "Enable to use an alternate Slimeblock recipe. (Current one conflicts with Tinkers Construct)"));

		// DOUBLES //
		configDataMap.put("toolSpeed", new ConfigObject(toolSpeed, "Speed by which the tool rotates blocks."));
		
		// STRINGS //
		configDataMap.put("blackList", new ConfigObject(blackListString, "Black List. All blocks listed here cannot be moved by pistons or rotators. "
				+ "The format is \"<unloacalized name>\" or \"<unloacalized name>:<metadata>\" devided by a ','. Don't use the prefix \"tile.\"."));
		configDataMap.put("whiteList", new ConfigObject(whiteListString, "White List. All blocks listed here can be moved by pistons or rotators. Leave this empty if you don't want to use a whitelist. "
				+ "The format is \"<unloacalized name>\" or \"<unloacalized name>:<metadata>\" devided by a ','. Don't use the prefix \"tile.\"."));
		configDataMap.put("modBlackList", new ConfigObject(modBlackListString, "Mod Black List. Blocks added by mods listed here cannot be moved by pistons or rotators. "
				+ "Make sure you are using modIDs not the names."));
		configDataMap.put("modWhiteList", new ConfigObject(modWhiteListString, "Mod White List. Blocks added by mods listed here can be moved by pistons or rotators. Leave this empty if you don't want to use a whitelist. For vanilla blocks add \"minecraft\""
				+ "Make sure you are using modIDs not the names."));
	}
	
	public static void load() {
		maxSystemSize = (Integer) getValue("maxBlocksMoving");
		machineSmoke = (Boolean) getValue("machineSmoke");
		moveAttached = (Boolean) getValue("moveAttached");
		creativeMachineRecipe = (Boolean) getValue("creativeMachineRecipe");
		sortTab = (Boolean) getValue("sortTab");
		sortInRows = (Boolean) getValue("sortInRows");
		predictPlacement = (Boolean) getValue("predictPlacement");
		renderStatuesInInventory = (Boolean) getValue("renderStatuesInInventory");
		renderStatuesAsBoxes = (Boolean) getValue("renderStatuesAsBoxes");
		statueSmoke = (Boolean) getValue("statueSmoke");
		opaqueSlimeblocks = (Boolean) getValue("opaqueSlimeblocks");
		showAllExtensions = (Boolean) getValue("showAllExtensions");
		showAllExtensionParts = (Boolean) getValue("showAllExtensionParts");
		showAllSails = (Boolean) getValue("showAllSails");
        alternateSlimeblockRecipe = (Boolean) getValue("alternateSlimeblockRecipe");

		statueScaleMin = (Integer) getValue("statueScaleMin");
		
		if (statueScaleMin < 1) statueScaleMin = 1;
		
		statueScaleMax = (Integer) getValue("statueScaleMax");
		statueScaleStep = (Integer) getValue("statueScaleStep");
		
		toolSpeed = (Double) getValue("toolSpeed");
		
		blackListString = (String) getValue("blackList");
		
		String[] names = blackListString.split(",");
		for (String name : names) {
			name = name.trim();
			if (!name.equals("")) blackList.add(name);
		}
		
		whiteListString = (String) getValue("whiteList");
		
		names = whiteListString.split(",");
		for (String name : names) {
			name = name.trim();
			if (!name.equals("")) whiteList.add(name);
		}
		
		modBlackListString = (String) getValue("modBlackList");
		
		names = modBlackListString.split(",");
		for (String name : names) {
			name = name.trim();
			if (!name.equals("")) modBlackList.add(name);
		}
		
		modWhiteListString = (String) getValue("modWhiteList");
		
		names = modWhiteListString.split(",");
		for (String name : names) {
			name = name.trim();
			if (!name.equals("")) modWhiteList.add(name);
		}
		
		// TODO: FIX FMP STUFF!
		blackList.add("tile.multipart");
	}
	
	public static Set getKeySet() {
		return configDataMap.keySet();
	}
	
	public static ConfigObject getConfigObject(String key) {
		return configDataMap.get(key);
	}
	
	public static Object getValue(String key) {
		return configDataMap.get(key).value;
	}
	
	private static boolean isBlackListed(BlockProxy proxy) {
		String name = proxy.getBlockName();
		int meta = proxy.getMetadata();
		return blackList.contains(name) || blackList.contains(name + ":" + meta);
		
	}
	
	public static boolean canBlockBeMoved(BlockProxy proxy) {
		String name = proxy.getBlockName();
		
		if (name.startsWith("tile.")) {
			name = name.substring(5);
		}
		
		Block block = proxy.getBlock();

        if (proxy.getPBlock() instanceof BStopper) return false;
		
		if (!modWhiteList.isEmpty()) {
			for (String modID : modWhiteList) {
				if (GameRegistry.findBlock(modID, name) != null) {
					
					return !blackList.contains(name);
				}
			}
			
			if (!whiteList.isEmpty()) {
				return whiteList.contains(name);
			}
			
			return false;
		}
		
		if (!modBlackList.isEmpty()) {
			for (String modID : modBlackList) {
				
				System.out.println(modID);
				System.out.println(name);
				
				if (GameRegistry.findBlock(modID, name) != null) {
					
					if (!whiteList.isEmpty()) {
						return whiteList.contains(name);
					}
					
					return false;
				}
			}
			
			return !blackList.contains(name);
		}
		
		if (!whiteList.isEmpty()) {
			return whiteList.contains(name);
		}

		return !blackList.contains(name);
	}
}
