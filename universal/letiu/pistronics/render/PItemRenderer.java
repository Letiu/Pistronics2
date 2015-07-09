package letiu.pistronics.render;

import letiu.modbase.render.UniversalItemRenderer;
import net.minecraft.item.ItemStack;

public abstract class PItemRenderer extends UniversalItemRenderer {

	public abstract boolean handleRenderType(ItemStack item, ItemRenderType type);
	
	public abstract boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper);

	public abstract void renderItem(ItemRenderType type, ItemStack item, Object... data);
	
}
