package letiu.pistronics.render.simple;

import letiu.modbase.util.WorldUtil;
import letiu.pistronics.render.PSimpleRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public class MechPistonRenderer extends PSimpleRenderer {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		 
		metadata = 1;
		
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		
		Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		MechPistonRenderer.renderMechPiston(world, x, y, z, block, renderer);
		return true;
	}
	
	public static void renderMechPiston(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer) {

		int facing = WorldUtil.getBlockFacing(world, x, y, z);

        switch (facing)
        {
            case 0:
                renderer.uvRotateWest = 3;
                renderer.uvRotateSouth = 3;
                renderer.uvRotateEast = 3;
                renderer.uvRotateNorth = 3;
                renderer.setRenderBounds(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
                break;
            case 1:
            	renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
                break;
            case 2:
            	renderer.uvRotateSouth = 1;
            	renderer.uvRotateNorth = 2;
            	renderer.setRenderBounds(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
                break;
            case 3:
            	renderer.uvRotateSouth = 2;
            	renderer.uvRotateNorth = 1;
            	renderer.uvRotateTop = 3;
            	renderer.uvRotateBottom = 3;
            	renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
                break;
            case 4:
            	renderer.uvRotateEast = 1;
            	renderer.uvRotateWest = 2;
            	renderer.uvRotateTop = 2;
            	renderer.uvRotateBottom = 1;
            	renderer.setRenderBounds(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                break;
            case 5:
            	renderer.uvRotateEast = 2;
            	renderer.uvRotateWest = 1;
            	renderer.uvRotateTop = 1;
            	renderer.uvRotateBottom = 2;
            	renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
        }

        WorldUtil.setBlockBounds(block, (float)renderer.renderMinX, (float)renderer.renderMinY, (float)renderer.renderMinZ, (float)renderer.renderMaxX, (float)renderer.renderMaxY, (float)renderer.renderMaxZ);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        WorldUtil.setBlockBounds(block, (float)renderer.renderMinX, (float)renderer.renderMinY, (float)renderer.renderMinZ, (float)renderer.renderMaxX, (float)renderer.renderMaxY, (float)renderer.renderMaxZ);
    
	}

	public boolean shouldRender3DInInventory() {
		return true;
	}

	public boolean shouldRender3DInInventory(int modelId) {
		return shouldRender3DInInventory();
	}

}
