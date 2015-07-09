package letiu.pistronics.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;

public abstract class PTileRenderer { 
	
	public abstract void renderTileEntityAt(RenderBlocks renderer, RenderBlocks fakeRenderer, TileEntity tile, double playerX, double playerY, double playerZ, float ticktime);

}
