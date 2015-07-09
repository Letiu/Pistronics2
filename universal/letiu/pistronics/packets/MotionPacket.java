package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.util.BlockProxy;
import net.minecraft.entity.player.EntityPlayer;
import letiu.pistronics.util.Vector3;
import cpw.mods.fml.relauncher.Side;

public class MotionPacket extends LocationPacket<MotionPacket> {

	private int dir;
	private float speed;
	private SystemType type;
	private int size;

	public MotionPacket() {
		
	}
	
	public MotionPacket(int x, int y, int z, int dimID, int dir, int size, float speed, SystemType type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
		this.dir = dir;
		this.size = size;
		this.speed = speed;
		this.type = type;
	}
	
	public MotionPacket(PistonSystem system, int dimID) {
		Vector3 coords = system.getRoot().getCoords();
		this.x = coords.x;
		this.y = coords.y;
		this.z = coords.z;
		this.dimID = dimID;
		this.dir = system.getDir();
		this.size = system.getSystemSize();
		this.speed = system.getSpeed();
		this.type = system.getSystemType();
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(dir);
		out.writeInt(size);
		out.writeFloat(speed);
		out.writeInt(type.ordinal());
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		this.dir = in.readInt();
		this.size = in.readInt();
		this.speed = in.readFloat();
		this.type = SystemType.values()[in.readInt()];
	}
	
	@Override
	public void execute(MotionPacket packet, EntityPlayer player, Side side) {
		
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			BlockProxy proxy = new BlockProxy(player.worldObj, packet.x, packet.y, packet.z);
			if (proxy.isPistonElement()) {
				PistonSystem system = new PistonSystem(proxy, packet.dir, packet.speed, packet.type);
				
				if (system.getSystemSize() == packet.size) {
				
					if (packet.type == SystemType.MOVE) system.clientMove();
					else system.clientRotate();
					
				}
			}
		}
	}
}
