package letiu.modbase.proxies;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayerFactory;
import letiu.modbase.render.FakeWorld;
import letiu.modbase.tiles.BaseTile;
import letiu.modbase.tiles.InventoryTile;
import letiu.modbase.tiles.SRInventoryTile;
import letiu.modbase.tiles.SpecialRenderTile;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy {

	protected FakeWorld fakeWorld, logicFakeWorld;
	protected EntityPlayerMP fakePlayer;
	
	@Override
	public EntityPlayerMP getFakePlayerMP() {
		if (fakePlayer == null) {
			World world = DimensionManager.getProvider(0).worldObj;
			fakePlayer = FakePlayerFactory.get((WorldServer) world, new GameProfile(new UUID(1, 2), "packetFakePlayer"));
		}
		return fakePlayer;
	}
	
	@Override
	public FakeWorld getFakeWorld(World realWorld) {
		if (fakeWorld == null) {
			fakeWorld = new FakeWorld(realWorld);
		}
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
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(BaseTile.class, "tlBaseTile");
		GameRegistry.registerTileEntity(SpecialRenderTile.class, "tlSpecialRenderTile");
		GameRegistry.registerTileEntity(InventoryTile.class, "tlInventoryTile");
		GameRegistry.registerTileEntity(SRInventoryTile.class, "tlSRInventoryTile");
	}
	
	@Override
	public void registerRecipes() {
		
	}
}
