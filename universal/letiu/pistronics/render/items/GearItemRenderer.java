package letiu.pistronics.render.items;

import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.models.ModelBigGear;
import letiu.pistronics.render.models.ModelGear;
import letiu.pistronics.render.models.ModelInterGear;
import letiu.pistronics.render.models.ModelSmallGear;
import letiu.pistronics.render.simple.RodRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import letiu.pistronics.data.BlockData;

public class GearItemRenderer extends PItemRenderer {

	private ModelGear modelGear;
	private ModelSmallGear modelSmallGear;
	private ModelBigGear modelBigGear;
	private ModelInterGear modelInterGear;
	
	public GearItemRenderer() {
		modelGear = new ModelGear();
		modelSmallGear = new ModelSmallGear();
		modelBigGear = new ModelBigGear();
		modelInterGear = new ModelInterGear();
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
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
		
		//PItem pItem = ((IBaseItem) item.getItem()).getData();
		
		RenderBlocks renderer = (RenderBlocks) data[0];
		
		
		
		int size = 1, meta = 0;
		boolean renderRod = false;
		
		if (item.stackTagCompound != null) {
			size = item.stackTagCompound.getInteger("size");
			meta = item.stackTagCompound.getInteger("meta");
			renderRod = item.stackTagCompound.getBoolean("rod");
		}
		
		
	    int tooths = (size == 3.0F) ? 24 : (size == 1F ? 8 : (size == 1.5 ? 20 : 40));
	    
	    if (type == ItemRenderType.INVENTORY) {
		    if (size == 3) {
		    	GL11.glScalef(0.45F, 0.45F, 0.45F);
		    }
		    else if (size == 5) {
		    	GL11.glScalef(0.34F, 0.34F, 0.34F);
		    }
	    }
	    
	    if (renderRod) RodRenderer.renderRodInInventory(BlockData.rod.block, 3, (RenderBlocks) data[0]);
	    
	    if (type == ItemRenderType.INVENTORY) {
		    if (size == 1) {
		    	GL11.glTranslatef(0F, 0F, -0.25F);
		    }
	    }

		Minecraft.getMinecraft().getTextureManager().bindTexture(Textures.getPlankTextureFromMeta(meta));
	    
	    for (int t = 0; t < tooths; t++) {
	    	GL11.glRotatef(360F/tooths, 0F, 0F, 1F);
	    	GL11.glTranslatef(0F, 0F, 0.0001F);
	    	
	    	if (size == 3.0F) {
	    		modelGear.renderModel(0.0625F);
	    	}
	    	else if (size == 1F) {
	    		modelSmallGear.renderModel(0.0625F);
	    	}
	    	else if (size == 5F) {
	    		modelBigGear.renderModel(0.0625F);
	    	}
	    	else {
	    		modelInterGear.renderModel(0.0625F);
	    	}
	    }
		
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		}
	}
}
