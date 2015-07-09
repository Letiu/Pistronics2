package letiu.modbase.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public abstract class CustomPacket<T extends CustomPacket> {

    public abstract void write(DataOutputStream out) throws IOException;
   
    public abstract void read(DataInputStream in) throws IOException;
   
    public abstract void execute(T packet, EntityPlayer player, Side side);
}