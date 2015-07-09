package letiu.modbase.network;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.modbase.core.ModClass;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class CustomPacket<T extends CustomPacket<T>> implements IMessage, IMessageHandler<T, IMessage> {
	
	@Override
	public final void toBytes(ByteBuf buf) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
    	DataOutputStream data = new DataOutputStream(out);
    	
    	try {
			write(data);
		} 
    	catch (IOException e) {
			e.printStackTrace();
		}
    	
		buf.writeBytes(out.toByteArray());
	}
	
	@Override
	public final void fromBytes(ByteBuf buf) {
		
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    	DataInputStream data = new DataInputStream(in);
 
    	try {
			read(data);
		} 
    	catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public final IMessage onMessage(CustomPacket message, MessageContext ctx) {
		if (ctx.side == Side.CLIENT) {
			execute((T) message, ModClass.proxy.getPlayer(), Side.CLIENT);
		}
		else if (ctx.side == Side.SERVER) {
			execute((T) message, ctx.getServerHandler().playerEntity, Side.SERVER);
		}
		
		return null;
	}
	
	/** save packet data here */
	public void write(DataOutputStream out) throws IOException {
		
	}
	
	/** load packet data here */
	public void read(DataInputStream in) throws IOException {
		
	}

	/** apply packet here. IMPORTANT don't use class variables use the provided packet. */
	public void execute(T packet, EntityPlayer player, Side side) {
		
	}
	
}
