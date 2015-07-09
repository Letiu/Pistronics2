package letiu.pistronics.render.simple;

import letiu.pistronics.blocks.BSailPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.render.PSimpleRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public class SailRenderer extends PSimpleRenderer {
	
	public static void renderSailWithNBT(ItemStack stack, RenderBlocks renderer) {
		
		renderer.setRenderBounds(0.0F, 0.0F, BSailPart.SAIL_A, 1.0F, 0.75F, BSailPart.SAIL_B);
		renderer.uvRotateSouth = 2;
    	renderer.uvRotateNorth = 1;
    	renderer.uvRotateTop = 3;
    	renderer.uvRotateBottom = 3;
		
		Tessellator tessellator = Tessellator.instance;
		Block block = BlockData.sailPart.block;
		
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, ItemData.sailPart.item.getIcon(stack, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, ItemData.sailPart.item.getIcon(stack, 1));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, ItemData.sailPart.item.getIcon(stack, 2));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, ItemData.sailPart.item.getIcon(stack, 3));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, ItemData.sailPart.item.getIcon(stack, 4));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, ItemData.sailPart.item.getIcon(stack, 5));
        tessellator.draw();
        
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		
	}
	
	public static boolean renderSail(IBlockAccess world, int x, int y, int z, Block block, int position, int face, RenderBlocks renderer) {
      
		if (face == 0 || face == 1) {
			if (position == 2 || position == 3) renderer.setRenderBounds(0.0F, BSailPart.SAIL_A, 0.0F, 1.0F, BSailPart.SAIL_B, 0.75F);
			else if (position == 4 || position == 5) renderer.setRenderBounds(0.0F, BSailPart.SAIL_A, 0.0F, 0.75F, BSailPart.SAIL_B, 1.0F);
		}
		else if (face == 2 || face == 3) {
			if (position == 0 || position == 1) renderer.setRenderBounds(0.0F, 0.0F, BSailPart.SAIL_A, 1.0F, 0.75F, BSailPart.SAIL_B);
			else if (position == 4 || position == 5) renderer.setRenderBounds(0.0F, 0.0F, BSailPart.SAIL_A, 0.75F, 1.0F, BSailPart.SAIL_B);
		}
		else if (face == 4 || face == 5) {
			if (position == 0 || position == 1) renderer.setRenderBounds(BSailPart.SAIL_A, 0.0F, 0.0F, BSailPart.SAIL_B, 0.75F, 1.0F);
			else if (position == 2 || position == 3) renderer.setRenderBounds(BSailPart.SAIL_A, 0.0F, 0.0F, BSailPart.SAIL_B, 1.0F, 0.75F);
		}
		
		switch (position) {
		case 0: Tessellator.instance.addTranslation(0F, -0.375F, 0F); break; 
		case 1: Tessellator.instance.addTranslation(0F, 0.625F, 0F); break;
		case 2: Tessellator.instance.addTranslation(0F, 0F, -0.375F); break; 
		case 3: Tessellator.instance.addTranslation(0F, 0F, 0.625F); break;
		case 4: Tessellator.instance.addTranslation(-0.375F, 0F, 0F); break; 
		case 5: Tessellator.instance.addTranslation(0.625F, 0F, 0F); break;
		}
		
		renderer.renderStandardBlock(block, x, y, z);
		
		switch (position) {
		case 0: Tessellator.instance.addTranslation(0F, 0.375F, 0F); break; 
		case 1: Tessellator.instance.addTranslation(0F, -0.625F, 0F); break;
		case 2: Tessellator.instance.addTranslation(0F, 0F, 0.375F); break; 
		case 3: Tessellator.instance.addTranslation(0F, 0F, -0.625F); break;
		case 4: Tessellator.instance.addTranslation(0.375F, 0F, 0F); break; 
		case 5: Tessellator.instance.addTranslation(-0.625F, 0F, 0F); break;
		}
        
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        
		return true;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return true;
	}

	public boolean shouldRender3DInInventory() {
		return true;
	}

	public boolean shouldRender3DInInventory(int modelId) {
		return shouldRender3DInInventory();
	}

}