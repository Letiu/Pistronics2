package letiu.pistronics.render.tiles;

import letiu.modbase.core.ModClass;
import letiu.modbase.render.TileUniversalRenderer;
import letiu.modbase.tiles.BaseTile;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.render.PTileRenderer;
import letiu.pistronics.render.RenderTweaker;
import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ElementRenderer extends PTileRenderer {

	@Override
	public void renderTileEntityAt(RenderBlocks renderer, RenderBlocks fakeRenderer, TileEntity tile, double playerX, double playerY, double playerZ, float ticktime) {	
		renderElementAt(renderer, fakeRenderer, (TileElementHolder) (((BaseTile) tile).data), playerX, playerY, playerZ, ticktime);
	}
	
	public void renderElementAt(RenderBlocks renderer, RenderBlocks fakeRenderer, TileElementHolder tile, double playerX, double playerY, double playerZ, float ticktime) {
		if (!tile.hasElement() || !show(tile)) return;
    	
    	int x = tile.tileEntity.xCoord;
    	int y = tile.tileEntity.yCoord;
    	int z = tile.tileEntity.zCoord;
    	
        Block block = tile.getElement();

        Tessellator tessellator = Tessellator.instance;
        //RenderHelper.disableStandardItemLighting();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GL11.glShadeModel(GL11.GL_SMOOTH);
        }
        else
        {
            GL11.glShadeModel(GL11.GL_FLAT);
        }

        tessellator.startDrawingQuads();
  
        
        // ROTATE //
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        tessellator.setNormal(1F, 1F, 1F);
        
        GL11.glTranslated(playerX, playerY, playerZ);
        
        // MOVE //
     	GL11.glTranslatef(getXOffset(tile, ticktime), getYOffset(tile, ticktime), getZOffset(tile, ticktime));	
     		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
		Vector3 rp = getRotatePoint(tile);
		GL11.glTranslatef(rp.x - x, rp.y - y, rp.z - z);
		
		int dir = getRotateDir(tile);
		float currentAngle = getAngle(tile, ticktime);
		
		switch(dir) {
		case 0: GL11.glRotatef(currentAngle, 0F, 1F, 0F); break;
		case 1: GL11.glRotatef(currentAngle, 0F, -1F, 0F); break;
		case 2: GL11.glRotatef(currentAngle, 0F, 0F, 1F); break;
		case 3: GL11.glRotatef(currentAngle, 0F, 0F, -1F); break;
		case 4: GL11.glRotatef(currentAngle, 1F, 0F, 0F); break;
		case 5: GL11.glRotatef(currentAngle, -1F, 0F, 0F); break;
		}
		
		GL11.glTranslatef(-rp.x + x, -rp.y + y, -rp.z + z);
		
		if (useRotateFix(tile)) {
			switch(dir) {
			case 0: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 1: GL11.glRotatef(90, 0F, -1F, 0F); break;
			case 2: GL11.glRotatef(90, 0F, 0F, 1F); break;
			case 3: GL11.glRotatef(90, 0F, 0F, -1F); break;
			case 4: GL11.glRotatef(90, 1F, 0F, 0F); break;
			case 5: GL11.glRotatef(90, -1F, 0F, 0F); break;
			}
		}
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	
		GL11.glTranslatef((float)-x, (float)-y, (float)-z);
        // ROTATE END //
		
		
        tessellator.setColorOpaque(1, 1, 1);

        int facing = WorldUtil.getBlockFacing(tile);
        
        if (currentAngle != 0F) RenderTweaker.renderAllSides = true;
        else RenderTweaker.sideRender[facing ^ 1] = false;
        
        // RENDER //

        if (useFakeWorld()) {
            fakeRenderer.renderAllFaces = true;
            fakeRenderer.renderBlockByRenderType(block, x, y, z);
            fakeRenderer.renderAllFaces = false;
        }
        else {
            renderer.renderAllFaces = true;
            renderer.renderBlockByRenderType(block, x, y, z);
            renderer.renderAllFaces = false;
        }

        tessellator.setTranslation(0.0D, 0.0D, 0.0D);
        tessellator.draw();

        
        // RENDER TLSR //
        TileEntity elementTile = tile.getElementTile();
   
        GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glTranslated(-playerX, -playerY, -playerZ);
		
    	if (elementTile != null && TileUniversalRenderer.hasTLSR(elementTile)) {
    		TileUniversalRenderer.renderTile(elementTile, playerX, playerY, playerZ, ticktime);
    	}
    	
    	RenderTweaker.reset();
    	
    	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
 		GL11.glPopMatrix();
 		
        RenderHelper.enableStandardItemLighting();
    }
	
	protected Vector3 getRotatePoint(TileElementHolder tile) {
		return new Vector3(tile.tileEntity.xCoord, tile.tileEntity.yCoord, tile.tileEntity.zCoord);
	}
	
	protected float getAngle(TileElementHolder tile, float ticktime) {
		return 0.0F;
	}
	
	protected boolean useRotateFix(TileElementHolder tile) {
		return false;
	}
	
	protected int getRotateDir(TileElementHolder tile) {
		return WorldUtil.getBlockFacing(tile.tileEntity.getWorldObj(), tile.tileEntity.xCoord, tile.tileEntity.yCoord, tile.tileEntity.zCoord);
	}
	
	protected float getXOffset(TileElementHolder tile, float ticktime) {
		return 0.0F;
	}
	
	protected float getYOffset(TileElementHolder tile, float ticktime) {
		return 0.0F;
	}
	
	protected float getZOffset(TileElementHolder tile, float ticktime) {
		return 0.0F;
	}
	
	protected boolean useFakeWorld() {
		return true;
	}
	
	protected boolean show(TileElementHolder tile) {
		return true;
	}
}
