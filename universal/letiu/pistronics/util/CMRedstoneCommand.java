package letiu.pistronics.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.pistronics.tiles.TileCreativeMachine;
import net.minecraft.nbt.NBTTagCompound;


public class CMRedstoneCommand {
	
	public static final int MOVE_MODE = 1;
	public static final int ROTATE_MODE = 2;
	
	public static final int DEFAULT_SPEED = 4;
	public static final int DEFAULT_MODE = MOVE_MODE;
	public static final boolean DEFAULT_DIR = true;
	
	public int speed = 4;
	public int mode = MOVE_MODE;
	public boolean direction = true;
	
	public float getMoveSpeed() {
		return (speed + 1) * 0.01F;
	}
	
	public float getRotateSpeed() {
		return (speed + 1) * 0.4F;
	}
	
	public NBTTagCompound getNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("speed", speed);
		nbt.setInteger("mode", mode);
		nbt.setBoolean("direction", direction);
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.speed = nbt.getInteger("speed");
		this.mode = nbt.getInteger("mode");
		this.direction = nbt.getBoolean("direction");
	}
	
	public void writeToStream(DataOutputStream out) throws IOException {
		out.writeByte(speed);
		out.writeByte(mode);
		out.writeBoolean(direction);
	}
	
	public void readFromStream(DataInputStream in) throws IOException {
		this.speed = in.readByte();
		this.mode = in.readByte();
		this.direction = in.readBoolean();
	}
}