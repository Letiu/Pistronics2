package letiu.modbase.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import letiu.modbase.blocks.SidedBlock;
import letiu.modbase.render.FakeWorld;

public interface IProxy {

	public void init();
	public void registerRenderers();
	public void registerTileEntities();
	public void registerEvents();
	public void registerNames();
	public FakeWorld getFakeWorld(World world);
	public FakeWorld getLogicFakeWorld(World world);
	public SidedBlock getSidedBlock();
	
	public EntityPlayer getPlayer();
}
