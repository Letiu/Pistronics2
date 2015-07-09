package letiu.modbase.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCustomSlider extends GuiButton
{
    /** The value of this slider control. */
    public float sliderValue = 1.0F;

    /** Is this slider control being dragged. */
    public boolean dragging;
    
    public int options;
    public int option;
    
    public String name;
    private float step;

    public GuiCustomSlider(int ID, int x, int y, String displayString, float value)
    {
        super(ID, x, y, 150, 20, displayString);
        this.sliderValue = value;
        this.width = 20;
        this.name = displayString;
    }
    
    public GuiCustomSlider(int ID, int x, int y, int width, int height, String displayString, float value)
    {
        this(ID, x, y, displayString, value);
        this.width = width;
        this.height = height;
        this.options = -1;
        this.option = -1;
        this.name = displayString;
    }
    
    public GuiCustomSlider(int ID, int x, int y, int width, int height, int options, int start, String displayString)
    {
        this(ID, x, y, displayString, 0F);
        this.width = width;
        this.height = height;
        this.options = options - 1;
        this.option = start - 1;
        this.name = displayString;
        this.step = 1F / this.options;
        updateValue();
        updateDisplayString();
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    public int getHoverState(boolean par1)
    {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.visible)
        {
            if (this.dragging)
            {
                this.sliderValue = (float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8);

                if (this.sliderValue < 0.0F)
                {
                    this.sliderValue = 0.0F;
                }

                if (this.sliderValue > 1.0F)
                {
                    this.sliderValue = 1.0F;
                }
                
                if (options != -1) {
                	option = Math.round(sliderValue / step);
                	sliderValue = (float) (option * step);
                	updateDisplayString();
                }

            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if (super.mousePressed(par1Minecraft, par2, par3))
        {
            this.sliderValue = (float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8);

            if (this.sliderValue < 0.0F)
            {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F)
            {
                this.sliderValue = 1.0F;
            }

            if (options != -1) {
            	option = Math.round(sliderValue / step);
            	sliderValue = (float) (option * step);
            	updateDisplayString();
            }
            
            this.dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public void mouseReleased(int par1, int par2)
    {
        this.dragging = false;
    }
    
    public void increment() {
    	if (options != -1 && option < options) {
    		option++;
    		updateValue();
    		updateDisplayString();
        }
    }
    
    public void decrement() {
    	if (options != -1 && option != 0) {
    		option--;
    		updateValue();
    		updateDisplayString();
        }
    }
    
    public void setFloatValue(float value) {
    	option = Math.round(value / step);
    	sliderValue = (float) (option * step);
    	updateDisplayString();
    }
    
    public void setOption(int option) {
    	this.option = option;
    	sliderValue = (float) (option * step);
    	updateDisplayString();
    }
    
    public void updateValue() {
    	if (options != -1) {
        	sliderValue = (float) (option * step);
        }
    }
    
    public void updateDisplayString() {
    	if (options != -1) {
    		this.displayString = this.name + ": " + (option + 1);
    	}
    }
}