package letiu.pistronics.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderUtil {

	public static boolean renderBlockWithAlpha(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer, float alpha) {

		Tessellator tessellator = Tessellator.instance;
		int l = block.getMixedBrightnessForBlock(world, x, y, z);
		
		tessellator.setColorRGBA_F(1F, 1F, 1F, alpha);
        renderer.enableAO = false;
      
        tessellator.setBrightness(renderer.renderMinY > 0.0D ? l : block.getMixedBrightnessForBlock(world, x, y - 1, z));
        renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, world, x, y, z, 0));
        
        tessellator.setBrightness(renderer.renderMaxY < 1.0D ? l : block.getMixedBrightnessForBlock(world, x, y + 1, z));
        renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, world, x, y, z, 1));
        
        tessellator.setBrightness(renderer.renderMinZ > 0.0D ? l : block.getMixedBrightnessForBlock(world, x, y, z - 1));
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, world, x, y, z, 2));

        tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? l : block.getMixedBrightnessForBlock(world, x, y, z + 1));
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, world, x, y, z, 3));

        tessellator.setBrightness(renderer.renderMinX > 0.0D ? l : block.getMixedBrightnessForBlock(world, x - 1, y, z));
        renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, world, x, y, z, 4));

        tessellator.setBrightness(renderer.renderMaxX < 1.0D ? l : block.getMixedBrightnessForBlock(world, x + 1, y, z));
        renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, world, x, y, z, 5));

        return true;
	}
	
	public static boolean renderInventoryBlockWithAlpha(Block block, int metadata, RenderBlocks renderer, float alpha) {

		Tessellator tessellator = Tessellator.instance;
		
		tessellator.setColorRGBA_F(1F, 1F, 1F, alpha);
        renderer.enableAO = false;
     
        renderer.renderFaceYNeg(block, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
        renderer.renderFaceYPos(block, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
        renderer.renderFaceZNeg(block, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
        renderer.renderFaceZPos(block, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
        renderer.renderFaceXNeg(block, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
        renderer.renderFaceXPos(block, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));

        return true;
	}
}
