package letiu.pistronics.tiles;

import letiu.pistronics.util.BlockProxy;

public interface ITransmitter {

	public boolean isConductive();
	public void setStrength(int strength);
	public int getStrength();
	public boolean isActive();
	public void pulse(int ticks);
	public void setToInput();
	public void setToOutput();
	public boolean isInput();
	//public int getInputStrength(BlockProxy proxy);
	public void notifyOutputBlocks(BlockProxy proxy);
	public void checkNextInput();
	
	//public void setState(boolean state);
	
}
