package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.tiles.TilePartblock;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class SawPacket extends LocationPacket<SawPacket> {

	private int part;
	private boolean isCreative;

	public SawPacket() {
		
	}
	
	public SawPacket(int x, int y, int z, int part, EntityPlayer player) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = player.dimension;
		this.part = part;
		this.isCreative = player.capabilities.isCreativeMode;
	}
	
	/** Used for Packets Sever -> Client */
	public SawPacket(int x, int y, int z, int part, int dimID) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
		this.part = part;
		this.isCreative = false;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeByte(part);
		out.writeBoolean(isCreative);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		this.part = in.readByte();
		this.isCreative = in.readBoolean();
	}
	
	@Override
	public void execute(SawPacket packet, EntityPlayer player, Side side) {
		if (side == Side.SERVER) {
			PTile pTile = WorldUtil.getPTile(player.worldObj, packet.x, packet.y, packet.z);
			if (pTile != null && pTile instanceof TilePartblock) {
				PBlock part = ((TilePartblock) pTile).getPart(packet.part);
				if (part != null) {
					if (!packet.isCreative) {
						WorldUtil.spawnItemStack(player.worldObj, packet.x, packet.y, packet.z, part.getDroppedStack(((TilePartblock) pTile).getTile(packet.part), 0));
					}
					((TilePartblock) pTile).setPart(null, packet.part);
					((TilePartblock) pTile).checkIntegrity(packet.dimID);
					PacketHandler.sendToAllInDimension(new SawPacket(packet.x, packet.y, packet.z, packet.part, packet.dimID), packet.dimID);
				}
			}
		}
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			PTile pTile = WorldUtil.getPTile(player.worldObj, packet.x, packet.y, packet.z);
			if (pTile != null && pTile instanceof TilePartblock) {
				((TilePartblock) pTile).setPart(null, packet.part);
				WorldUtil.updateBlock(player.worldObj, packet.x, packet.y, packet.z);
			}
		}
	}
}
