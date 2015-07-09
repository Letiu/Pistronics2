package letiu.pistronics.render.items;

import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.simple.SailRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class SailItemRenderer extends PItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
		
		//PItem pItem = ((IBaseItem) item.getItem()).getData();
		
		RenderBlocks renderer = (RenderBlocks) data[0];
		
		SailRenderer.renderSailWithNBT(item, renderer);
		
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		}
	}
}
