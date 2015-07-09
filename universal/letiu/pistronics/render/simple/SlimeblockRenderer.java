package letiu.pistronics.render.simple;

import letiu.pistronics.render.PSimpleRenderer;
import letiu.pistronics.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class SlimeblockRenderer extends PSimpleRenderer {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {	
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		tessellator.addTranslation(0F, -0.1F, 0F);
		renderer.setRenderBounds(0.2D, 0.2D, 0.2D, 0.8D, 0.8D, 0.8D);
		RenderUtil.renderInventoryBlockWithAlpha(block, metadata, renderer, 0.9F);
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		RenderUtil.renderInventoryBlockWithAlpha(block, metadata, renderer, 0.8F);
		tessellator.addTranslation(0F, 0.1F, 0F);
		tessellator.draw();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.setRenderBounds(0.2D, 0.2D, 0.2D, 0.8D, 0.8D, 0.8D);
		RenderUtil.renderBlockWithAlpha(world, x, y, z, block, renderer, 0.9F);
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		RenderUtil.renderBlockWithAlpha(world, x, y, z, block, renderer, 0.8F);
		
		return true;
	}

	public boolean shouldRender3DInInventory() {
		return true;
	}

	public boolean shouldRender3DInInventory(int modelId) {
		return shouldRender3DInInventory();
	}

}
