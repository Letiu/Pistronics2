package letiu.modbase.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import letiu.pistronics.reference.ModInformation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler implements IPacketHandler {

	private static TreeMap<Byte, CustomPacket> mapIDToPacket = new TreeMap<Byte, CustomPacket>();
	private static HashMap<Class<? extends CustomPacket>, Byte> mapPacketToID = new HashMap<Class<? extends CustomPacket>, Byte>();
	
	private static byte counter = 0;
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		try {
			
            EntityPlayer entityPlayer = (EntityPlayer) player;
            
            ByteArrayInputStream in = new ByteArrayInputStream(packet.data);
        	DataInputStream data = new DataInputStream(in);
        	
            int packetID = data.readUnsignedByte(); // Assuming your packetId is between 0 (inclusive) and 256 (exclusive). If you need more you need to change this
            
            CustomPacket customPacket = mapIDToPacket.get((byte) packetID);
            customPacket.read(data);
            customPacket.execute(customPacket, entityPlayer, entityPlayer.worldObj.isRemote ? Side.CLIENT : Side.SERVER);
	    } 
//		catch (ProtocolException e) {
//	        if (player instanceof EntityPlayerMP) {
//                ((EntityPlayerMP) player).playerNetServerHandler.kickPlayerFromServer("Protocol Exception!");
//                Logger.getLogger("Pistronics").warning("Player " + ((EntityPlayer)player).username + " caused a Protocol Exception!");
//	        }
//	    }
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void registerPacket(CustomPacket packet) {
		mapIDToPacket.put(counter, packet);
		mapPacketToID.put(packet.getClass(), counter);
		counter++;
	}
	
	private static Packet makePacket(CustomPacket packet) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(out);
		
	    try {
			data.writeByte(mapPacketToID.get(packet.getClass()));
			packet.write(data);
		} 
	    catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return PacketDispatcher.getPacket(ModInformation.CHANNEL, out.toByteArray());
	}
	
	public static void sendToAllInDimension(CustomPacket packet, int dimID) {
		PacketDispatcher.sendPacketToAllInDimension(makePacket(packet), dimID);
	}
	
	public static void sendToServer(CustomPacket packet) {
		PacketDispatcher.sendPacketToServer(makePacket(packet));
	}

}
