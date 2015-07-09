package letiu.modbase.proxies;

import net.minecraft.world.World;
import letiu.modbase.render.FakeWorld;
import letiu.modbase.tiles.BaseTile;
import letiu.modbase.tiles.InventoryTile;
import letiu.modbase.tiles.SRInventoryTile;
import letiu.modbase.tiles.SpecialRenderTile;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy {

	protected FakeWorld fakeWorld, logicFakeWorld;
	
	@Override
	public FakeWorld getFakeWorld(World realWorld) {
		if (fakeWorld == null) fakeWorld = new FakeWorld(realWorld);
		else fakeWorld.setRealWorld(realWorld);
		return fakeWorld;
	}
	
	@Override
	public FakeWorld getLogicFakeWorld(World realWorld) {
		if (logicFakeWorld == null) {
			logicFakeWorld = new FakeWorld(realWorld);
		}
		else logicFakeWorld.setRealWorld(realWorld);
		return logicFakeWorld;
	}
	
	@Override
	public void registerRenderers() {}
	
	@Override
	public void registerNames() {}
	
	@Override
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(BaseTile.class, "tlBaseTile");
		GameRegistry.registerTileEntity(SpecialRenderTile.class, "tlSpecialRenderTile");
		GameRegistry.registerTileEntity(InventoryTile.class, "tlInventoryTile");
		GameRegistry.registerTileEntity(SRInventoryTile.class, "tlSRInventoryTile");
	}
}
