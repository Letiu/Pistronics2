package letiu.pistronics.render.tiles;

import letiu.pistronics.reference.Textures;
import org.lwjgl.opengl.GL11;

import letiu.modbase.render.MyTextureManager;
import letiu.modbase.tiles.BaseTile;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.render.PTileRenderer;
import letiu.pistronics.tiles.TileStatue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class StatueRenderer extends PTileRenderer {
	
	public static final MyTextureManager myTexManager = new MyTextureManager(Minecraft.getMinecraft().getResourceManager());
	
	@Override
	public void renderTileEntityAt(RenderBlocks renderer, RenderBlocks fakeRenderer, TileEntity tile,
			double playerX, double playerY, double playerZ, float ticktime) {
		
		GL11.glPushMatrix();

		if (!ConfigData.renderStatuesAsBoxes) {
			renderStatueAt(renderer, fakeRenderer, (TileStatue) (((BaseTile) tile).data), playerX + 0.5D, playerY, playerZ + 0.5D, ticktime);
		}
		
		GL11.glPopMatrix();
	}
		
	public void renderStatueAt(RenderBlocks renderer, RenderBlocks fakeRenderer, TileStatue tile, double playerX, double playerY, double playerZ, float ticktime) {
		if (tile == null) return;

		ResourceLocation texture = tile.getStatueTexture();
		TextureManager orignalTexManager = RenderManager.instance.renderEngine;
		
		if (texture != null) {
			myTexManager.texture = texture;
            myTexManager.factor = tile.getStatueResolution();
            myTexManager.overlay = (tile.camou && tile.camouID == -1) ? Textures.camouOverlay : null;
			RenderManager.instance.renderEngine = myTexManager;
		}	
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(playerX, playerY, playerZ);
		GL11.glRotatef(tile.getAngle(), 0F, 1F, 0F);
 		
 		float scale = ((float) tile.getScale()) / 100F;
 		GL11.glScalef(scale, scale, scale);
 		GL11.glTranslated(-playerX, -playerY, -playerZ);

        if (tile.getEntity() == null) {
            tile.tryLoadEntity();
        }

		EntityLivingBase entity = (EntityLivingBase) tile.getEntity();
		
		if (entity != null) {

			if (entity.worldObj == null) {
				entity.worldObj = tile.tileEntity.getWorldObj();
			}
			
			Render render = (Render) RenderManager.instance.entityRenderMap.get(entity.getClass());	
			render.doRender(entity, playerX, playerY, playerZ, 0F, 0F);
		}
		
		if (texture != null) {
			RenderManager.instance.renderEngine = orignalTexManager;
		}
		
		GL11.glPopMatrix();
	}

}
