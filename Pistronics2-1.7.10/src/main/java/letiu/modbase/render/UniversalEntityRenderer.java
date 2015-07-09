package letiu.modbase.render;

import letiu.modbase.entities.BaseEntity;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class UniversalEntityRenderer extends Render {

	@Override
	public final void doRender(Entity entity, double x, double y, double z, float var8, float var9) {
		if (entity instanceof BaseEntity) {
			((BaseEntity) entity).getRenderer().renderEntity(entity, x, y, z, var8, var9);
		}
	}
	
	public void renderEntity(Entity entity, double x, double y, double z, float var8, float var9) {
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}


}
