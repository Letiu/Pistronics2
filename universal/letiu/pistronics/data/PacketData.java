package letiu.pistronics.data;

import letiu.modbase.network.PacketHandler;
import letiu.pistronics.packets.*;

public class PacketData {

	public static void init() {
		
			PacketHandler.registerPacket(new SawPacket());
			PacketHandler.registerPacket(new RenderUpdatePacket());
			PacketHandler.registerPacket(new PartFusePacket());
			PacketHandler.registerPacket(new MotionPacket());
			PacketHandler.registerPacket(new SmokePacket());
			PacketHandler.registerPacket(new RedstoneInputPacket());
			PacketHandler.registerPacket(new PulsePacket());
			PacketHandler.registerPacket(new SubHitClickPacket());
			PacketHandler.registerPacket(new RodExDataPacket());
			PacketHandler.registerPacket(new CreativeMachinePacket());
			PacketHandler.registerPacket(new GearDataPacket());
            PacketHandler.registerPacket(new StatueDataPacket());
	}
}
