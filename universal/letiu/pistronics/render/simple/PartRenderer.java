package letiu.pistronics.render.simple;

import letiu.modbase.core.ModBaseInfo;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BExtensionPart;
import letiu.pistronics.blocks.BRodPart;
import letiu.pistronics.blocks.BSailPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.render.PSimpleRenderer;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileSail;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class PartRenderer extends PSimpleRenderer {

	public static int metaForRender = -1;
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		RodRenderer.renderRodInInventory(block, 3, renderer);
	}
	
	public static boolean renderKnot(IBlockAccess world, int x, int y, int z, Block block, int facing, RenderBlocks renderer) {

		renderer.setRenderBounds(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F);
		renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    
		return true;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		this.renderKnot(world, x, y, z, block, WorldUtil.getBlockFacing(world, x, y, z), renderer);
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			TilePartblock tileP = (TilePartblock) tile;
			
			for (int i = 0; i < 6; i++) {
				
				metaForRender = i;
				PBlock part = tileP.getPart(i);
				if (part != null) {
					if (part instanceof BRodPart) {
						RodRenderer.renderRod1T(world, x, y, z, BlockData.rodPart.block, i ^ 1, renderer);
					}
					else if (part instanceof BExtensionPart) {
						RodRenderer.renderRod1F(world, x, y, z, BlockData.rodPart.block, i ^ 1, renderer);
						if (ModBaseInfo.MC_VERSION == "1.6.4") {
							ExtensionRenderer.renderExtension16(world, x, y, z, BlockData.extensionPart.block, i, renderer);
						}
						else {
							ExtensionRenderer.renderExtension(world, x, y, z, BlockData.extensionPart.block, i, renderer);
						}
					}
					else if (part instanceof BSailPart) {
						PTile sailTile = tileP.getTile(i);
						if (sailTile != null && sailTile instanceof TileSail) {
							SailRenderer.renderSail(world, x, y, z, block, i, ((TileSail) sailTile).face, renderer);
						}
					}
				}
			}
		}
		else {
			System.out.println("tile missing");
		}
		
		metaForRender = -1;
		return true;
	}

	public boolean shouldRender3DInInventory() {
		return true;
	}

	public boolean shouldRender3DInInventory(int modelId) {
		return shouldRender3DInInventory();
	}

}