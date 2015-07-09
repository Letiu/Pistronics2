package letiu.modbase.proxies;

import java.util.List;

import letiu.modbase.blocks.SidedBlock;
import letiu.modbase.entities.BaseEntity;
import letiu.modbase.events.ViewEventHandler;
import letiu.modbase.items.BaseItem;
import letiu.modbase.items.ItemCollector;
import letiu.modbase.render.TileUniversalRenderer;
import letiu.modbase.render.UniversalEntityRenderer;
import letiu.modbase.tiles.SRInventoryTile;
import letiu.modbase.tiles.SpecialRenderTile;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PItem;
import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.render.PRenderManagerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	private SidedBlock sidedBlock;
	
	@Override
	public void init() {
		
	}
	
	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	@Override
	public void registerRenderers() {
		
		PRenderManager.init();
		PRenderManagerClient.init();
		
		// Initialize SidedBlock //
		sidedBlock = SidedBlock.createInstance();
		
		// Simple //
		List<ISimpleBlockRenderingHandler> renderers = PRenderManager.getSimpleRenderers();
		for (int i = 0; i < renderers.size(); i++) {
			int renderID = RenderingRegistry.getNextAvailableRenderId();
			PRenderManager.setRenderID(i, renderID);
			RenderingRegistry.registerBlockHandler(renderID, renderers.get(i));
		}
		
		// Tile //
		ClientRegistry.bindTileEntitySpecialRenderer(SpecialRenderTile.class, new TileUniversalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(SRInventoryTile.class, new TileUniversalRenderer());
		
		// Entity //
		RenderingRegistry.registerEntityRenderingHandler(BaseEntity.class, new UniversalEntityRenderer());
		
		// Item //
		for (BaseItem item : ItemCollector.items) {
			PItemRenderer renderer = item.data.getSpecialRenderer();
			if (renderer != null) {
				MinecraftForgeClient.registerItemRenderer(item, renderer);
			}
		}
		
		// Item Block //
		for (PItem itemBlock : ItemData.getItemBlockData()) {
			PItemRenderer renderer = itemBlock.getSpecialRenderer();
			if (renderer != null) {
				MinecraftForgeClient.registerItemRenderer((Item) itemBlock.item, renderer);
			}
		}
		
		// Entities //
		RenderingRegistry.registerEntityRenderingHandler(BaseEntity.class, new UniversalEntityRenderer());
	}

	@Override
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new ViewEventHandler());
	}

	@Override
	public SidedBlock getSidedBlock() {
		return sidedBlock;
	}
}
