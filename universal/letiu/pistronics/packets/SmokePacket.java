package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BGear;
import letiu.pistronics.tiles.TileGear;
import letiu.pistronics.util.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class SmokePacket extends LocationPacket<SmokePacket> {
	
	boolean craft;
	
	public SmokePacket() {
		
	}
	
	public SmokePacket(int x, int y, int z, int dimID) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
		this.craft = false;
	}
	
	public SmokePacket(int x, int y, int z, int dimID, boolean craft) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
		this.craft = craft;
	}
	
	public SmokePacket(Vector3 coords, int dimID) {
		this.x = coords.x;
		this.y = coords.y;
		this.z = coords.z;
		this.dimID = dimID;
		this.craft = false;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeBoolean(craft);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		this.craft = in.readBoolean();
	}
	
	@Override
	public void execute(SmokePacket packet, EntityPlayer player, Side side) {
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			if (packet.craft) {
				if (WorldUtil.getPBlock(player.worldObj, packet.x, packet.y, packet.z) instanceof BGear) {
					int meta = WorldUtil.getBlockMeta(player.worldObj, packet.x, packet.y, packet.z);
					int gearSize = ((TileGear) WorldUtil.getPTile(player.worldObj, packet.x, packet.y, packet.z)).getSize();
					
					Vector3 pos = new Vector3(packet.x, packet.y, packet.z);
					Vector3 offsetA = null, offsetB = null;
					
					if (meta == 0 || meta == 1) {
						pos.x -= (gearSize / 2);
						pos.z -= (gearSize / 2);
						offsetA = Vector3.X_AXIS;
						offsetB = Vector3.Z_AXIS;
					}
					else if (meta == 2 || meta == 3) {
						pos.x -= (gearSize / 2);
						pos.y -= (gearSize / 2);
						offsetA = Vector3.X_AXIS;
						offsetB = Vector3.Y_AXIS;
					}
					else if (meta == 4 || meta == 5) {
						pos.y -= (gearSize / 2);
						pos.z -= (gearSize / 2);
						offsetA = Vector3.Y_AXIS;
						offsetB = Vector3.Z_AXIS;
					}
					
					for (int a = 0; a < gearSize; a++) {
						for (int b = 0; b < gearSize; b++) {
							WorldUtil.spawnCraftSmokeAt(player.worldObj, pos.x + offsetA.x * a + offsetB.x * b,
																		 pos.y + offsetA.y * a + offsetB.y * b,
																		 pos.z + offsetA.z * a + offsetB.z * b);
						}
					}
				}
				else WorldUtil.spawnCraftSmokeAt(player.worldObj, packet.x, packet.y, packet.z);
			}
			else WorldUtil.spawnSmokeAt(player.worldObj, packet.x, packet.y, packet.z);
		}
	}
	
}
