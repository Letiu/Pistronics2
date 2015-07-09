package letiu.pistronics.packets;

import letiu.modbase.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class RenderUpdatePacket extends LocationPacket<RenderUpdatePacket> {

	public RenderUpdatePacket() {
		
	}
	
	public RenderUpdatePacket(int x, int y, int z, int dimID) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
	}
	
	@Override
	public void execute(RenderUpdatePacket packet, EntityPlayer player, Side side) {
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			WorldUtil.updateBlock(player.worldObj, packet.x, packet.y, packet.z);
		}
	}
}
