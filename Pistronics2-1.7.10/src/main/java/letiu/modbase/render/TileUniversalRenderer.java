package letiu.modbase.render;

import letiu.modbase.core.ModClass;
import letiu.modbase.tiles.BaseTile;
import letiu.pistronics.data.PTile;
import letiu.pistronics.render.PTileRenderer;
import letiu.pistronics.tiles.ISpecialRenderTile;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileUniversalRenderer extends TileEntitySpecialRenderer {

	private RenderBlocks renderer;
	private RenderBlocks fakeRenderer;
	
	public static boolean hasTLSR(TileEntity tile) {
		return TileEntityRendererDispatcher.instance.hasSpecialRenderer(tile);
	}
	
	public static void renderTile(TileEntity tile, double playerX, double playerY, double playerZ, float ticktime) {
		TileEntitySpecialRenderer tlsr =  TileEntityRendererDispatcher.instance.getSpecialRenderer(tile);
		if (tlsr != null) tlsr.renderTileEntityAt(tile, playerX, playerY, playerZ, ticktime);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double playerX, double playerY, double playerZ, float ticktime) {
		if (tile != null && tile instanceof BaseTile) {
			PTile pTile = ((BaseTile) tile).data;
			if (pTile != null && pTile instanceof ISpecialRenderTile) {
				PTileRenderer tileRenderer = ((ISpecialRenderTile) pTile).getRenderer();
				this.bindTexture(TextureMap.locationBlocksTexture);
				tileRenderer.renderTileEntityAt(renderer, fakeRenderer, tile, playerX, playerY, playerZ, ticktime);
			}
		}
	}

	@Override
	public void func_147496_a(World world) {
		this.renderer = new RenderBlocks(world);
        this.fakeRenderer = new CustomRenderBlocks(world, ModClass.proxy.getFakeWorld(world));
    }
}
