package letiu.pistronics.render.simple;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.blocks.SidedBlock;
import letiu.modbase.core.ModClass;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BExtensionPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PTile;
import letiu.pistronics.render.PSimpleRenderer;
import letiu.pistronics.tiles.TilePartblock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public class ExtensionRenderer extends PSimpleRenderer {

	public static void renderExtensionWithNBT(ItemStack stack, RenderBlocks renderer) {
	
		renderer.setRenderBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
		renderer.uvRotateSouth = 2;
    	renderer.uvRotateNorth = 1;
    	renderer.uvRotateTop = 3;
    	renderer.uvRotateBottom = 3;
		
		Tessellator tessellator = Tessellator.instance;
		Block block = BlockData.extension.block;
		
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, stack.getItem().getIcon(stack, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, stack.getItem().getIcon(stack, 1));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, stack.getItem().getIcon(stack, 2));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, stack.getItem().getIcon(stack, 3));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, stack.getItem().getIcon(stack, 4));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, stack.getItem().getIcon(stack, 5));
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
	
	public static void renderExtensionInventory(Block block, int metadata, RenderBlocks renderer) {
		
		renderer.setRenderBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
		renderer.uvRotateSouth = 2;
    	renderer.uvRotateNorth = 1;
    	renderer.uvRotateTop = 3;
    	renderer.uvRotateBottom = 3;
		
		Tessellator tessellator = Tessellator.instance;
		
		if (block instanceof BaseBlock) {
			if (((BaseBlock) block).data instanceof BExtensionPart) {
				GL11.glScalef(0.75F, 0.75F, 0.75F);
			}
		}
		
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
        renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {	
		renderExtensionInventory(block, 3, renderer);
		RodRenderer.renderRod3QInInventory(BlockData.rod.block, 3, renderer);
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		renderExtension(world, x, y, z, block, facing, renderer);
		
		if (((BaseBlock) block).data instanceof BExtensionPart) {
			RodRenderer.renderRod1F(world, x, y, z, BlockData.rod.block, facing ^ 1, renderer);
		}
		else {
			RodRenderer.renderRod3Q(world, x, y, z, BlockData.rod.block, facing, renderer);
		}
		return true;
	}

	public static boolean renderExtension(IBlockAccess world, int x, int y, int z, Block block, int facing, RenderBlocks renderer) {        

		boolean east = false, south = false;
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			TilePartblock tileP = (TilePartblock) tile;
			south = tileP.hasPart(2) || tileP.hasPart(3);
			east = tileP.hasPart(4) || tileP.hasPart(5);
		}
		
        switch (facing) {
            case 0:
                renderer.uvRotateEast = east ? 0 : 3;
                renderer.uvRotateWest = 3;
                renderer.uvRotateSouth = south ? 0 : 3;
                renderer.uvRotateNorth = 3;
                renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
               break;
            case 1:
            	renderer.setRenderBounds(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
                break;
            case 2:
            	renderer.uvRotateSouth = 1;
            	renderer.uvRotateNorth = 2;
            	renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
                break;
            case 3:
            	renderer.uvRotateSouth = 2;
            	renderer.uvRotateNorth = 1;
            	renderer.uvRotateTop = 3;
            	renderer.uvRotateBottom = 3;
            	renderer.setRenderBounds(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
                break;
            case 4:
            	renderer.uvRotateEast = 1;
            	renderer.uvRotateWest = 2;
            	renderer.uvRotateTop = 2;
            	renderer.uvRotateBottom = 1;
            	renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
                break;
            case 5:
            	renderer.uvRotateEast = 2;
            	renderer.uvRotateWest = 1;
            	renderer.uvRotateTop = 1;
            	renderer.uvRotateBottom = 2;
            	renderer.setRenderBounds(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            	
        }

        renderer.renderStandardBlock(block, x, y, z);
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.flipTexture = false;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        return true;
	}
	
	public static boolean renderExtension16(IBlockAccess world, int x, int y, int z, Block block, int facing, RenderBlocks renderer) {        
		
		boolean east = false, south = false;
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			TilePartblock tileP = (TilePartblock) tile;
			south = tileP.hasPart(2) || tileP.hasPart(3);
			east = tileP.hasPart(4) || tileP.hasPart(5);
		}
		
        switch (facing) {
            case 0:
                renderer.uvRotateEast = east ? 0 : 3;
                renderer.uvRotateWest = 3;
                renderer.uvRotateSouth = south ? 0 : 3;
                renderer.uvRotateNorth = 3;
                renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
               break;
            case 1:
//            	renderer.uvRotateEast = 1;
//            	renderer.uvRotateSouth = 2;
//            	renderer.uvRotateNorth = 1;
//            	renderer.uvRotateTop = 2;
//            	renderer.uvRotateBottom = 2;
            	
            	renderer.setRenderBounds(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
                break;
            case 2:
            	renderer.uvRotateSouth = 1;
            	renderer.uvRotateNorth = 2;
            	renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
                break;
            case 3:
            	renderer.uvRotateSouth = 2;
            	renderer.uvRotateNorth = 1;
            	renderer.uvRotateTop = 3;
            	renderer.uvRotateBottom = 3;
            	renderer.setRenderBounds(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
                break;
            case 4:
            	renderer.uvRotateEast = 1;
            	renderer.uvRotateWest = 2;
            	renderer.uvRotateTop = 2;
            	renderer.uvRotateBottom = 1;
            	renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
                break;
            case 5:
            	renderer.uvRotateEast = 2;
            	renderer.uvRotateWest = 1;
            	renderer.uvRotateTop = 1;
            	renderer.uvRotateBottom = 2;
            	renderer.setRenderBounds(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            	
        }

        SidedBlock sidedBlock = ModClass.proxy.getSidedBlock();
        sidedBlock.setBlockData(BlockData.extensionPart);
     
        for (int i = 0; i < 6; i++) {
        	sidedBlock.side = i;
        	if ((i == 2 || i == 5) && (facing == 1 || facing == 0)) renderer.flipTexture = true;
        	else renderer.flipTexture = false;
        	renderer.renderStandardBlock(sidedBlock, x, y, z);
        }
        
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
        renderer.flipTexture = false;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        return true;
	}
	
	public boolean shouldRender3DInInventory() {
		return true;
	}

	public boolean shouldRender3DInInventory(int modelId) {
		return shouldRender3DInInventory();
	}

}
