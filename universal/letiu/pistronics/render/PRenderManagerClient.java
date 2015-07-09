package letiu.pistronics.render;

import net.minecraft.client.renderer.entity.RenderManager;
import letiu.modbase.render.UniversalEntityRenderer;
import letiu.pistronics.render.entities.EntityPetrifyArrowRenderer;

public class PRenderManagerClient {


	public static UniversalEntityRenderer entityPetrifyArrowRenderer;
	
	public static void init() {
		// Entity //
		entityPetrifyArrowRenderer = new EntityPetrifyArrowRenderer();
		entityPetrifyArrowRenderer.setRenderManager(RenderManager.instance);
	}
}
