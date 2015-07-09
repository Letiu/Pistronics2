package letiu.pistronics.render.tiles;

import letiu.pistronics.data.BlockData;
import letiu.pistronics.gears.Gear;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PTileRenderer;
import letiu.pistronics.render.models.ModelBigGear;
import letiu.pistronics.render.models.ModelGear;
import letiu.pistronics.render.models.ModelInterGear;
import letiu.pistronics.render.models.ModelSmallGear;
import letiu.pistronics.render.simple.RodRenderer;
import letiu.pistronics.tiles.TileGear;
import letiu.pistronics.util.BlockProxy;
import letiu.modbase.tiles.BaseTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

public class GearRenderer extends PTileRenderer {

	private ModelGear modelGear;
	private ModelSmallGear modelSmallGear;
	private ModelBigGear modelBigGear;
	private ModelInterGear modelInterGear;
	
	public GearRenderer() {
		modelGear = new ModelGear();
		modelSmallGear = new ModelSmallGear();
		modelBigGear = new ModelBigGear();
		modelInterGear = new ModelInterGear();
	}

	@Override
	public void renderTileEntityAt(RenderBlocks renderer, RenderBlocks fakeRenderer, TileEntity tile,
			double playerX, double playerY, double playerZ, float ticktime) {
        
		GL11.glPushMatrix();

        GL11.glTranslatef((float) playerX, (float) playerY, (float) playerZ);
        renderGearAt(renderer, fakeRenderer, (TileGear) (((BaseTile) tile).data), playerX, playerY, playerZ, ticktime);
      
        GL11.glPopMatrix();
    }
	
	public void renderGearAt(RenderBlocks renderer, RenderBlocks fakeRenderer, TileGear tile, double playerX, double playerY, double playerZ, float ticktime) {
		
		Tessellator tessellator = Tessellator.instance;
        //This will make your block brightness dependent from surroundings lighting.
//        float f = block.getBlockBrightness(world, i, j, k);
//        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
//        int l1 = l % 65536;
//        int l2 = l / 65536;
//        tessellator.setColorOpaque_F(f, f, f);
//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)l1, (float)l2); 
              
	    GL11.glPushMatrix();

	    int dir = new BlockProxy(tile).getMetadata() & 7;
	    
	  //if (tl.isRunning) {
    	//GL11.glRotatef((tl.clockwise ? -1 : 1) * tl.rotation, 0F, 0F, 1F);
    //}
	    if (tile.hasRod) {
		    tessellator.startDrawingQuads();
	        tessellator.setColorOpaque(1, 1, 1);
	        tessellator.setNormal(1F, 1F, 1F);
	        tessellator.setTranslation(-tile.tileEntity.xCoord, -tile.tileEntity.yCoord, -tile.tileEntity.zCoord);
	        
			RodRenderer.renderRod(tile.tileEntity.getWorldObj(), tile.tileEntity.xCoord, tile.tileEntity.yCoord, tile.tileEntity.zCoord,
		    		BlockData.rod.block, dir, renderer);
			
			tessellator.setTranslation(0, 0, 0);
			tessellator.draw();
	    }
	    
	    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	    
	    switch(dir) {
	    case 0: GL11.glRotatef(90F, 1F, 0F, 0F); break;
	    case 1: GL11.glRotatef(-90F, 1F, 0F, 0F); break;
	    case 2: GL11.glRotatef(180F, 0F, 1F, 0F); break;
	    case 3: GL11.glRotatef(0F, 0F, 1F, 0F); break;
	    case 4: GL11.glRotatef(270F, 0F, 1F, 0F); break;
	    case 5: GL11.glRotatef(90F, 0F, 1F, 0F); break;
	    }
	    
	    GL11.glTranslatef(0F, 0F, -0.75F);
	    
	    for (int i = 0; i < 3; i++) {
	    
	    	GL11.glTranslatef(0F, 0F, 0.25F);
	    	
	    	Gear gear = tile.getGear(i);
	    	if (gear != null) {
	    
	    		Minecraft.getMinecraft().getTextureManager().bindTexture(Textures.getPlankTextureFromMeta(gear.meta));
	    		
			    int tooths = (gear.size == 3.0F) ? 24 : (gear.size == 1F ? 8 : (gear.size == 1.5 ? 20 : 40));
			    
			    for (int t = 0; t < tooths; t++) {
			    	GL11.glRotatef(360F/tooths, 0F, 0F, 1F);
			    	GL11.glTranslatef(0F, 0F, 0.0001F);
			    	
			    	if (gear.size == 3.0F) {
			    		modelGear.renderModel(0.0625F);
			    	}
			    	else if (gear.size == 1F) {
			    		modelSmallGear.renderModel(0.0625F);
			    	}
			    	else if (gear.size == 5F) {
			    		modelBigGear.renderModel(0.0625F);
			    	}
			    	else {
			    		modelInterGear.renderModel(0.0625F);
			    	}
			    }
	    	}
	    }
		    
	    GL11.glPopMatrix();
		

		
	}
}
