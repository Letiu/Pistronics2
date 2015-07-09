package letiu.modbase.render;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class MyTextureManager extends TextureManager {

    private final Map mapScaledTextureObjects = Maps.newHashMap();

	public ResourceLocation texture;
    public ResourceLocation overlay;
    public int factor;
	
	public MyTextureManager(IResourceManager resourceManager) {
        super(resourceManager);
	}

    public void init() {

    }
	
	public boolean hasValidTexture() {
		if (texture == null) return false;
		if (this.getTexture(texture) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void bindTexture(ResourceLocation resourceLocation) {
		if (texture != null) {
            if (factor == 1) {
                super.bindTexture(texture);
            }
            else {
                String texturePath = texture.getResourcePath() + "_statueScale_" + factor;
                if (overlay != null) texturePath += "_overlay_" + overlay.getResourcePath();
                ScaledTexture scaledTexture = (ScaledTexture) this.mapScaledTextureObjects.get(texturePath);

                if (scaledTexture == null) {

                    scaledTexture = new ScaledTexture(texture, factor, overlay);

                    try {
                        scaledTexture.loadTexture(Minecraft.getMinecraft().getResourceManager());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    this.mapScaledTextureObjects.put(texturePath, scaledTexture);
                }

                GL11.glBindTexture(GL11.GL_TEXTURE_2D, scaledTexture.getGlTextureId());
            }
		}
		else {
			super.bindTexture(resourceLocation);
		}
	}

    public static BufferedImage createScaledVersion(BufferedImage bImage, int factor) {
        if (bImage == null) return null;
        if (factor < 1) factor = 1;

        BufferedImage result = new BufferedImage(bImage.getWidth() * factor, bImage.getHeight() * factor, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = result.createGraphics();

        for (int x = 0; x < factor; x++) {
            for (int y = 0; y < factor; y++) {
                graphics2D.drawImage(bImage, bImage.getWidth() * x, bImage.getHeight() * y, null);
            }
        }

        graphics2D.dispose();

        return result;
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) return (BufferedImage) img;

        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bufferedImage;
    }

}
