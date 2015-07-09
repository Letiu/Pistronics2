package letiu.pistronics.tiles;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.NBTUtil;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.modbase.tiles.BaseTile;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileElementHolder extends PTile {

	protected Block element;
	protected TileEntity elementTile;
	protected int elementMeta;
	
	public void setContent(Block element, int elementMeta, TileEntity elementTile) {
		this.element = element;
		this.elementMeta = elementMeta;
		this.elementTile = elementTile;
	}
	
	public void clear() {
		this.element = null;
		this.elementTile = null;
		this.elementMeta = 0;
	}
	
	@Override
	public void update() {
		if (elementTile != null && elementTile instanceof BaseTile) {
			((BaseTile) elementTile).data.update();
		}
	}
	
	public boolean hasElement() {
		return element != null;
	}
	
	public void setElement(PBlock element) {
		if (element == null) {
			this.element = null;
			this.elementTile = null;
			this.elementMeta = 0;
		}
		else {
			this.element = element.block;
			if (element != null && element.hasTileEntity()) {
				this.elementTile = element.block.createTileEntity(tileEntity.getWorldObj(), elementMeta);
				this.elementTile.setWorldObj(tileEntity.getWorldObj());
				this.elementTile.xCoord = this.tileEntity.xCoord;
				this.elementTile.yCoord = this.tileEntity.yCoord;
				this.elementTile.zCoord = this.tileEntity.zCoord;
			}
		}
	}
	
	public void setElementMeta(int meta) {
		this.elementMeta = meta;
	}
	
	public Block getElement() {
		return element;
	}
	
	public PBlock getPElement() {
		if (element != null && element instanceof BaseBlock) {
			return ((BaseBlock) this.element).data;
		}
		return null;
	}
	
//	public void setPElementTile(PTile elementTile) {
//		this.elementTile = elementTile.tileEntity;
//	}
	
	public PTile getPElementTile() {
		if (elementTile != null) {
			return ((BaseTile) this.elementTile).data;
		}
		return null;
	}
	
	public void setElementTile(TileEntity tile) {
		this.elementTile = tile;
	}
	
	public TileEntity getElementTile() {
		return elementTile;
	}
	
	public int getElementMeta() {
		return elementMeta;
	}
	
    public float getOffsetX(float ticktime) {
		return 0.0F;
        //return -1 * (this.retrieving ? (this.getProgress(prog) - 1.0F) * (float)Facing.offsetsXForSide[this.getDirection()] : (1.0F - this.getProgress(prog)) * (float)Facing.offsetsXForSide[this.getDirection()]);
    }

    public float getOffsetY(float ticktime) {
    	return 0.0F;
        //return -1 * (this.retrieving ? (this.getProgress(prog) - 1.0F) * (float)Facing.offsetsYForSide[this.getDirection()] : (1.0F - this.getProgress(prog)) * (float)Facing.offsetsYForSide[this.getDirection()]);
    }

    public float getOffsetZ(float ticktime) {
    	return 0.0F;
        //return -1 * (this.retrieving ? (this.getProgress(prog) - 1.0F) * (float)Facing.offsetsZForSide[this.getDirection()] : (1.0F - this.getProgress(prog)) * (float)Facing.offsetsZForSide[this.getDirection()]);
    }
	
    @Override
    public void postLoad() {
    	if (this.elementTile != null) {
    		this.elementTile.setWorldObj(tileEntity.getWorldObj());
    	}
    }
    
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {

		tagCompound.setInteger("elementMeta", elementMeta);
		
		if (element != null) {
			tagCompound.setInteger("element", BlockItemUtil.getBlockID(element));
		}
	
		if (elementTile != null) {
			NBTTagCompound elementTileNBT = NBTUtil.getNewCompound();
			elementTile.writeToNBT(elementTileNBT);
			tagCompound.setTag("elementTile", elementTileNBT);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		
		this.elementMeta = tagCompound.getInteger("elementMeta");
		
		int elementID = tagCompound.getInteger("element");
		if (elementID != 0) {
			this.element = BlockItemUtil.getBlockByID(elementID);
			if (element != null && element.hasTileEntity(elementMeta) && tileEntity != null) {
				elementTile = element.createTileEntity(tileEntity.getWorldObj(), elementMeta);
				if (elementTile != null && tagCompound.hasKey("elementTile")) {
					elementTile.readFromNBT(tagCompound.getCompoundTag("elementTile"));
					elementTile.setWorldObj(tileEntity.getWorldObj());
					this.elementTile.xCoord = this.tileEntity.xCoord;
					this.elementTile.yCoord = this.tileEntity.yCoord;
					this.elementTile.zCoord = this.tileEntity.zCoord;
				}
			}
		}
	}

}
