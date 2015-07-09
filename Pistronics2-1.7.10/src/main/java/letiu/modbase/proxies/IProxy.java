package letiu.modbase.proxies;

import letiu.modbase.blocks.SidedBlock;
import letiu.modbase.render.FakeWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public interface IProxy {

	public void init();
	public void registerRenderers();
	public void registerTileEntities();
	public void registerEvents();
	public void registerRecipes();
	public FakeWorld getFakeWorld(World realWorld);
	public FakeWorld getLogicFakeWorld(World realWorld);
	public EntityPlayerMP getFakePlayerMP();
	public SidedBlock getSidedBlock();
	
	public EntityPlayer getPlayer();
}
