package letiu.pistronics.render.items;

import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.tiles.StatueRenderer;
import letiu.pistronics.tiles.TileStatue;
import letiu.modbase.tiles.BaseTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class StatueItemRenderer extends PItemRenderer {
	
	private static TileStatue tile = new TileStatue();
	
	{
		tile.tileEntity = new BaseTile();
		tile.tileEntity.setWorldObj(Minecraft.getMinecraft().theWorld);
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef(0F, -0.5F, 0F);
		
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
		
		if (stack != null && stack.stackTagCompound != null) {
			
			RenderBlocks renderer = (RenderBlocks) data[0];
			
			if (tile.tileEntity == null) {
				tile.tileEntity = new BaseTile();
			}
			
			if (tile.tileEntity.getWorldObj() == null) {
				tile.tileEntity.setWorldObj(Minecraft.getMinecraft().theWorld);
			}
			
			tile.readFromNBT(stack.stackTagCompound);
			
			TextureManager orignalTexManager = RenderManager.instance.renderEngine;
			StatueRenderer.myTexManager.texture = tile.getStatueTexture();
            StatueRenderer.myTexManager.factor = tile.getStatueResolution();
            StatueRenderer.myTexManager.overlay = (tile.camou && tile.camouID == -1) ? Textures.camouOverlay : null;
			RenderManager.instance.renderEngine = StatueRenderer.myTexManager;
			
			EntityLivingBase entity = (EntityLivingBase) tile.getEntity();
			
			if (entity != null) {
				
				if (entity.worldObj == null) {
					entity.worldObj = tile.tileEntity.getWorldObj();
					if (entity.worldObj == null) {
						entity.worldObj = Minecraft.getMinecraft().theWorld;
					}
				}
				
				Render render = (Render) RenderManager.instance.entityRenderMap.get(entity.getClass());	
				render.doRender(entity, 0, 0, 0, 0F, 0F);
				
			    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		        GL11.glDisable(GL11.GL_TEXTURE_2D);
		        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		        GL11.glEnable(GL11.GL_TEXTURE_2D);
		        
		        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			}
			
			RenderManager.instance.renderEngine = orignalTexManager;
		}
		
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		}
		
		GL11.glTranslatef(0F, 0.5F, 0F);
		
		GL11.glPopMatrix();
	}
}
