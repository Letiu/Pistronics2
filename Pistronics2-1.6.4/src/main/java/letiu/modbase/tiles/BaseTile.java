package letiu.modbase.tiles;

import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class BaseTile extends TileEntity {

	public PTile data;
	
	@Override
	public void updateEntity() {
		data.update();
	}
	
	@Override
	public Packet getDescriptionPacket() {
		Packet132TileEntityData packet = new Packet132TileEntityData();
		packet.xPosition = this.xCoord;
		packet.yPosition = this.yCoord;
		packet.zPosition = this.zCoord;
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		packet.data = nbt;
		return packet;
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		this.readFromNBT(packet.data);
	}
	
	@Override
	public void validate() {
		super.validate();
		data.postLoad();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setString("key", data.getKey());
		data.writeToNBT(tagCompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		data = TileData.getTile(tagCompound.getString("key"));
		data.tileEntity = this;
		data.readFromNBT(tagCompound);
	}
	
}
