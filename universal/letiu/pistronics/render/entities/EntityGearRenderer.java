package letiu.pistronics.render.entities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import letiu.modbase.render.UniversalEntityRenderer;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.models.ModelBigGear;
import letiu.pistronics.render.models.ModelGear;
import letiu.pistronics.render.models.ModelInterGear;
import letiu.pistronics.render.models.ModelSmallGear;

public class EntityGearRenderer extends UniversalEntityRenderer {

	private ModelGear modelGear;
	private ModelSmallGear modelSmallGear;
	private ModelBigGear modelBigGear;
	private ModelInterGear modelInterGear;
	
	public EntityGearRenderer() {
		modelGear = new ModelGear();
		modelSmallGear = new ModelSmallGear();
		modelBigGear = new ModelBigGear();
		modelInterGear = new ModelInterGear();
	}
	
	@Override
	public void renderEntity(Entity entity, double x, double y, double z, float var8, float var9) {
		
		GL11.glPushMatrix();

		GL11.glTranslated(x, y, z);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("pistronics", Textures.OAK_WOOD_PLANKS));
	    
	    int tooths = 24; //(tl.size == 3.0F) ? 24 : (tl.size == 1F ? 8 : (tl.size == 1.5 ? 20 : 40));
	    
	    for (int t = 0; t < tooths; t++) {
	    	GL11.glRotatef(360F/tooths, 0F, 0F, 1F);
	    	GL11.glTranslatef(0F, 0F, 0.0001F);
	    	modelGear.renderModel(0.0625F);
	    	/*if (tl.size == 3.0F) modelGear.renderModel(0.0625F);
	    	else if (tl.size == 1F) {
	    		modelSmallGear.renderModel(0.0625F);
	    	}
	    	else if (tl.size == 5F) {
	    		modelBigGear.renderModel(0.0625F);
	    	}
	    	else {
	    		modelInterGear.renderModel(0.0625F);
	    	}*/
	    } 
      
        GL11.glPopMatrix();
	}
	
}
