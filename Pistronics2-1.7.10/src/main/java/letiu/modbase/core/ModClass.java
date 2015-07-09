package letiu.modbase.core;

import letiu.modbase.blocks.BlockCollector;
import letiu.modbase.blocks.VanillaData;
import letiu.modbase.config.ConfigHandler;
import letiu.modbase.entities.EntityCollector;
import letiu.modbase.events.ArrowEventHandler;
import letiu.modbase.integration.nei.RecipeHandlers;
import letiu.modbase.items.ItemCollector;
import letiu.modbase.proxies.IProxy;
import letiu.modbase.render.TextureMapper;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.*;
import letiu.pistronics.recipes.Recipes;
import letiu.pistronics.reference.ModInformation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION)
public class ModClass {

	@Instance(ModInformation.ID)
	public static ModClass instance;
	
	@SidedProxy(clientSide = ModBaseInfo.CLIENT_PROXY, serverSide = ModBaseInfo.SERVER_PROXY)
	public static IProxy proxy;
	
	public static CreativeTabs modTap = new ModCreativeTab();
	
	public static ArrowEventHandler arrowEventHandler = new ArrowEventHandler();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// config
		ConfigData.init();
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		ConfigData.load();
		
		// modbase init
		VanillaData.init();
		TextureMapper.init();
		BlockCollector.init();
		ItemCollector.init();
		
		// pistronics init
		BlockData.init();
		ItemData.init();
		EntityData.init();
//		PRenderManager.init();

		// create/register stuff
		BlockCollector.createBlocks();
		ItemCollector.createItems();
		EntityCollector.registerEntities();

		// Potions
//		BasePotion.expandPotionArray();
//		PotionData.init();
		
		// proxy
		proxy.init();
		proxy.registerRenderers();
		proxy.registerEvents();
		
		Recipes.registerRecipes();
        //NEIWrapper.addRecipeHandlers();
		
		//MinecraftForge.EVENT_BUS.register(new SystemController());
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerTileEntities();
		PacketData.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		MinecraftForge.EVENT_BUS.register(arrowEventHandler);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
        RecipeHandlers.registerHandlers();

	}
}