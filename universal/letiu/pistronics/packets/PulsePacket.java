package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.util.BlockProxy;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class PulsePacket extends LocationPacket<PulsePacket> {

	private int pulseTicks;

	public PulsePacket() {
		
	}
	
	public PulsePacket(int x, int y, int z, int dimID, int pulseTicks) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
		this.pulseTicks = pulseTicks;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(pulseTicks);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		this.pulseTicks = in.readInt();
	}
	
	@Override
	public void execute(PulsePacket packet, EntityPlayer player, Side side) {
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			BlockProxy proxy = new BlockProxy(player.worldObj, packet.x, packet.y, packet.z);
			if (proxy.isPistonElement()) {
				PistonSystem system = new PistonSystem(proxy, 0, 0, SystemType.REDSTONE);
				system.pulse(packet.pulseTicks);
				//WorldUtil.updateBlock(player.worldObj, packet.x, packet.y, packet.z);
			}
		}
	}
}
