package letiu.pistronics.render.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelInterGear extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
  
  public ModelInterGear()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      Shape1 = new ModelRenderer(this, 28, 0);
      Shape1.addBox(0F, 0F, 0F, 4, 4, 4);
      Shape1.setRotationPoint(4F, 16.5F, 4F);
      Shape1.setTextureSize(128, 128);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, -45F, -2.356194F);
      Shape2 = new ModelRenderer(this, 0, 0);
      Shape2.addBox(0F, 0F, 0F, 6, 16, 8);
      Shape2.setRotationPoint(-3F, 0F, 0F);
      Shape2.setTextureSize(128, 128);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Shape1.render(f5);
    Shape2.render(f5);
  }
  
  public void renderModel(float f5) {
	  //GL11.glRotatef(180, 1F, 0F, 0F);
	 
	  
	  Shape1.render(f5);
	  //GL11.glTranslatef(0F, 0F, -0.0001F);
	  Shape2.render(f5);
	  //GL11.glTranslatef(0F, 0F, 0.0001F);
	  

	 // GL11.glRotatef(180, 1F, 0F, 0F);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    //super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }

}