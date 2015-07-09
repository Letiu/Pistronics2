package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.modbase.util.WorldUtil;
import letiu.pistronics.tiles.TileCreativeMachine;
import letiu.pistronics.util.CMRedstoneCommand;
import net.minecraft.entity.player.EntityPlayer;
import letiu.pistronics.data.PTile;
import cpw.mods.fml.relauncher.Side;

public class CreativeMachinePacket extends LocationPacket<CreativeMachinePacket> {

	private CMRedstoneCommand[] commands;
	
	public CreativeMachinePacket() {
		
	}
	
	public CreativeMachinePacket(TileCreativeMachine tile, int dimID) {
		this.x = tile.tileEntity.xCoord;
		this.y = tile.tileEntity.yCoord;
		this.z = tile.tileEntity.zCoord;
		this.dimID = dimID;
		
		this.commands = tile.getCommands();
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		
		for (int i = 0; i < 16; i++) {
			if (commands[i] != null) {
				out.writeBoolean(true);
				commands[i].writeToStream(out);
			}
			else out.writeBoolean(false);
		}
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		
		this.commands = new CMRedstoneCommand[16];
		
		for (int i = 0; i < 16; i++) {
			if (in.readBoolean()) {
				commands[i] = new CMRedstoneCommand();
				commands[i].readFromStream(in);
			}
		}
	}
	
	@Override
	public void execute(CreativeMachinePacket packet, EntityPlayer player, Side side) {
		if (Side.SERVER == side) {
			PTile tile = WorldUtil.getPTile(player.worldObj, packet.x, packet.y, packet.z);
			if (tile != null && tile instanceof TileCreativeMachine) {
				((TileCreativeMachine) tile).setCommands(packet.commands);
			}
		}
	}
	
}
