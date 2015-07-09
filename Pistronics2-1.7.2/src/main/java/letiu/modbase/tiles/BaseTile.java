package letiu.modbase.tiles;

import letiu.modbase.util.NBTUtil;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class BaseTile extends TileEntity {

	public PTile data;
	
	@Override
	public void updateEntity() {
		data.update();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		AxisAlignedBB box = data.getRenderBoundingBox();
		return box == null ? super.getRenderBoundingBox() : box;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		super.getDescriptionPacket();
		NBTTagCompound tag = NBTUtil.getNewCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
	}
		
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
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
