package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.PTile;
import letiu.pistronics.gears.Gear;
import letiu.pistronics.tiles.TileGear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;

public class GearDataPacket extends LocationPacket<GearDataPacket> {

	private Gear gear0, gear1, gear2;
	private boolean hasRod;
	
	public GearDataPacket() {
		
	}
	
	public GearDataPacket(TileGear gearTile, int dimID) {
		this.x = gearTile.tileEntity.xCoord;
		this.y = gearTile.tileEntity.yCoord;
		this.z = gearTile.tileEntity.zCoord;
		this.dimID = dimID;
		
		this.hasRod = gearTile.hasRod;
		this.gear0 = gearTile.getGear(0);
		this.gear1 = gearTile.getGear(1);
		this.gear2 = gearTile.getGear(2);
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeBoolean(hasRod);
		
		if (gear0 == null) {
			out.writeBoolean(false);
		}
		else {
			out.writeBoolean(true);
			gear0.writeToStream(out);
		}
		
		if (gear1 == null) {
			out.writeBoolean(false);
		}
		else {
			out.writeBoolean(true);
			gear1.writeToStream(out);
		}
		
		if (gear2 == null) {
			out.writeBoolean(false);
		}
		else {
			out.writeBoolean(true);
			gear2.writeToStream(out);
		}
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		this.hasRod = in.readBoolean();
		
		if (in.readBoolean()) {
			gear0 = Gear.readFromStream(in);
		}
		
		if (in.readBoolean()) {
			gear1 = Gear.readFromStream(in);
		}
		
		if (in.readBoolean()) {
			gear2 = Gear.readFromStream(in);
		}
	}
	
	@Override
	public void execute(GearDataPacket packet, EntityPlayer player, Side side) {
		this.x = packet.x;
		this.y = packet.y;
		this.z = packet.z;
		World world = player.worldObj;
		
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			PTile pTile = WorldUtil.getPTile(world, x, y, z);
			if (pTile != null && pTile instanceof TileGear) {
				TileGear gearTile = (TileGear) pTile;
				
				gearTile.hasRod = packet.hasRod;
				gearTile.setGear(packet.gear0, 0);
				gearTile.setGear(packet.gear1, 1);
				gearTile.setGear(packet.gear2, 2);
				
				WorldUtil.updateBlock(world, x, y, z);
			}
		}
	}
}
