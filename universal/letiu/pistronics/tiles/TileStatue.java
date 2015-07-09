package letiu.pistronics.tiles;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.NBTUtil;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.render.PTileRenderer;
import org.lwjgl.Sys;

public class TileStatue extends PTile implements ISpecialRenderTile {

    public static final int DEFAULT_RESOLUTION = 4;
    public static final int MAX_RESOLUTION = 16;
    public static final int OVERLAY_RESOLUTION = 8;

	public boolean camou = false;
	public int camouID = -1;
	public int camouMeta = 0;
	
	private int scale = 100;
	private int angle = 0;

    private int resolution = DEFAULT_RESOLUTION;

	// Used for post load //
	private NBTTagCompound nbtData; 
	private Entity entity;
	private ResourceLocation camouTexture;
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

    public void tryLoadEntity() {
        if (nbtData != null) {
            setEntity(nbtData);
        }
    }

	public void setEntity(NBTTagCompound nbt) {
		if (nbt != null) {
			if (nbt.hasKey("entityKey") && nbt.hasKey("entityNBT")) {
				this.entity = EntityList.createEntityByName(nbt.getString("entityKey"), tileEntity.getWorldObj());
				this.entity.readFromNBT(nbt.getCompoundTag("entityNBT"));	
			}
			else {
				this.entity = new EntitySheep(tileEntity.getWorldObj());
			}
		}
	}
	
	public void setEntity(ItemStack stack) {
		if (stack != null && stack.stackTagCompound != null) {
			setEntity(stack.stackTagCompound);
		}
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public boolean tryCamou(Block block, int meta) {
		
		this.camouID = BlockItemUtil.getBlockID(block);
		this.camouMeta = meta;

		if (camou && tileEntity != null && tileEntity.getWorldObj() != null && tileEntity.getWorldObj().isRemote) {
            this.camouTexture = BlockItemUtil.getResourceLocation(block, meta);
			return true;
		}

		return false;
	}

	public ResourceLocation getStatueTexture() {
		
		if (camou) {
			if (camouID == -2) {
				return null;
			}
			else if (camouID != -1 && camouTexture != null) {
				return camouTexture;
			}
		}

        return Textures.stoneTexture;
	}

    public void incrementResolution() {
        this.resolution *= 2;
        if (this.resolution > MAX_RESOLUTION) this.resolution = MAX_RESOLUTION;
    }

    public void decrementResolution() {
        this.resolution /= 2;
        if (this.resolution < 1) this.resolution = 1;
    }

    public void setStatueResolution(int resolution) {
        this.resolution = resolution;
        if (this.resolution < 1) this.resolution = 1;
        if (this.resolution > MAX_RESOLUTION) this.resolution = MAX_RESOLUTION;
    }

    public int getStatueResolution() {
        return (camouID == -1 && camou) ? OVERLAY_RESOLUTION : resolution;
    }
	
	public void rotate(int amt) {
		this.angle = (this.angle + 360 + amt) % 360;
	}
	
	public boolean incScale() {
		this.scale += ConfigData.statueScaleStep;
		if (scale >= ConfigData.statueScaleMax) {
			this.scale = ConfigData.statueScaleMax;
			return false;
		}
		return true;
	}
	
	public boolean decScale() {
		this.scale -= ConfigData.statueScaleStep;
		if (scale <= ConfigData.statueScaleMin) {
			this.scale = ConfigData.statueScaleMin;
			return false;
		}
		return true;
	}
	
	public int getAngle() {
		return angle;
	}
	
	public int getScale() {
		return scale;
	}

    public void setScale(int scale) {
        if (scale < ConfigData.statueScaleMin) this.scale = ConfigData.statueScaleMin;
        else if (scale > ConfigData.statueScaleMax) this.scale = ConfigData.statueScaleMax;
        else this.scale = scale;
    }

    public void setAngle(int angle) {
        this.angle = angle % 360;
    }
	
	@Override
	public String getKey() {
		return TileData.key_statue;
	}

	@Override
	public PTileRenderer getRenderer() {
		return PRenderManager.statueRenderer;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(-5.0D, 0.0D, -5.0D, 6.0D, 6.0D, 6.0D).getOffsetBoundingBox(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {

		tagCompound.setBoolean("camou", camou);
		tagCompound.setInteger("camouID", camouID);
		tagCompound.setInteger("camouMeta", camouMeta);
		
		tagCompound.setInteger("scale", scale);
		tagCompound.setInteger("angle", angle);

        tagCompound.setInteger("resolution", resolution);
		
		if (entity != null) {
			NBTTagCompound entityNBT = new NBTTagCompound();
			entity.writeToNBT(entityNBT);
			
			tagCompound.setString("entityKey", EntityList.getEntityString(entity));
			tagCompound.setTag("entityNBT", entityNBT);
		}
	}
	
	@Override
	public void postLoad() {
		setEntity(nbtData);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		
		this.camou = tagCompound.getBoolean("camou");
		this.camouID = tagCompound.getInteger("camouID");
		this.camouMeta = tagCompound.getInteger("camouMeta");
		
		this.scale = tagCompound.getInteger("scale");
		this.angle = tagCompound.getInteger("angle");

        this.setStatueResolution(tagCompound.getInteger("resolution"));

		if (camouID != -1 && camouID != -2) {
			tryCamou(BlockItemUtil.getBlockByID(camouID), camouMeta);
		}
		
		nbtData = tagCompound;
		if (tileEntity.getWorldObj() != null) setEntity(tagCompound);
	}
	
	@Override
	public NBTTagCompound getNBTForItem() {
		NBTTagCompound tagCompound = NBTUtil.getNewCompound();
		
		tagCompound.setBoolean("camou", camou);
		tagCompound.setInteger("camouID", camouID);
		tagCompound.setInteger("camouMeta", camouMeta);
		
		tagCompound.setInteger("scale", scale);

        tagCompound.setInteger("resolution", resolution);

		if (entity != null) {
			NBTTagCompound entityNBT = new NBTTagCompound();
			entity.writeToNBT(entityNBT);
			
			tagCompound.setString("entityKey", EntityList.getEntityString(entity));
			tagCompound.setTag("entityNBT", entityNBT);
		}
		
		return tagCompound;
	}
}
