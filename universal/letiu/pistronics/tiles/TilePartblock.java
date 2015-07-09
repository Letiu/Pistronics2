package letiu.pistronics.tiles;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import letiu.pistronics.packets.SawPacket;
import letiu.pistronics.reference.Redstone;
import letiu.pistronics.util.BlockProxy;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TilePartblock extends PTile implements ITransmitter {
	
	public boolean dirty;
	
	protected int strength = 0;
	protected int pulseTicks = 0;
	
	protected boolean checkInput = false;
	
	private PBlock[] parts = new PBlock[6];
	private PTile[] tiles = new PTile[6];
	
	protected byte neighborStates = 0;
	protected byte updateCounter = Redstone.TICK_RATE;
	
	/** Sets part if it is an IPart and also sets the TileEntity if available. */
	public void setPart(PBlock block, int side) {
		if (side < 0 || side >= parts.length) throw new IllegalArgumentException("side out of bound");
		if (block == null) {
			parts[side] = null;
			tiles[side] = null;
			
			if (!hasPart()) {
				WorldUtil.setBlockToAir(tileEntity);
			}
		}
		if (block instanceof IPart) {
			parts[side] = block;
			if (block.hasTileEntity()) {
				tiles[side] = block.createPTile();
				if (tiles[side] != null) {
					tiles[side].tileEntity = tileEntity;
				}
			}
		}
	}
	
	/** Will set the part without any checks or Tile creation, in most cases setPart() is the better choice. */
	public void setPartSolo(PBlock block, int side) {
		parts[side] = block;
	}
	
	public PBlock getPart(int side) {
		return parts[side];
	}
	
	public boolean hasPart(int side) {
		return parts[side] != null;
	}
	
	public boolean hasPart() {
		for (int i = 0; i < 6; i++) {
			if (parts[i] != null) return true;
		}
		return false;
	}
	
	public PTile getTile(int side) {
		return tiles[side];
	}
	
	public void setTile(PTile tile, int side) {
		tiles[side] = tile;
	}
	
	@Override
	public String getKey() {
		return TileData.key_partblock;
	}
	
	@Override
	public void update() {
		
		if (dirty && tileEntity.getWorldObj().isRemote) {
			WorldUtil.updateBlock(tileEntity);
			this.dirty = false;
		}
		
		if (pulseTicks > 0) {
			pulseTicks--;
			if (pulseTicks == 0) {
				WorldUtil.updateBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			}
		}
		
		updateTiles();
		
		if (checkInput) {
			WorldUtil.notifyBlockOfNeighborChange(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, 0);
			checkInput = false;
		}
	}
	
	private void updateTiles() {
		for (int i = 0; i < 6; i++) {
			if (tiles[i] != null) {
				tiles[i].update();
			}
		}
	}
	
	@Override
	public boolean isConductive() {
		for (int i = 0; i < 6; i++) {
			if (tiles[i] != null && tiles[i] instanceof ITransmitter) {
				if (((ITransmitter) tiles[i]).isConductive()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isInput() {
		// not needed //
		return false;
	}
	
	@Override
	public boolean isActive() {
		return strength > 0 || pulseTicks > 0;
	}
	
	@Override
	public int getStrength() {
		return pulseTicks > 0 ? 15 : strength;
	}
	
	@Override
	public void notifyOutputBlocks(BlockProxy proxy) {
		
		for (int i = 0; i < 6; i++) {
			if (tiles[i] != null && tiles[i] instanceof TileExtension) {
				if (!((TileExtension) tiles[i]).isInput()) {
					proxy.getNeighbor(i).notifyBlockOfNeighborChange(0);
				}
			}
		}
	}

	@Override
	public void checkNextInput() {
		this.checkInput = true;
	}
	
	@Override
	public void setStrength(int strength) {
		this.strength = strength;
		for (int i = 0; i < 6; i++) {
			if (tiles[i] != null && tiles[i] instanceof ITransmitter) {
				ITransmitter trans = (ITransmitter) tiles[i];
				if (trans.isConductive()) {
					trans.setStrength(strength);
				}
			}
		}
	}
	
	public void pulse(int ticks) {
		this.pulseTicks = ticks;
		for (int i = 0; i < 6; i++) {
			if (tiles[i] != null && tiles[i] instanceof ITransmitter) {
				ITransmitter trans = (ITransmitter) tiles[i];
				if (trans.isConductive()) {
					trans.pulse(ticks);
				}
			}
		}
	}

	@Override
	public void setToInput() {
		for (int i = 0; i < 6; i++) {
			if (tiles[i] != null && tiles[i] instanceof ITransmitter) {
				ITransmitter trans = (ITransmitter) tiles[i];
				if (trans.isConductive()) {
					trans.setToInput();
				}
			}
		}
	}

	@Override
	public void setToOutput() {
		for (int i = 0; i < 6; i++) {
			if (tiles[i] != null && tiles[i] instanceof ITransmitter) {
				ITransmitter trans = (ITransmitter) tiles[i];
				if (trans.isConductive()) {
					trans.setToOutput();
				}
			}
		}
	}
	
	public void checkIntegrity(int dimID) {
		if (!tileEntity.getWorldObj().isRemote) {
			for (int i = 0; i < 6; i++) {
				if (parts[i] != null) {
					if (!((IPart) parts[i]).canPartStay(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, i)) {
						WorldUtil.spawnItemStack(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, 
								parts[i].getDroppedStack(tiles[i], 0));
						this.setPart(null, i);
						PacketHandler.sendToAllInDimension(new SawPacket(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, i, dimID), dimID);
					}
				}
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		int[] partIDs = new int[6];
		for (int i = 0; i < 6; i++) {
			if (parts[i] == null) {
				partIDs[i] = 0;
			}
			else {
				partIDs[i] = BlockItemUtil.getBlockID(parts[i].block);
			}
		}
		tagCompound.setIntArray("partIDs", partIDs);
	
		NBTTagList nbtList = new NBTTagList();
		for (int i = 0; i < 6; i++) {
			NBTTagCompound partTileNBT = NBTUtil.getNewCompound();
			if (tiles[i] != null) {
				tiles[i].writeToNBT(partTileNBT);
			}
			nbtList.appendTag(partTileNBT);
		}
		tagCompound.setTag("tiles", nbtList);
		
		tagCompound.setInteger("pulseTicks", pulseTicks);
		tagCompound.setInteger("strength", strength);
		tagCompound.setBoolean("checkInput", checkInput);
		tagCompound.setByte("neighborStates", neighborStates);
		tagCompound.setByte("updateCounter", updateCounter);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		int[] partIDs = tagCompound.getIntArray("partIDs");
		for (int i = 0; i < 6; i++) {
			if (partIDs[i] != 0) {
				Block block = BlockItemUtil.getBlockByID(partIDs[i]);
				if (block instanceof BaseBlock) {
					this.setPart(((BaseBlock) block).data, i);
				}
			}
		}
		
		NBTTagList nbtList = (NBTTagList) tagCompound.getTag("tiles");
		for (int i = 0; i < 6; i++) {
			if (tiles[i] != null) {
				tiles[i].readFromNBT(NBTUtil.getCompoundAt(nbtList, i));
			}
		}
		
		this.pulseTicks = tagCompound.getInteger("pulseTicks");
		this.strength = tagCompound.getInteger("strength");
		this.checkInput = tagCompound.getBoolean("checkInput");
		this.neighborStates = tagCompound.getByte("neighborStates");
		this.updateCounter = tagCompound.getByte("updateCounter");
		
		this.dirty = true;
	}
	
	@Override
	public void postLoad() {
		for (int i = 0; i < 6; i++) {
			if (tiles[i] != null) {
				tiles[i].tileEntity = tileEntity;
			}
		}
	}
}
