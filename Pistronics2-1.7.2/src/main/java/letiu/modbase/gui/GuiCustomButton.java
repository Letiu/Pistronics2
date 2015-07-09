package letiu.modbase.gui;

import letiu.pistronics.util.Vector2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiCustomButton extends GuiButton {
	
	protected boolean inverted;
	protected Vector2 uv1, uv2;

	protected static final ResourceLocation buttonIcon = new ResourceLocation("pistronics", "textures/blocks/gui/testIcon.png");
	
	public GuiCustomButton(int ID, int x, int y, int width, int height, String label, Vector2 uv1, Vector2 uv2) {
		super(ID, x, y, width, height, label);
		inverted = false;
		this.uv1 = uv1;
		this.uv2 = uv2;
	}
	
	public void setInverted(boolean value) {
		this.inverted = value;
	}
	
	public boolean isInverted() {
		return this.inverted;
	}
	
	@Override
	public void drawButton(Minecraft minecraft, int x, int y) {
	
		if (this.visible)
        {
            FontRenderer fontrenderer = minecraft.fontRenderer;
            minecraft.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            
            minecraft.getTextureManager().bindTexture(buttonIcon);
            if (inverted) {
            	this.drawTexturedModalRect(this.xPosition, this.yPosition, uv2.x, uv2.y, width, height);
            }
            else {
            	this.drawTexturedModalRect(this.xPosition, this.yPosition, uv1.x, uv1.y, width, height);
            }
            
            this.mouseDragged(minecraft, x, y);
        }
	}
	
	/*
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {

        FontRenderer fontrenderer = par1Minecraft.fontRenderer;
        par1Minecraft.getTextureManager().bindTexture(buttonTextures);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        int k = this.getHoverState(this.field_82253_i);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
        
        par1Minecraft.getTextureManager().bindTexture(buttonIcon);
        if (inverted) {
        	this.drawTexturedModalRect(this.xPosition, this.yPosition, uv2.x, uv2.y, 20, 20);
        }
        else {
        	this.drawTexturedModalRect(this.xPosition, this.yPosition, uv1.x, uv1.y, 20, 20);
        }
        
        this.mouseDragged(par1Minecraft, par2, par3);
    }*/

}
