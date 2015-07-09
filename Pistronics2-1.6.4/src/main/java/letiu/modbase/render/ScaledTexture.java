package letiu.modbase.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;

@SideOnly(Side.CLIENT)
public class ScaledTexture extends AbstractTexture {

    protected final ResourceLocation textureLocation;
    protected final ResourceLocation overlayLocation;
    private final int scale;

    public ScaledTexture(ResourceLocation resourceLocation, int scale) {
        this.textureLocation = resourceLocation;
        this.scale = scale;
        this.overlayLocation = null;
    }

    public ScaledTexture(ResourceLocation resourceLocation, int scale, ResourceLocation overlay) {
        this.textureLocation = resourceLocation;
        this.scale = scale;
        this.overlayLocation = overlay;
    }

    @Override
    public void loadTexture(ResourceManager resourceManager) {
        //this.deleteGlTexture();
        InputStream inputstream = null;

        try
        {
            Resource resource = resourceManager.getResource(this.textureLocation);
            inputstream = resource.getInputStream();
            BufferedImage bufferedimage = ImageIO.read(inputstream);

            // scale texture //
            bufferedimage = MyTextureManager.createScaledVersion(bufferedimage, scale);

            // apply overlay //
            if (overlayLocation != null) {
                BufferedImage overlay = ImageIO.read(resourceManager.getResource(this.overlayLocation).getInputStream());
                if (overlay != null) {
                    bufferedimage.getGraphics().drawImage(overlay, 0, 0, (ImageObserver) null);
                }
            }

            boolean flag = false;
            boolean flag1 = false;

            if (resource.hasMetadata())
            {
                try
                {
                    TextureMetadataSection texturemetadatasection = (TextureMetadataSection)resource.getMetadata("texture");

                    if (texturemetadatasection != null)
                    {
                        flag = texturemetadatasection.getTextureBlur();
                        flag1 = texturemetadatasection.getTextureClamp();
                    }
                }
                catch (RuntimeException runtimeexception)
                {
                    //logger.warn("Failed reading metadata of: " + this.textureLocation, runtimeexception);
                }
            }

            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedimage, flag, flag1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally
        {
            if (inputstream != null)
            {
                try {
                    inputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}