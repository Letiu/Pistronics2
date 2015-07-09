package letiu.pistronics.tiles;

import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.reference.Redstone;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.RedstoneUtil;
import net.minecraft.nbt.NBTTagCompound;

public class TileRod extends PTile implements ITransmitter {
	
	public boolean redstone;
	
	protected boolean inOut;
	protected int strength = 0;
	protected int pulseTicks = 0;
	
	protected byte neighborStates = 0;
	protected byte updateCounter = Redstone.TICK_RATE;
	
	@Override
	public String getKey() {
		return TileData.key_rod;
	}
	
	@Override
	public void update() {
		if (pulseTicks > 0) {
			pulseTicks--;
			if (pulseTicks == 0) {
				WorldUtil.updateBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			}
		}
		
		if (!tileEntity.getWorldObj().isRemote) {
			updateCounter--;
			if (updateCounter == 0) {
				BlockProxy proxy = new BlockProxy(this);
				byte newStates = RedstoneUtil.getNeighborStates(proxy);
				if (newStates != neighborStates) {
					neighborStates = newStates;
					PistonSystem.checkIntegrity(proxy);
				}
				updateCounter = Redstone.TICK_RATE;
			}
		}
	}
	
	@Override
	public boolean isConductive() {
		return redstone;
	}

	@Override
	public boolean isInput() {
		return false;// inOut;
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
	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public void pulse(int ticks) {
		this.pulseTicks = ticks;
	}

	@Override
	public void setToInput() {
		//this.inOut = false;
	}

	@Override
	public void setToOutput() {
		this.inOut = true;
	}
	
	@Override
	public void notifyOutputBlocks(BlockProxy proxy) {}

	@Override
	public void checkNextInput() {}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		tagCompound.setBoolean("redstone", redstone);
		tagCompound.setInteger("pulseTicks", pulseTicks);
		tagCompound.setBoolean("inOut", inOut);
		tagCompound.setInteger("strength", strength);
		tagCompound.setByte("neighborStates", neighborStates);
		tagCompound.setByte("updateCounter", updateCounter);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		this.redstone = tagCompound.getBoolean("redstone");
		this.pulseTicks = tagCompound.getInteger("pulseTicks");
		this.inOut = tagCompound.getBoolean("inOut");
		this.strength = tagCompound.getInteger("strength");
		this.neighborStates = tagCompound.getByte("neighborStates");
		this.updateCounter = tagCompound.getByte("updateCounter");
	}
	
	@Override
	public NBTTagCompound getNBTForItem() {
		NBTTagCompound tagCompound = NBTUtil.getNewCompound();
		
		tagCompound.setBoolean("redstone", redstone);
		
		return tagCompound;
	}
	
	public static NBTTagCompound getNBTForItem(NBTTagCompound source) {
		NBTTagCompound tagCompound = NBTUtil.getNewCompound();
		
		tagCompound.setBoolean("redstone", source.getBoolean("redstone"));
		
		return tagCompound;
	}
}
