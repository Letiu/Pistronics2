package letiu.pistronics.render;

import java.util.ArrayList;
import java.util.List;

import letiu.pistronics.render.entities.EntityPetrifyArrowRenderer;
import letiu.pistronics.render.items.ExtensionItemRenderer;
import letiu.pistronics.render.items.GearItemRenderer;
import letiu.pistronics.render.items.RodItemRenderer;
import letiu.pistronics.render.items.SailItemRenderer;
import letiu.pistronics.render.items.StatueItemRenderer;
import letiu.pistronics.render.simple.ExtensionRenderer;
import letiu.pistronics.render.simple.MechPistonRenderer;
import letiu.pistronics.render.simple.PartRenderer;
import letiu.pistronics.render.simple.RodRenderer;
import letiu.pistronics.render.simple.SailRenderer;
import letiu.pistronics.render.simple.SlimeblockRenderer;
import letiu.pistronics.render.tiles.ElementRenderer;
import letiu.pistronics.render.tiles.GearRenderer;
import letiu.pistronics.render.tiles.MechElementRenderer;
import letiu.pistronics.render.tiles.MotionRenderer;
import letiu.pistronics.render.tiles.RodFolderElementRenderer;
import letiu.pistronics.render.tiles.StatueRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class PRenderManager {

	private static boolean initialized = false;
	
	public static PSimpleRenderer pistonRenderer, rodRenderer, extensionRenderer, partRenderer, slimeblockRenderer, sailRenderer;
	public static PTileRenderer elementRenderer, motionRenderer, mechElementRenderer, gearRenderer, statueRenderer, rodFolderElementRenderer;
	public static PItemRenderer extensionItemRenderer, rodItemRenderer, sailItemRenderer, gearItemRenderer, statueItemRenderer;
	
	private static List<ISimpleBlockRenderingHandler> simpleRenderers;
	private static List<PTileRenderer> tileRenderers;
	private static List<PItemRenderer> itemRenderers;
	
	public static int getRenderID(PSimpleRenderer renderer) {
		if (initialized && renderer != null) {
			return renderer.renderID;
		}
		else return 0;
	}
	
	public static void init() {
		
		initialized = true;
		
		simpleRenderers = new ArrayList<ISimpleBlockRenderingHandler>();
		tileRenderers = new ArrayList<PTileRenderer>(); 
		itemRenderers = new ArrayList<PItemRenderer>();
		
		// Simple //
		pistonRenderer = new MechPistonRenderer();
		simpleRenderers.add(pistonRenderer);
		
		extensionRenderer = new ExtensionRenderer();
		simpleRenderers.add(extensionRenderer);
		
		rodRenderer = new RodRenderer();
		simpleRenderers.add(rodRenderer);
		
		partRenderer = new PartRenderer();
		simpleRenderers.add(partRenderer);
		
		slimeblockRenderer = new SlimeblockRenderer();
		simpleRenderers.add(slimeblockRenderer);

		sailRenderer = new SailRenderer();
		simpleRenderers.add(sailRenderer);
		
		// Tile //
		elementRenderer = new ElementRenderer();
		tileRenderers.add(elementRenderer);
		
		motionRenderer = new MotionRenderer();
		tileRenderers.add(motionRenderer);
		
		mechElementRenderer = new MechElementRenderer();
		tileRenderers.add(mechElementRenderer);
		
		gearRenderer = new GearRenderer();
		tileRenderers.add(gearRenderer);
		
		statueRenderer = new StatueRenderer();
		tileRenderers.add(statueRenderer);
		
		rodFolderElementRenderer = new RodFolderElementRenderer();
		tileRenderers.add(rodFolderElementRenderer);
		
		// Item //
		extensionItemRenderer = new ExtensionItemRenderer();
		itemRenderers.add(extensionItemRenderer);
		
		rodItemRenderer = new RodItemRenderer();
		itemRenderers.add(rodItemRenderer);
		
		sailItemRenderer = new SailItemRenderer();
		itemRenderers.add(sailItemRenderer);
		
		gearItemRenderer = new GearItemRenderer();
		itemRenderers.add(gearItemRenderer);
		
		statueItemRenderer = new StatueItemRenderer();
		itemRenderers.add(statueItemRenderer);
		
		
	}
	
	public static int getRendererAmount() {
		if (simpleRenderers == null) return 0;
		else return simpleRenderers.size();
	}
	
	public static void setRenderID(int index, int ID) {
		if (index < 0 || index > simpleRenderers.size()) {
			throw new IllegalArgumentException("index out of bound");
		}
		((PSimpleRenderer) simpleRenderers.get(index)).renderID = ID;
	}
	
	public static List<ISimpleBlockRenderingHandler> getSimpleRenderers() {
		return simpleRenderers;
	}
	
	public static List<PTileRenderer> getTileRenderers() {
		return tileRenderers;
	}
}
