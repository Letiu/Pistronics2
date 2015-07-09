package letiu.modbase.proxies;

import net.minecraft.entity.player.EntityPlayer;
import letiu.modbase.blocks.SidedBlock;

public class ServerProxy extends CommonProxy {
	
	public void init() {
		
	}
	
	@Override
	public EntityPlayer getPlayer() {
		return null;
	}

	@Override
	public void registerEvents() {
		
	}

	@Override
	public SidedBlock getSidedBlock() {
		return null;
	}
}
