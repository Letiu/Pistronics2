package letiu.modbase.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import letiu.modbase.core.ModClass;
import letiu.pistronics.packets.LocationPacket;
import letiu.pistronics.reference.ModInformation;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

	private static SimpleNetworkWrapper wrapper = new SimpleNetworkWrapper(ModInformation.CHANNEL);
	//private static ArrayList<CustomPacket> packets = new ArrayList<CustomPacket>();
	
	private static int counter = 0;
	
	
	public static void registerPacket(CustomPacket packet) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			wrapper.registerMessage((Class<? extends IMessageHandler<CustomPacket, IMessage>>) packet.getClass(), (Class<CustomPacket>) packet.getClass(), counter++, Side.CLIENT);
		}
		else {
			wrapper.registerMessage((Class<? extends IMessageHandler<CustomPacket, IMessage>>) packet.getClass(), (Class<CustomPacket>) packet.getClass(), counter++, Side.SERVER);
		}
	}
	
	
	public static void sendToAllInDimension(CustomPacket packet, int dimID) {
		wrapper.sendToDimension(packet, dimID);
	}
	
	public static void sendToServer(CustomPacket packet) {
		
		if (Minecraft.getMinecraft().isSingleplayer()) {
			if (packet instanceof LocationPacket) {
				LocationPacket packetL = (LocationPacket) packet;
				World world;
				
				EntityPlayerMP player = ModClass.proxy.getFakePlayerMP();
				player.dimension = packetL.getDimension();
				player.worldObj = DimensionManager.getProvider(packetL.getDimension()).worldObj;
			
				packet.execute(packet, player, Side.SERVER);
				return;
			}
		}
		
		wrapper.sendToServer(packet);
	}

}
