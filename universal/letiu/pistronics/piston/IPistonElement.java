package letiu.pistronics.piston;

import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.util.BlockProxy;

public interface IPistonElement {

	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, boolean strongConnection);
	public boolean connectsToSide(BlockProxy proxy, int side, SystemType type);
	public boolean isTransmitter(BlockProxy proxy);
	
}
