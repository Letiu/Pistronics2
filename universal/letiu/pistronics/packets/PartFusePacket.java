package letiu.pistronics.packets;

import letiu.pistronics.blocks.BPartblock;
import letiu.pistronics.data.BlockData;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class PartFusePacket extends LocationPacket<PartFusePacket> {

	public PartFusePacket() {
		
	}
	
	public PartFusePacket(int x, int y, int z, int dimID) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
	}
	
	@Override
	public void execute(PartFusePacket packet, EntityPlayer player, Side side) {
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			((BPartblock) BlockData.partBlock).tryFuse(player.worldObj, x, y, z);
		}
	}
	
}
