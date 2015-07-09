package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import letiu.modbase.network.CustomPacket;

public abstract class LocationPacket<T extends CustomPacket<T>> extends CustomPacket<T> {

	protected int x, y, z, dimID;

	public int getDimension() {
		return dimID;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeInt(dimID);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		this.x = in.readInt();
		this.y = in.readInt();
		this.z = in.readInt();
		this.dimID = in.readInt();
	}
	
	@Override
	public void execute(T packet, EntityPlayer player, Side side) {
		
	}
}
