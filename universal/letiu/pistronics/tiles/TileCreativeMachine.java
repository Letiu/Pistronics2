package letiu.pistronics.tiles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import letiu.pistronics.data.TileData;
import letiu.pistronics.util.CMRedstoneCommand;

public class TileCreativeMachine extends TileMech {

	private CMRedstoneCommand[] commands = new CMRedstoneCommand[16];

	public TileCreativeMachine() {
		commands[0] = new CMRedstoneCommand();
	}
	
	public void setCommands(CMRedstoneCommand[] commands) {
		this.commands = commands;
	}
	
	public CMRedstoneCommand[] getCommands() {
		return commands;
	}
	
	/** if rsStrengthControl == true returns defaultCommand */
	public CMRedstoneCommand getCommand(int strength) {
		if (commands[strength] == null && commands[0] != null) return commands[0];
		else return commands[strength];
	}
	
	@Override
	public String getKey() {
		return TileData.key_creativeMachine;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		for (int i = 0; i < 15; i++) {
			if (commands[i] != null) tagCompound.setTag("command" + i, commands[i].getNBT());
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		for (int i = 0; i < 15; i++) {
			if (tagCompound.hasKey("command" + i)) {
				commands[i] = new CMRedstoneCommand();
				commands[i].readFromNBT((NBTTagCompound) tagCompound.getTag("command" + i));
			}
			else commands[i] = null;
		}
	}
}



