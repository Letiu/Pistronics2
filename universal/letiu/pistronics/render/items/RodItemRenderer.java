package letiu.pistronics.render.items;

import letiu.pistronics.itemblocks.BIRodPart;
import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.simple.RodRenderer;
import letiu.modbase.items.IBaseItem;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import letiu.pistronics.data.PItem;

public class RodItemRenderer extends PItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		
		switch (type) {
		case ENTITY: {
			return (helper == ItemRendererHelper.ENTITY_BOBBING ||
					helper == ItemRendererHelper.ENTITY_ROTATION ||
					helper == ItemRendererHelper.BLOCK_3D);
			}
		case EQUIPPED: {
			return (helper == ItemRendererHelper.BLOCK_3D ||
					helper == ItemRendererHelper.EQUIPPED_BLOCK);
			}
		case EQUIPPED_FIRST_PERSON: {
			return (helper == ItemRendererHelper.EQUIPPED_BLOCK);
			}
		case INVENTORY: {
			return (helper == ItemRendererHelper.INVENTORY_BLOCK);
			}
		default: return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
		
		PItem pItemBlock = ((IBaseItem) item.getItem()).getData();
		
		if (pItemBlock instanceof BIRodPart) {
			GL11.glScalef(0.75F, 0.75F, 0.75F);
		}
		
		RenderBlocks renderer = (RenderBlocks) data[0];
		
		RodRenderer.renderRodWithNBT(item, renderer);
		
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		}
	}
}
