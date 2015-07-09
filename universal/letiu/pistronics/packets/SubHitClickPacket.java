package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.tiles.TilePartblock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;

public class SubHitClickPacket extends LocationPacket<SubHitClickPacket> {

	private int part, side;
	private String playerName;
	private boolean blockClick;

	public SubHitClickPacket() {
		
	}
	
	public SubHitClickPacket(int x, int y, int z, int part, int side, EntityPlayer player) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = player.dimension;
		this.part = part;
		this.side = side;
		this.playerName = player.getCommandSenderName();
		this.blockClick = false;
	}
	
	public SubHitClickPacket(int x, int y, int z, int part, int side, EntityPlayer player, boolean blockClick) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = player.dimension;
		this.part = part;
		this.side = side;
		this.playerName = player.getCommandSenderName();
		this.blockClick = true;
	} 
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeByte(part);
		out.writeByte(side);
		out.writeUTF(playerName);
		out.writeBoolean(blockClick);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		this.part = in.readByte();
		this.side = in.readByte();
		this.playerName = in.readUTF();
		this.blockClick = in.readBoolean();
	}
	
	@Override
	public void execute(SubHitClickPacket packet, EntityPlayer player, Side side) {
		
		this.x = packet.x;
		this.y = packet.y;
		this.z = packet.z;
		World world = player.worldObj;
		EntityPlayer clickingPlayer = world.getPlayerEntityByName(packet.playerName);
				
		if (side == Side.SERVER) {
			PTile pTile = WorldUtil.getPTile(world, x, y, z);
			if (pTile != null && pTile instanceof TilePartblock) {
				TilePartblock tile = (TilePartblock) pTile;
				PBlock part = tile.getPart(packet.part);
				if (part != null && part instanceof IPart) {
					((IPart) part).onPartActivated(world, x, y, z, clickingPlayer, packet.part, packet.side);
					tile.checkIntegrity(packet.dimID);
					PTile partTile = tile.getTile(packet.part);
					if (partTile != null) {						
						PacketHandler.sendToAllInDimension(new RodExDataPacket(partTile, packet.part, packet.dimID), packet.dimID);
					}
				}
			}
		}
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			PTile pTile = WorldUtil.getPTile(player.worldObj, packet.x, packet.y, packet.z);
			if (pTile != null && pTile instanceof TilePartblock) {
				if (packet.blockClick) {
					BlockData.partBlock.onBlockActivated(player.worldObj, x, y, z, clickingPlayer, packet.side, 0, 0, 0);
					WorldUtil.updateBlock(world, x, y, z);
				}
				else {
					TilePartblock tile = (TilePartblock) pTile;
					PBlock part = tile.getPart(packet.part);
					if (part != null && part instanceof IPart) {
						((IPart) part).onPartActivated(world, x, y, z, clickingPlayer, packet.part, packet.side);
						WorldUtil.updateBlock(world, x, y, z);
					}
				}
			}
		}
	}
}
