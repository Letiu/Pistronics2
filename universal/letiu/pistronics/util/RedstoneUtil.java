package letiu.pistronics.util;

import net.minecraft.world.World;
import letiu.modbase.network.PacketHandler;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.packets.PulsePacket;
import letiu.pistronics.piston.IPistonElement;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.tiles.ITransmitter;
import letiu.pistronics.tiles.TileCreativeMachine;
import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TileMechPiston;
import letiu.pistronics.tiles.TileMechRotator;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRodFolder;

public class RedstoneUtil {

	public static byte getNeighborStates(BlockProxy proxy) {
		byte result = 0;
		
		
		PBlock pBlock = proxy.getPBlock();
		if (pBlock != null && pBlock instanceof IPistonElement) {
		
			IPistonElement element = (IPistonElement) proxy.getPBlock();
			
			for (int i = 0; i < 6; i++) {
				if (element.connectsToSide(proxy, i, SystemType.REDSTONE)) {
					BlockProxy neighbor = proxy.getNeighbor(i);
					PBlock block = neighbor.getPBlock();
					if (block != null && block instanceof IPistonElement) {
						if (((IPistonElement) block).connectsToSide(neighbor, i ^ 1, SystemType.REDSTONE)) {
							result |= 1;
						}
					}
				}
				result <<= 1;
			}
		}
		
		return result;
	}
	
	public static void connectAdjacentExtensions(PistonSystem system, BlockProxy proxy) {
		
		PTile tile = proxy.getPTile();
		
		// Don't transmit signals "through" pistons/rotators/creativeMachine //
		if (tile instanceof TileMechPiston || tile instanceof TileMechRotator || tile instanceof TileCreativeMachine) {
			return;
		}
		
		if (tile != null && tile instanceof ITransmitter && ((ITransmitter) tile).isConductive()) {
			for (int i = 0; i < 6; i++) {
				
				BlockProxy neighbor = proxy.getNeighbor(i);
				PTile neighTile = neighbor.getPTile();
				
				if (neighTile instanceof TileMechPiston || neighTile instanceof TileMechRotator) {
					continue;
				}
				else if (neighTile instanceof TileRodFolder && neighbor.getFacing() != (i ^ 1)) {
					continue;
				}
				else if (neighTile instanceof TileElementHolder) {
					neighTile = ((TileElementHolder) neighTile).getPElementTile();
				}
				else if (neighTile instanceof TilePartblock) {
					neighTile = ((TilePartblock) neighbor.getPTile()).getTile(i ^ 1);
				}
				// continue if neighbor is normal extension and is not facing the right direction.
				else if (neighbor.getFacing() != (i ^ 1)) {
					continue; 
				}
				
				if (neighTile != null && neighTile instanceof TileExtension) {
					if (((TileExtension) neighTile).isConductive()) {						
						neighbor.getConnectedForSystem(system, false);
					}
				}
			}
		}
	}
	
	public static void pulseBlock(World world, int x, int y, int z, int dimID, int ticks) {
		BlockProxy proxy = new BlockProxy(world, x, y, z);
		PistonSystem system = new PistonSystem(proxy, 0, 0, SystemType.REDSTONE);
		system.pulse(ticks);
		PacketHandler.sendToAllInDimension(new PulsePacket(x, y, z, dimID, ticks), dimID);
	}
}
