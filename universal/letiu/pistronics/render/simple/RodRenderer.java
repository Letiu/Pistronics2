package letiu.pistronics.render.simple;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BRodPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.render.PSimpleRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public class RodRenderer extends PSimpleRenderer {
	
	public static void renderRodWithNBT(ItemStack stack, RenderBlocks renderer) {
		
		renderer.setRenderBounds(0.375F, 0.375F, 0.01F, 0.625F, 0.625F, 0.99F);
		renderer.uvRotateSouth = 2;
    	renderer.uvRotateNorth = 1;
    	renderer.uvRotateTop = 3;
    	renderer.uvRotateBottom = 3;
		
		Tessellator tessellator = Tessellator.instance;
		Block block = BlockData.rod.block;
		
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, ItemData.rod.item.getIcon(stack, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, ItemData.rod.item.getIcon(stack, 1));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, ItemData.rod.item.getIcon(stack, 2));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, ItemData.rod.item.getIcon(stack, 3));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, ItemData.rod.item.getIcon(stack, 4));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, ItemData.rod.item.getIcon(stack, 5));
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
	
	public static void renderRodInInventory(Block block, int metadata, RenderBlocks renderer) {
		
		renderer.setRenderBounds(0.375F, 0.375F, 0.01F, 0.625F, 0.625F, 0.99F);
		renderer.uvRotateSouth = 2;
    	renderer.uvRotateNorth = 1;
    	renderer.uvRotateTop = 3;
    	renderer.uvRotateBottom = 3;
    	
    	if (block instanceof BaseBlock) {
			if (((BaseBlock) block).data instanceof BRodPart) {
				renderer.setRenderBounds(0.375F, 0.375F, 0.01F, 0.625F, 0.625F, 0.50F);
				 GL11.glTranslatef(0F, 0F, 0.25F);
			}
		}
		
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

        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}
	
	public static void renderRod3QInInventory(Block block, int metadata, RenderBlocks renderer) {
		
		renderer.setRenderBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 0.75F);
		renderer.uvRotateSouth = 2;
    	renderer.uvRotateNorth = 1;
    	renderer.uvRotateTop = 3;
    	renderer.uvRotateBottom = 3;
		
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

        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}
	
	public static void renderRod1TInInventory(Block block, int metadata, RenderBlocks renderer) {
		
		renderer.setRenderBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 0.375F);
		renderer.uvRotateSouth = 2;
    	renderer.uvRotateNorth = 1;
    	renderer.uvRotateTop = 3;
    	renderer.uvRotateBottom = 3;
		
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

        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if (((BaseBlock) block).data instanceof BRodPart) {
			renderRod1TInInventory(block, 4, renderer);;
		}
		renderRodInInventory(block, 3, renderer);
	}
	
	public static boolean renderRod(IBlockAccess world, int x, int y, int z, Block block, int facing, RenderBlocks renderer) {
        
        switch (facing) {
            case 0:
                renderer.uvRotateEast = 3;
                renderer.uvRotateWest = 3;
                renderer.uvRotateSouth = 3;
                renderer.uvRotateNorth = 3;
                renderer.setRenderBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
                break;
            case 1:
            	renderer.setRenderBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
                break;
            case 2:
            	renderer.uvRotateSouth = 1;
            	renderer.uvRotateNorth = 2;
            	renderer.setRenderBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
                break;
            case 3:
            	renderer.uvRotateSouth = 2;
            	renderer.uvRotateNorth = 1;
            	renderer.uvRotateTop = 3;
            	renderer.uvRotateBottom = 3;
            	renderer.setRenderBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
                break;
            case 4:
            	renderer.uvRotateEast = 1;
            	renderer.uvRotateWest = 2;
            	renderer.uvRotateTop = 2;
            	renderer.uvRotateBottom = 1;
            	renderer.setRenderBounds(0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
                break;
            case 5:
            	renderer.uvRotateEast = 2;
            	renderer.uvRotateWest = 1;
            	renderer.uvRotateTop = 1;
            	renderer.uvRotateBottom = 2;
            	renderer.setRenderBounds(0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
        }
      
        renderer.renderStandardBlock(block, x, y, z);
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return true;
	}
	
	public static boolean renderRod3Q(IBlockAccess world, int x, int y, int z, Block block, int facing, RenderBlocks renderer) {
        
        switch (facing) {
            case 0:
                renderer.uvRotateEast = 3;
                renderer.uvRotateWest = 3;
                renderer.uvRotateSouth = 3;
                renderer.uvRotateNorth = 3;
                renderer.setRenderBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
                break;
            case 1:
            	renderer.setRenderBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
                break;
            case 2:
            	renderer.uvRotateSouth = 1;
            	renderer.uvRotateNorth = 2;
            	renderer.setRenderBounds(0.375F, 0.375F, 0.25F, 0.625F, 0.625F, 1.0F);
                break;
            case 3:
            	renderer.uvRotateSouth = 2;
            	renderer.uvRotateNorth = 1;
            	renderer.uvRotateTop = 3;
            	renderer.uvRotateBottom = 3;
            	renderer.setRenderBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 0.75F);
                break;
            case 4:
            	renderer.uvRotateEast = 1;
            	renderer.uvRotateWest = 2;
            	renderer.uvRotateTop = 2;
            	renderer.uvRotateBottom = 1;
            	renderer.setRenderBounds(0.25F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
                break;
            case 5:
            	renderer.uvRotateEast = 2;
            	renderer.uvRotateWest = 1;
            	renderer.uvRotateTop = 1;
            	renderer.uvRotateBottom = 2;
            	renderer.setRenderBounds(0.0F, 0.375F, 0.375F, 0.75F, 0.625F, 0.625F);
        }
      
        renderer.renderStandardBlock(block, x, y, z);
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return true;
	}
	
	public static boolean renderRod1T(IBlockAccess world, int x, int y, int z, Block block, int facing, RenderBlocks renderer) {
        
        switch (facing) {
            case 0:
                renderer.uvRotateEast = 3;
                renderer.uvRotateWest = 3;
                renderer.uvRotateSouth = 3;
                renderer.uvRotateNorth = 3;
                renderer.setRenderBounds(0.375F, 0.625F, 0.375F, 0.625F, 1.0F, 0.625F);
                break;
            case 1:
            	renderer.setRenderBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.375F, 0.625F);
                break;
            case 2:
            	renderer.uvRotateSouth = 1;
            	renderer.uvRotateNorth = 2;
            	renderer.setRenderBounds(0.375F, 0.375F, 0.625F, 0.625F, 0.625F, 1.0F);
                break;
            case 3:
            	renderer.uvRotateSouth = 2;
            	renderer.uvRotateNorth = 1;
            	renderer.uvRotateTop = 3;
            	renderer.uvRotateBottom = 3;
            	renderer.setRenderBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 0.375F);
                break;
            case 4:
            	renderer.uvRotateEast = 1;
            	renderer.uvRotateWest = 2;
            	renderer.uvRotateTop = 2;
            	renderer.uvRotateBottom = 1;
            	renderer.setRenderBounds(0.625F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
                break;
            case 5:
            	renderer.uvRotateEast = 2;
            	renderer.uvRotateWest = 1;
            	renderer.uvRotateTop = 1;
            	renderer.uvRotateBottom = 2;
            	renderer.setRenderBounds(0.0F, 0.375F, 0.375F, 0.375F, 0.625F, 0.625F);
        }
      
        renderer.renderStandardBlock(block, x, y, z);
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return true;
	}
	
	public static boolean renderRod1F(IBlockAccess world, int x, int y, int z, Block block, int facing, RenderBlocks renderer) {
        
        switch (facing) {
            case 0:
                renderer.uvRotateEast = 3;
                renderer.uvRotateWest = 3;
                renderer.uvRotateSouth = 3;
                renderer.uvRotateNorth = 3;
                renderer.setRenderBounds(0.375F, 0.625F, 0.375F, 0.625F, 0.75F, 0.625F);
                break;
            case 1:
            	renderer.setRenderBounds(0.375F, 0.25F, 0.375F, 0.625F, 0.375F, 0.625F);
                break;
            case 2:
            	renderer.uvRotateSouth = 1;
            	renderer.uvRotateNorth = 2;
            	renderer.setRenderBounds(0.375F, 0.375F, 0.625F, 0.625F, 0.625F, 0.75F);
                break;
            case 3:
            	renderer.uvRotateSouth = 2;
            	renderer.uvRotateNorth = 1;
            	renderer.uvRotateTop = 3;
            	renderer.uvRotateBottom = 3;
            	renderer.setRenderBounds(0.375F, 0.375F, 0.25F, 0.625F, 0.625F, 0.375F);
                break;
            case 4:
            	renderer.uvRotateEast = 1;
            	renderer.uvRotateWest = 2;
            	renderer.uvRotateTop = 2;
            	renderer.uvRotateBottom = 1;
            	renderer.setRenderBounds(0.625F, 0.375F, 0.375F, 0.75F, 0.625F, 0.625F);
                break;
            case 5:
            	renderer.uvRotateEast = 2;
            	renderer.uvRotateWest = 1;
            	renderer.uvRotateTop = 1;
            	renderer.uvRotateBottom = 2;
            	renderer.setRenderBounds(0.25F, 0.375F, 0.375F, 0.375F, 0.625F, 0.625F);
        }
      
        renderer.renderStandardBlock(block, x, y, z);
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
		if (((BaseBlock) block).data instanceof BRodPart) {
			return this.renderRod1T(world, x, y, z, block, WorldUtil.getBlockFacing(world, x, y, z) ^ 1, renderer);
		}
		return this.renderRod(world, x, y, z, block, WorldUtil.getBlockFacing(world, x, y, z), renderer);
	}

	public boolean shouldRender3DInInventory() {
		return true;
	}

	public boolean shouldRender3DInInventory(int modelId) {
		return shouldRender3DInInventory();
	}

}