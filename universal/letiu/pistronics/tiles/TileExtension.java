package letiu.pistronics.tiles;

import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BExtensionPart;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.reference.Redstone;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.RedstoneUtil;
import net.minecraft.nbt.NBTTagCompound;

public class TileExtension extends PTile implements ITransmitter {
	
	public boolean sticky, super_sticky, redstone, camou, redio;
	
	public int camouID = -1;
	public int camouMeta;
	
	protected int comp = 0; 
	
	protected boolean inOut = false;
	protected boolean checkInput = false;
	protected int strength = 0;
	protected int pulseTicks = 0;
	
	protected byte neighborStates = 0;
	protected byte updateCounter = Redstone.TICK_RATE;
	
	@Override
	public String getKey() {
		return TileData.key_extension;
	}
	
	@Override
	public void update() {
		if (pulseTicks > 0) {
			pulseTicks--;
			if (pulseTicks == 0) {
				WorldUtil.updateBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
				notifyOutputBlocks(new BlockProxy(this));
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
			
			if (checkInput) {
				BlockProxy proxy = new BlockProxy(this);
				proxy.notifyBlockOfNeighborChange(0);
				notifyOutputBlocks(proxy);
				checkInput = false;
			}
		}
		
//		if (tileEntity != null && tileEntity.getWorldObj() != null && !tileEntity.getWorldObj().isRemote) {
//			if (checkInput == 0) {
//				BlockProxy proxy = new BlockProxy(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
//				proxy.notifyBlockOfNeighborChange(0);
//				notifyOutputBlocks(proxy);
//				checkInput = TICK_RATE;
//			}
//			else checkInput--;
//		}
	}
	
	public void setComp(int comp) {
		if (this.comp != comp) {
			this.comp = comp;
			BlockProxy proxy = new BlockProxy(this);
			proxy.notifyBlockOfNeighborChange(0);
			if (!isInput() && isActive()) notifyOutputBlocks(proxy);
		}
	}
	
	public void incrementComp() {
		setComp((comp + 1) % 16);
	}
	
	public void decrementComp() {
		setComp((comp + 15) % 16);
	}
	
	public int getComp() {
		return comp;
	}
	
	@Override
	public boolean isConductive() {
		return redstone;
	}
	
	@Override
	public boolean isInput() {
		return !inOut;
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
		this.inOut = false;
	}

	@Override
	public void setToOutput() {
		this.inOut = true;
	}
	
//	@Override
//	public int getInputStrength(BlockProxy proxy) {
//		return proxy.getPowerInputFrom(proxy.getFacing());
//	}
	
	@Override
	public void notifyOutputBlocks(BlockProxy proxy) {
		PTile tile = proxy.getPTile();
		if (tile != null && tile instanceof TilePartblock) {
			TilePartblock tileP = (TilePartblock) tile;
			for (int i = 0; i < 6; i++) {
				if (tileP.getPart(i) instanceof BExtensionPart) {
					BlockProxy neighbor = proxy.getNeighbor(i);
					neighbor.notifyBlockOfNeighborChange(0);
				}
			}
		}
		else {
			int facing = proxy.getFacing();
			proxy = proxy.getNeighbor(facing);
			proxy.notifyBlockOfNeighborChange(0);
			proxy = proxy.getNeighbor(facing);
			proxy.notifyNeighbors(0);			
		}
	}
	
	@Override
	public void checkNextInput() {
		this.checkInput = true;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		tagCompound.setBoolean("sticky", sticky);
		tagCompound.setBoolean("super_sticky", super_sticky);
		tagCompound.setBoolean("redstone", redstone);
		tagCompound.setBoolean("camou", camou);
		tagCompound.setInteger("camouID", camouID);
		tagCompound.setInteger("camouMeta", camouMeta);
		tagCompound.setInteger("pulseTicks", pulseTicks);
		tagCompound.setBoolean("inOut", inOut);
		tagCompound.setInteger("strength", strength);
		tagCompound.setBoolean("checkInput", checkInput);
		tagCompound.setByte("neighborStates", neighborStates);
		tagCompound.setByte("updateCounter", updateCounter);
		tagCompound.setBoolean("redio", redio);
		tagCompound.setInteger("comp", comp);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		this.sticky = tagCompound.getBoolean("sticky");
		this.super_sticky = tagCompound.getBoolean("super_sticky");
		this.redstone = tagCompound.getBoolean("redstone");
		this.camou = tagCompound.getBoolean("camou");
		this.camouID = tagCompound.getInteger("camouID");
		this.camouMeta = tagCompound.getInteger("camouMeta");
		this.pulseTicks = tagCompound.getInteger("pulseTicks");
		this.inOut = tagCompound.getBoolean("inOut");
		this.strength = tagCompound.getInteger("strength");
		this.checkInput = tagCompound.getBoolean("checkInput");
		this.neighborStates = tagCompound.getByte("neighborStates");
		this.updateCounter = tagCompound.getByte("updateCounter");
		this.redio = tagCompound.getBoolean("redio");
		this.comp = tagCompound.getInteger("comp");
	}
	
	@Override
	public NBTTagCompound getNBTForItem() {
		NBTTagCompound tagCompound = NBTUtil.getNewCompound();
		
		tagCompound.setBoolean("sticky", sticky);
		tagCompound.setBoolean("super_sticky", super_sticky);
		tagCompound.setBoolean("redstone", redstone);
		tagCompound.setBoolean("camou", camou);
		tagCompound.setInteger("camouID", camouID);
		tagCompound.setInteger("camouMeta", camouMeta);
		tagCompound.setBoolean("redio", redio);
		tagCompound.setInteger("comp", comp);
		
		return tagCompound;
	}
}

