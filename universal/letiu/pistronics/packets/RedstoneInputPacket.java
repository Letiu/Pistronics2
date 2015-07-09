package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.modbase.util.WorldUtil;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class RedstoneInputPacket extends LocationPacket<RedstoneInputPacket> {

	private int strength;

	public RedstoneInputPacket() {
		
	}
	
	public RedstoneInputPacket(int x, int y, int z, int dimID, int strength) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
		this.strength = strength;
	}
	
	public RedstoneInputPacket(Vector3 coords, int dimID, int strength) {
		this.x = coords.x;
		this.y = coords.y;
		this.z = coords.z;
		this.dimID = dimID;
		this.strength = strength;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(strength);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		this.strength = in.readInt();
	}
	
	@Override
	public void execute(RedstoneInputPacket packet, EntityPlayer player, Side side) {
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			BlockProxy proxy = new BlockProxy(player.worldObj, packet.x, packet.y, packet.z);
			PistonSystem system = new PistonSystem(proxy, 0, 0, SystemType.REDSTONE);
			system.setToInput(proxy, packet.strength);
			WorldUtil.updateBlock(player.worldObj, packet.x, packet.y, packet.z);
		}
	}
}
