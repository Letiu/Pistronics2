package letiu.modbase.render;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import letiu.pistronics.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.logging.ILogAgent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeDirection;

public class FakeWorld extends World {
	
	private ArrayList<TileEntity> tileEntities;
	private List<FakeBlockData> data = Collections.synchronizedList(new ArrayList<FakeBlockData>());
	private World realWorld;
	
	private Vector3 solo = null;
	
	public FakeWorld(World realWorld) {
		super(realWorld.getSaveHandler(), "FakeWorld", new WorldSettings(realWorld.getWorldInfo()), null, null, null);
		this.realWorld = realWorld;
		this.tileEntities = new ArrayList<TileEntity>();
	}
	
	public void setRealWorld(World world) {
		this.realWorld = world;
	}

	public void addFakeTl(TileEntity tl) {
		if (tl != null) {
			tileEntities.add(tl);
		}
	}
	
	public void setSolo(Vector3 solo) {
		this.solo = solo;
	}
	
	public void addFakeTl(TileEntity tile, Vector3 coords) {
		if (tile != null) {
			
//			if (tile instanceof TileEntityWrapper) {
//				try {
//					 int i = 5 / 0;
//				}
//				catch (ArithmeticException e) {
//					e.printStackTrace();
//				}
//			}
			
//			System.out.println("Adding tl with coords: " + coords.x + "/" + coords.y + "/" + coords.z);
			/*or (int i = 0; i < tileEntities.size(); i++) {
				TileEntity tl = tileEntities.get(i);
				if (tl.xCoord == coords.x && tl.yCoord == coords.y && tl.zCoord == coords.z) {
					tileEntities.remove(i);
					i--;
				}
			}*/
			tile.xCoord = coords.x;
			tile.yCoord = coords.y;
			tile.zCoord = coords.z;
			tileEntities.add(tile);
//			System.out.println("Side: " + tile.worldObj.isRemote);
//			System.out.println("Added FakeTl " + tile);
//			System.out.println(coords);
//			System.out.println("Added Fake TL");
//			System.out.println("CacheTL coords: " + tileEntities.get(0).xCoord + "/" + tileEntities.get(0).yCoord + "/" + tileEntities.get(0).zCoord + "/");
		}
	}
	
	public void addFakeBlockData(FakeBlockData blockData) {
		if (blockData != null) {
			synchronized (data) {
				for (int i = 0; i < data.size(); i++) {
					FakeBlockData block = data.get(i);
					if (block == null) return;
					if (block.x == blockData.x && block.y == blockData.y && block.z == blockData.z) {
						data.remove(i);
						i--;
					}
				}
				this.data.add(blockData);
			}
		}
	}
	
	public void clearFakeDataAt(int x, int y, int z) {
//		System.out.println("clearing fakedata");
		synchronized (data) {
			for (int i = 0; i < tileEntities.size(); i++) {
				TileEntity tl = tileEntities.get(i);
				if (tl.xCoord == x && tl.yCoord == y && tl.zCoord == z) {
					
						tileEntities.remove(i);
					
					i--;
				}
			}
			for (int i = 0; i < data.size(); i++) {
				FakeBlockData block = data.get(i);
				if (block.x == x && block.y == y && block.z == z) {
					data.remove(i);
					i--;
				}
			}
		}
	}
	
	public void clearFakeDataAt(Vector3 coords) {
		clearFakeDataAt(coords.x, coords.y, coords.z);
	}
	
	public void reset() {
		this.tileEntities.clear();
		this.data.clear();
		this.solo = null;
	}
	
	@Override
	public TileEntity getBlockTileEntity(int x, int y, int z) {
		
		if (solo != null && (solo.x != x || solo.y != y || solo.z != z)) {
			return null;
		}
		
//		System.out.println("LFTL TlChacheSize: " + tileEntities.size()) ;
//		System.out.println("CacheTL coords: " + tileEntities.get(0).xCoord + "/" + tileEntities.get(0).yCoord + "/" + tileEntities.get(0).zCoord + "/");
		//System.out.println("LF: " + x + "/" + y + "/" + z);
		for (TileEntity tl : tileEntities) {
			if (tl.xCoord == x && tl.yCoord == y && tl.zCoord == z) {
				//System.out.println("retrieved tl");
				return tl;
			}
		}
		//.out.println("FAIL");
		return null; //realWorld.getBlockTileEntity(x, y, z);
	}
	
	@Override
	public int getBlockMetadata(int x, int y, int z) {
		return realWorld.getBlockMetadata(x, y, z);
	}
	
	@Override
	public float getBrightness(int par1, int par2, int par3, int par4) {
		return realWorld.getBrightness(par1, par2, par3, par4);
	}
	
	@Override
	public int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4) {
		return realWorld.getLightBrightnessForSkyBlocks(par1, par2, par3, par4);
	}
	
	@Override
	public int getBlockId(int x, int y, int z) {
		
		if (solo != null && (solo.x != x || solo.y != y || solo.z != z)) {
			return 0;
		}
		
		synchronized (data) {
			for (FakeBlockData blockdata : data) {
				if (blockdata != null && blockdata.x == x && blockdata.y == y && blockdata.z == z) {
					//System.out.println("retrived fake id");
					return blockdata.id;
				}
			}
		}
		
		//System.out.println("returning real id");
		return realWorld.getBlockId(x, y, z);
	}
	
	////////////////////
	
	@Override
	public void addBlockEvent(int par1, int par2, int par3, int par4, int par5,
			int par6) {
		// TODO Auto-generated method stub
		realWorld.addBlockEvent(par1, par2, par3, par4, par5, par6);
	}
	
	@Override
	public void addLoadedEntities(List par1List) {
		// TODO Auto-generated method stub
		realWorld.addLoadedEntities(par1List);
	}
	
	@Override
	public void addTileEntity(Collection par1Collection) {
		// TODO Auto-generated method stub
		realWorld.addTileEntity(par1Collection);
	}
	
	@Override
	public void addTileEntity(TileEntity entity) {
		// TODO Auto-generated method stub
		realWorld.addTileEntity(entity);
	}
	
	@Override
	public boolean addWeatherEffect(Entity par1Entity) {
		// TODO Auto-generated method stub
		return realWorld.addWeatherEffect(par1Entity);
	}
	
	@Override
	public void addWorldAccess(IWorldAccess par1iWorldAccess) {
		// TODO Auto-generated method stub
		realWorld.addWorldAccess(par1iWorldAccess);
	}
	
	@Override
	public CrashReportCategory addWorldInfoToCrashReport(
			CrashReport par1CrashReport) {
		// TODO Auto-generated method stub
		return realWorld.addWorldInfoToCrashReport(par1CrashReport);
	}
	
	@Override
	public boolean blockExists(int par1, int par2, int par3) {
		return realWorld.blockExists(par1, par2, par3);
	}
	
	@Override
	public int blockGetRenderType(int par1, int par2, int par3) {
		return realWorld.blockGetRenderType(par1, par2, par3);
	}
	
	@Override
	public boolean blockHasTileEntity(int par1, int par2, int par3) {
		return realWorld.blockHasTileEntity(par1, par2, par3);
	}
	
	@Override
	public boolean canBlockFreeze(int par1, int par2, int par3, boolean par4) {
		return realWorld.canBlockFreeze(par1, par2, par3, par4);
	}
	
	@Override
	public boolean canBlockFreezeBody(int par1, int par2, int par3, boolean par4) {
		return realWorld.canBlockFreezeBody(par1, par2, par3, par4);
	}
	
	@Override
	public boolean canBlockSeeTheSky(int par1, int par2, int par3) {
		return realWorld.canBlockSeeTheSky(par1, par2, par3);
	}
	
	@Override
	public boolean canLightningStrikeAt(int par1, int par2, int par3) {
		return realWorld.canLightningStrikeAt(par1, par2, par3);
	}
	
	@Override
	public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
		return realWorld.canMineBlock(par1EntityPlayer, par2, par3, par4);
	}
	
	@Override
	public boolean canMineBlockBody(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
		return realWorld.canMineBlockBody(par1EntityPlayer, par2, par3, par4);
	}
	
	@Override
	public boolean canPlaceEntityOnSide(int par1, int par2, int par3, int par4, boolean par5, int par6, Entity par7Entity, ItemStack par8ItemStack) {
		return realWorld.canPlaceEntityOnSide(par1, par2, par3, par4, par5, par6, par7Entity, par8ItemStack);
	}
	
	@Override
	public boolean canSnowAt(int par1, int par2, int par3) {
		return realWorld.canSnowAt(par1, par2, par3);
	}
	
	@Override
	public boolean canSnowAtBody(int par1, int par2, int par3) {
		return realWorld.canSnowAtBody(par1, par2, par3);
	}
	
	@Override
	public boolean checkBlockCollision(AxisAlignedBB par1AxisAlignedBB) {
		return realWorld.checkBlockCollision(par1AxisAlignedBB);
	}
	
	@Override
	public boolean checkChunksExist(int par1, int par2, int par3, int par4, int par5, int par6) {
		return realWorld.checkChunksExist(par1, par2, par3, par4, par5, par6);
	}
	
	@Override
	public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB) {
		return realWorld.checkNoEntityCollision(par1AxisAlignedBB);
	}
	
	@Override
	public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB, Entity par2Entity) {
		return realWorld.checkNoEntityCollision(par1AxisAlignedBB, par2Entity);
	}
	
	@Override
	public void checkSessionLock() throws MinecraftException {
		realWorld.checkSessionLock();
	}
	
	@Override
	public MovingObjectPosition clip(Vec3 par1Vec3, Vec3 par2Vec3) {
		// TODO Auto-generated method stub
		return realWorld.clip(par1Vec3, par2Vec3);
	}
	
	@Override
	public MovingObjectPosition clip(Vec3 par1Vec3, Vec3 par2Vec3, boolean par3) {
		// TODO Auto-generated method stub
		return realWorld.clip(par1Vec3, par2Vec3, par3);
	}
	
	@Override
	public int countEntities(Class par1Class) {
		// TODO Auto-generated method stub
		return realWorld.countEntities(par1Class);
	}
	
	@Override
	public int countEntities(EnumCreatureType type, boolean forSpawnCount) {
		// TODO Auto-generated method stub
		return realWorld.countEntities(type, forSpawnCount);
	}
	
	@Override
	public Explosion createExplosion(Entity par1Entity, double par2,
			double par4, double par6, float par8, boolean par9) {
		// TODO Auto-generated method stub
		return realWorld.createExplosion(par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override
	public boolean destroyBlock(int par1, int par2, int par3, boolean par4) {
		// TODO Auto-generated method stub
		return realWorld.destroyBlock(par1, par2, par3, par4);
	}
	
	@Override
	public void destroyBlockInWorldPartially(int par1, int par2, int par3,
			int par4, int par5) {
		// TODO Auto-generated method stub
		realWorld.destroyBlockInWorldPartially(par1, par2, par3, par4, par5);
	}
	
	@Override
	public boolean doChunksNearChunkExist(int par1, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return realWorld.doChunksNearChunkExist(par1, par2, par3, par4);
	}
	
	@Override
	public boolean doesBlockHaveSolidTopSurface(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.doesBlockHaveSolidTopSurface(par1, par2, par3);
	}
	
	@Override
	public Vec3 drawCloudsBody(float par1) {
		// TODO Auto-generated method stub
		return realWorld.drawCloudsBody(par1);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return realWorld.equals(obj);
	}
	
	@Override
	public boolean extendedLevelsInChunkCache() {
		// TODO Auto-generated method stub
		return realWorld.extendedLevelsInChunkCache();
	}
	
	@Override
	public boolean extinguishFire(EntityPlayer par1EntityPlayer, int par2,
			int par3, int par4, int par5) {
		// TODO Auto-generated method stub
		return realWorld.extinguishFire(par1EntityPlayer, par2, par3, par4, par5);
	}
	
	@Override
	public ChunkPosition findClosestStructure(String par1Str, int par2,
			int par3, int par4) {
		// TODO Auto-generated method stub
		return realWorld.findClosestStructure(par1Str, par2, par3, par4);
	}
	
	@Override
	public Entity findNearestEntityWithinAABB(Class par1Class,
			AxisAlignedBB par2AxisAlignedBB, Entity par3Entity) {
		// TODO Auto-generated method stub
		return realWorld.findNearestEntityWithinAABB(par1Class, par2AxisAlignedBB,
				par3Entity);
	}
	
	@Override
	public void func_82738_a(long par1) {
		// TODO Auto-generated method stub
		realWorld.func_82738_a(par1);
	}
	
	@Override
	public void func_82739_e(int par1, int par2, int par3, int par4, int par5) {
		// TODO Auto-generated method stub
		realWorld.func_82739_e(par1, par2, par3, par4, par5);
	}
	
	@Override
	public void func_92088_a(double par1, double par3, double par5,
			double par7, double par9, double par11,
			NBTTagCompound par13nbtTagCompound) {
		// TODO Auto-generated method stub
		realWorld.func_92088_a(par1, par3, par5, par7, par9, par11, par13nbtTagCompound);
	}
	
	@Override
	public void func_96440_m(int par1, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		realWorld.func_96440_m(par1, par2, par3, par4);
	}
	
	@Override
	public int getActualHeight() {
		// TODO Auto-generated method stub
		return realWorld.getActualHeight();
	}
	
	@Override
	public BiomeGenBase getBiomeGenForCoords(int par1, int par2) {
		// TODO Auto-generated method stub
		return realWorld.getBiomeGenForCoords(par1, par2);
	}
	
	@Override
	public BiomeGenBase getBiomeGenForCoordsBody(int par1, int par2) {
		// TODO Auto-generated method stub
		return realWorld.getBiomeGenForCoordsBody(par1, par2);
	}
	
	@Override
	public float getBlockDensity(Vec3 par1Vec3, AxisAlignedBB par2AxisAlignedBB) {
		// TODO Auto-generated method stub
		return realWorld.getBlockDensity(par1Vec3, par2AxisAlignedBB);
	}
	
	@Override
	public int getBlockLightOpacity(int x, int y, int z) {
		// TODO Auto-generated method stub
		return realWorld.getBlockLightOpacity(x, y, z);
	}
	
	@Override
	public int getBlockLightValue(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.getBlockLightValue(par1, par2, par3);
	}
	
	@Override
	public int getBlockLightValue_do(int par1, int par2, int par3, boolean par4) {
		// TODO Auto-generated method stub
		return realWorld.getBlockLightValue_do(par1, par2, par3, par4);
	}
	
	@Override
	public Material getBlockMaterial(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.getBlockMaterial(par1, par2, par3);
	}
	
	@Override
	public int getBlockPowerInput(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.getBlockPowerInput(par1, par2, par3);
	}
	
	@Override
	public float getCelestialAngleRadians(float par1) {
		// TODO Auto-generated method stub
		return realWorld.getCelestialAngleRadians(par1);
	}
	
	@Override
	public Chunk getChunkFromBlockCoords(int par1, int par2) {
		// TODO Auto-generated method stub
		return realWorld.getChunkFromBlockCoords(par1, par2);
	}
	
	@Override
	public Chunk getChunkFromChunkCoords(int par1, int par2) {
		// TODO Auto-generated method stub
		return realWorld.getChunkFromChunkCoords(par1, par2);
	}
	
	@Override
	public int getChunkHeightMapMinimum(int par1, int par2) {
		// TODO Auto-generated method stub
		return realWorld.getChunkHeightMapMinimum(par1, par2);
	}
	
	@Override
	public IChunkProvider getChunkProvider() {
		// TODO Auto-generated method stub
		return realWorld.getChunkProvider();
	}
	
	@Override
	public EntityPlayer getClosestPlayer(double par1, double par3, double par5,
			double par7) {
		// TODO Auto-generated method stub
		return realWorld.getClosestPlayer(par1, par3, par5, par7);
	}
	
	@Override
	public EntityPlayer getClosestVulnerablePlayer(double par1, double par3,
			double par5, double par7) {
		// TODO Auto-generated method stub
		return realWorld.getClosestVulnerablePlayer(par1, par3, par5, par7);
	}
	
	@Override
	public EntityPlayer getClosestVulnerablePlayerToEntity(Entity par1Entity,
			double par2) {
		// TODO Auto-generated method stub
		return realWorld.getClosestVulnerablePlayerToEntity(par1Entity, par2);
	}
	
	@Override
	public Vec3 getCloudColour(float par1) {
		// TODO Auto-generated method stub
		return realWorld.getCloudColour(par1);
	}
	
	@Override
	public List getCollidingBlockBounds(AxisAlignedBB par1AxisAlignedBB) {
		// TODO Auto-generated method stub
		return realWorld.getCollidingBlockBounds(par1AxisAlignedBB);
	}
	
	@Override
	public List getCollidingBoundingBoxes(Entity par1Entity,
			AxisAlignedBB par2AxisAlignedBB) {
		// TODO Auto-generated method stub
		return realWorld.getCollidingBoundingBoxes(par1Entity, par2AxisAlignedBB);
	}
	
	@Override
	public Calendar getCurrentDate() {
		// TODO Auto-generated method stub
		return realWorld.getCurrentDate();
	}
	
	@Override
	public float getCurrentMoonPhaseFactor() {
		// TODO Auto-generated method stub
		return realWorld.getCurrentMoonPhaseFactor();
	}
	
	@Override
	public String getDebugLoadedEntities() {
		// TODO Auto-generated method stub
		return realWorld.getDebugLoadedEntities();
	}
	
	@Override
	public List getEntitiesWithinAABB(Class par1Class,
			AxisAlignedBB par2AxisAlignedBB) {
		// TODO Auto-generated method stub
		return realWorld.getEntitiesWithinAABB(par1Class, par2AxisAlignedBB);
	}
	
	@Override
	public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity,
			AxisAlignedBB par2AxisAlignedBB) {
		// TODO Auto-generated method stub
		return realWorld.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB);
	}
	
	@Override
	public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity,
			AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3iEntitySelector) {
		// TODO Auto-generated method stub
		return realWorld.getEntitiesWithinAABBExcludingEntity(par1Entity,
				par2AxisAlignedBB, par3iEntitySelector);
	}
	
	@Override
	public PathEntity getEntityPathToXYZ(Entity par1Entity, int par2, int par3,
			int par4, float par5, boolean par6, boolean par7, boolean par8,
			boolean par9) {
		// TODO Auto-generated method stub
		return realWorld.getEntityPathToXYZ(par1Entity, par2, par3, par4, par5, par6, par7,
				par8, par9);
	}
	
	@Override
	public int getFirstUncoveredBlock(int par1, int par2) {
		// TODO Auto-generated method stub
		return realWorld.getFirstUncoveredBlock(par1, par2);
	}
	
	@Override
	public Vec3 getFogColor(float par1) {
		// TODO Auto-generated method stub
		return realWorld.getFogColor(par1);
	}
	
	@Override
	public int getFullBlockLightValue(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.getFullBlockLightValue(par1, par2, par3);
	}
	
	@Override
	public GameRules getGameRules() {
		// TODO Auto-generated method stub
		return realWorld.getGameRules();
	}
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return realWorld.getHeight();
	}
	
	@Override
	public int getHeightValue(int par1, int par2) {
		// TODO Auto-generated method stub
		return realWorld.getHeightValue(par1, par2);
	}
	
	@Override
	public double getHorizon() {
		// TODO Auto-generated method stub
		return realWorld.getHorizon();
	}
	
	@Override
	public int getIndirectPowerLevelTo(int par1, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return realWorld.getIndirectPowerLevelTo(par1, par2, par3, par4);
	}
	
	@Override
	public boolean getIndirectPowerOutput(int par1, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return realWorld.getIndirectPowerOutput(par1, par2, par3, par4);
	}
	
	@Override
	public float getLightBrightness(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.getLightBrightness(par1, par2, par3);
	}
	
	@Override
	public List getLoadedEntityList() {
		// TODO Auto-generated method stub
		return realWorld.getLoadedEntityList();
	}
	
	@Override
	public float getLocationTensionFactor(double par1, double par3, double par5) {
		// TODO Auto-generated method stub
		return realWorld.getLocationTensionFactor(par1, par3, par5);
	}
	
	@Override
	public IUpdatePlayerListBox getMinecartSoundUpdater(
			EntityMinecart par1EntityMinecart) {
		// TODO Auto-generated method stub
		return realWorld.getMinecartSoundUpdater(par1EntityMinecart);
	}
	
	@Override
	public int getMoonPhase() {
		// TODO Auto-generated method stub
		return realWorld.getMoonPhase();
	}
	
	@Override
	public PathEntity getPathEntityToEntity(Entity par1Entity,
			Entity par2Entity, float par3, boolean par4, boolean par5,
			boolean par6, boolean par7) {
		// TODO Auto-generated method stub
		return realWorld.getPathEntityToEntity(par1Entity, par2Entity, par3, par4, par5,
				par6, par7);
	}
	
	@Override
	public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2) {
		// TODO Auto-generated method stub
		return realWorld.getPendingBlockUpdates(par1Chunk, par2);
	}
	
	@Override
	public EntityPlayer getPlayerEntityByName(String par1Str) {
		// TODO Auto-generated method stub
		return realWorld.getPlayerEntityByName(par1Str);
	}
	
	@Override
	public int getPrecipitationHeight(int par1, int par2) {
		// TODO Auto-generated method stub
		return realWorld.getPrecipitationHeight(par1, par2);
	}
	
	@Override
	public String getProviderName() {
		// TODO Auto-generated method stub
		return realWorld.getProviderName();
	}
	
	@Override
	public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2,
			int par3, int par4) {
		// TODO Auto-generated method stub
		return realWorld.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4);
	}
	
	@Override
	public ISaveHandler getSaveHandler() {
		// TODO Auto-generated method stub
		return realWorld.getSaveHandler();
	}
	
	@Override
	public Scoreboard getScoreboard() {
		// TODO Auto-generated method stub
		return realWorld.getScoreboard();
	}
	
	@Override
	public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock,
			int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return realWorld.getSkyBlockTypeBrightness(par1EnumSkyBlock, par2, par3, par4);
	}
	
	@Override
	public Vec3 getSkyColor(Entity par1Entity, float par2) {
		// TODO Auto-generated method stub
		return realWorld.getSkyColor(par1Entity, par2);
	}
	
	@Override
	public Vec3 getSkyColorBody(Entity par1Entity, float par2) {
		// TODO Auto-generated method stub
		return realWorld.getSkyColorBody(par1Entity, par2);
	}
	
	@Override
	public ChunkCoordinates getSpawnPoint() {
		// TODO Auto-generated method stub
		return realWorld.getSpawnPoint();
	}
	
	@Override
	public float getStarBrightness(float par1) {
		// TODO Auto-generated method stub
		return realWorld.getStarBrightness(par1);
	}
	
	@Override
	public float getStarBrightnessBody(float par1) {
		// TODO Auto-generated method stub
		return realWorld.getStarBrightnessBody(par1);
	}
	
	@Override
	public int getStrongestIndirectPower(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.getStrongestIndirectPower(par1, par2, par3);
	}
	
	@Override
	public float getSunBrightness(float par1) {
		// TODO Auto-generated method stub
		return realWorld.getSunBrightness(par1);
	}
	
	@Override
	public float getTensionFactorForBlock(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.getTensionFactorForBlock(par1, par2, par3);
	}
	
	@Override
	public int getTopSolidOrLiquidBlock(int par1, int par2) {
		// TODO Auto-generated method stub
		return realWorld.getTopSolidOrLiquidBlock(par1, par2);
	}
	
	@Override
	public long getTotalWorldTime() {
		// TODO Auto-generated method stub
		return realWorld.getTotalWorldTime();
	}
	
	@Override
	public int getUniqueDataId(String par1Str) {
		// TODO Auto-generated method stub
		return super.getUniqueDataId(par1Str);
	}
	
	@Override
	public WorldChunkManager getWorldChunkManager() {
		// TODO Auto-generated method stub
		return super.getWorldChunkManager();
	}
	
	public ILogAgent getWorldLogAgent() {
		return realWorld.getWorldLogAgent();
	};
	
	@Override
	public long getWorldTime() {
		// TODO Auto-generated method stub
		return realWorld.getWorldTime();
	}
	
	@Override
	public Vec3Pool getWorldVec3Pool() {
		// TODO Auto-generated method stub
		return realWorld.getWorldVec3Pool();
	}
	
	@Override
	public boolean handleMaterialAcceleration(AxisAlignedBB par1AxisAlignedBB,
			Material par2Material, Entity par3Entity) {
		// TODO Auto-generated method stub
		return realWorld.handleMaterialAcceleration(par1AxisAlignedBB, par2Material,
				par3Entity);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	@Override
	public boolean isAABBInMaterial(AxisAlignedBB par1AxisAlignedBB,
			Material par2Material) {
		// TODO Auto-generated method stub
		return super.isAABBInMaterial(par1AxisAlignedBB, par2Material);
	}
	
	@Override
	protected void initialize(WorldSettings par1WorldSettings) {
		// TODO Auto-generated method stub
		super.initialize(par1WorldSettings);
	}
	
	@Override
	public boolean isAirBlock(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.isAirBlock(par1, par2, par3);
	}
	
	@Override
	public boolean isAnyLiquid(AxisAlignedBB par1AxisAlignedBB) {
		// TODO Auto-generated method stub
		return realWorld.isAnyLiquid(par1AxisAlignedBB);
	}
	
	@Override
	public boolean isBlockFreezable(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.isBlockFreezable(par1, par2, par3);
	}
	
	@Override
	public boolean isBlockFreezableNaturally(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.isBlockFreezableNaturally(par1, par2, par3);
	}
	
	@Override
	public boolean isBlockFullCube(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.isBlockFullCube(par1, par2, par3);
	}
	
	@Override
	public boolean isBlockHighHumidity(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.isBlockHighHumidity(par1, par2, par3);
	}
	
	@Override
	public boolean isBlockIndirectlyGettingPowered(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.isBlockIndirectlyGettingPowered(par1, par2, par3);
	}
	
	@Override
	public boolean isBlockNormalCube(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.isBlockNormalCube(par1, par2, par3);
	}
	
	@Override
	public boolean isBlockNormalCubeDefault(int par1, int par2, int par3,
			boolean par4) {
		// TODO Auto-generated method stub
		return realWorld.isBlockNormalCubeDefault(par1, par2, par3, par4);
	}
	
	@Override
	public boolean isBlockOpaqueCube(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.isBlockOpaqueCube(par1, par2, par3);
	}
	
	@Override
	public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return realWorld.isBlockProvidingPowerTo(par1, par2, par3, par4);
	}
	
	@Override
	public boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side) {
		// TODO Auto-generated method stub
		return realWorld.isBlockSolidOnSide(x, y, z, side);
	}
	
	@Override
	public boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side,
			boolean _default) {
		// TODO Auto-generated method stub
		return realWorld.isBlockSolidOnSide(x, y, z, side, _default);
	}
	
	@Override
	public boolean isBlockTickScheduledThisTick(int par1, int par2, int par3,
			int par4) {
		// TODO Auto-generated method stub
		return realWorld.isBlockTickScheduledThisTick(par1, par2, par3, par4);
	}
	
	@Override
	public boolean isBlockTopFacingSurfaceSolid(Block par1Block, int par2) {
		// TODO Auto-generated method stub
		return realWorld.isBlockTopFacingSurfaceSolid(par1Block, par2);
	}
	
	@Override
	public boolean isBoundingBoxBurning(AxisAlignedBB par1AxisAlignedBB) {
		// TODO Auto-generated method stub
		return realWorld.isBoundingBoxBurning(par1AxisAlignedBB);
	}
	
	@Override
	public boolean isDaytime() {
		// TODO Auto-generated method stub
		return realWorld.isDaytime();
	}
	
	@Override
	public boolean isMaterialInBB(AxisAlignedBB par1AxisAlignedBB,
			Material par2Material) {
		// TODO Auto-generated method stub
		return realWorld.isMaterialInBB(par1AxisAlignedBB, par2Material);
	}
	
	@Override
	public boolean isRaining() {
		// TODO Auto-generated method stub
		return realWorld.isRaining();
	}
	
	@Override
	public boolean isThundering() {
		// TODO Auto-generated method stub
		return realWorld.isThundering();
	}
	
	@Override
	public void joinEntityInSurroundings(Entity par1Entity) {
		// TODO Auto-generated method stub
		realWorld.joinEntityInSurroundings(par1Entity);
	}
	
	@Override
	public WorldSavedData loadItemData(Class par1Class, String par2Str) {
		// TODO Auto-generated method stub
		return realWorld.loadItemData(par1Class, par2Str);
	}
	
	@Override
	public void markBlockForRenderUpdate(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		realWorld.markBlockForRenderUpdate(par1, par2, par3);
	}
	
	@Override
	public void markBlockForUpdate(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		realWorld.markBlockForUpdate(par1, par2, par3);
	}
	
	@Override
	public void markBlockRangeForRenderUpdate(int par1, int par2, int par3,
			int par4, int par5, int par6) {
		// TODO Auto-generated method stub
		realWorld.markBlockRangeForRenderUpdate(par1, par2, par3, par4, par5, par6);
	}
	
	@Override
	public void markBlocksDirtyVertical(int par1, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		realWorld.markBlocksDirtyVertical(par1, par2, par3, par4);
	}
	
	@Override
	public void markTileEntityChunkModified(int par1, int par2, int par3,
			TileEntity par4TileEntity) {
		// TODO Auto-generated method stub
		realWorld.markTileEntityChunkModified(par1, par2, par3, par4TileEntity);
	}
	
	@Override
	public void markTileEntityForDespawn(TileEntity par1TileEntity) {
		// TODO Auto-generated method stub
		realWorld.markTileEntityForDespawn(par1TileEntity);
	}
	
	@Override
	public Explosion newExplosion(Entity par1Entity, double par2, double par4,
			double par6, float par8, boolean par9, boolean par10) {
		// TODO Auto-generated method stub
		return realWorld.newExplosion(par1Entity, par2, par4, par6, par8, par9, par10);
	}
	
	@Override
	public void notifyBlockChange(int par1, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		realWorld.notifyBlockChange(par1, par2, par3, par4);
	}
	
	@Override
	public void notifyBlockOfNeighborChange(int par1, int par2, int par3,
			int par4) {
		// TODO Auto-generated method stub
		realWorld.notifyBlockOfNeighborChange(par1, par2, par3, par4);
	}
	
	@Override
	public void notifyBlocksOfNeighborChange(int par1, int par2, int par3,
			int par4) {
		// TODO Auto-generated method stub
		realWorld.notifyBlocksOfNeighborChange(par1, par2, par3, par4);
	}
	
	@Override
	public void notifyBlocksOfNeighborChange(int par1, int par2, int par3,
			int par4, int par5) {
		// TODO Auto-generated method stub
		realWorld.notifyBlocksOfNeighborChange(par1, par2, par3, par4, par5);
	}
	
	@Override
	public void onEntityRemoved(Entity par1Entity) {
		// TODO Auto-generated method stub
		realWorld.onEntityRemoved(par1Entity);
	}
	
	@Override
	public void playAuxSFX(int par1, int par2, int par3, int par4, int par5) {
		// TODO Auto-generated method stub
		realWorld.playAuxSFX(par1, par2, par3, par4, par5);
	}
	
	@Override
	public void playAuxSFXAtEntity(EntityPlayer par1EntityPlayer, int par2,
			int par3, int par4, int par5, int par6) {
		// TODO Auto-generated method stub
		realWorld.playAuxSFXAtEntity(par1EntityPlayer, par2, par3, par4, par5, par6);
	}
	
	@Override
	public void playRecord(String par1Str, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		realWorld.playRecord(par1Str, par2, par3, par4);
	}
	
	@Override
	public void playSoundAtEntity(Entity par1Entity, String par2Str,
			float par3, float par4) {
		// TODO Auto-generated method stub
		realWorld.playSoundAtEntity(par1Entity, par2Str, par3, par4);
	}
	
	@Override
	public void playSoundEffect(double par1, double par3, double par5,
			String par7Str, float par8, float par9) {
		// TODO Auto-generated method stub
		realWorld.playSoundEffect(par1, par3, par5, par7Str, par8, par9);
	}
	
	@Override
	public void playSoundToNearExcept(EntityPlayer par1EntityPlayer,
			String par2Str, float par3, float par4) {
		// TODO Auto-generated method stub
		realWorld.playSoundToNearExcept(par1EntityPlayer, par2Str, par3, par4);
	}
	
	@Override
	public MovingObjectPosition rayTraceBlocks_do_do(Vec3 par1Vec3,
			Vec3 par2Vec3, boolean par3, boolean par4) {
		// TODO Auto-generated method stub
		return realWorld.rayTraceBlocks_do_do(par1Vec3, par2Vec3, par3, par4);
	}
	
	@Override
	public void removeBlockTileEntity(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		realWorld.removeBlockTileEntity(par1, par2, par3);
	}
	
	@Override
	public void removeEntity(Entity par1Entity) {
		// TODO Auto-generated method stub
		realWorld.removeEntity(par1Entity);
	}
	
	@Override
	public void removePlayerEntityDangerously(Entity par1Entity) {
		// TODO Auto-generated method stub
		realWorld.removePlayerEntityDangerously(par1Entity);
	}
	
	@Override
	public void removeWorldAccess(IWorldAccess par1iWorldAccess) {
		// TODO Auto-generated method stub
		realWorld.removeWorldAccess(par1iWorldAccess);
	}
	
	@Override
	public void scheduleBlockUpdate(int par1, int par2, int par3, int par4,
			int par5) {
		// TODO Auto-generated method stub
		realWorld.scheduleBlockUpdate(par1, par2, par3, par4, par5);
	}
	
	@Override
	public void scheduleBlockUpdateFromLoad(int par1, int par2, int par3,
			int par4, int par5, int par6) {
		// TODO Auto-generated method stub
		realWorld.scheduleBlockUpdateFromLoad(par1, par2, par3, par4, par5, par6);
	}
	
	@Override
	public void scheduleBlockUpdateWithPriority(int par1, int par2, int par3,
			int par4, int par5, int par6) {
		// TODO Auto-generated method stub
		realWorld.scheduleBlockUpdateWithPriority(par1, par2, par3, par4, par5, par6);
	}
	
	@Override
	public List selectEntitiesWithinAABB(Class par1Class,
			AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3iEntitySelector) {
		// TODO Auto-generated method stub
		return realWorld.selectEntitiesWithinAABB(par1Class, par2AxisAlignedBB,
				par3iEntitySelector);
	}
	
	@Override
	public void sendQuittingDisconnectingPacket() {
		// TODO Auto-generated method stub
		realWorld.sendQuittingDisconnectingPacket();
	}
	
	@Override
	public void setAllowedSpawnTypes(boolean par1, boolean par2) {
		// TODO Auto-generated method stub
		realWorld.setAllowedSpawnTypes(par1, par2);
	}
	
	@Override
	public boolean setBlock(int par1, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return realWorld.setBlock(par1, par2, par3, par4);
	}
	
	@Override
	public boolean setBlock(int par1, int par2, int par3, int par4, int par5,
			int par6) {
		// TODO Auto-generated method stub
		return realWorld.setBlock(par1, par2, par3, par4, par5, par6);
	}
	
	@Override
	public boolean setBlockMetadataWithNotify(int par1, int par2, int par3,
			int par4, int par5) {
		// TODO Auto-generated method stub
		return realWorld.setBlockMetadataWithNotify(par1, par2, par3, par4, par5);
	}
	
	@Override
	public void setBlockTileEntity(int par1, int par2, int par3,
			TileEntity par4TileEntity) {
		// TODO Auto-generated method stub
		realWorld.setBlockTileEntity(par1, par2, par3, par4TileEntity);
	}
	
	@Override
	public boolean setBlockToAir(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.setBlockToAir(par1, par2, par3);
	}
	
	@Override
	public void setEntityState(Entity par1Entity, byte par2) {
		// TODO Auto-generated method stub
		realWorld.setEntityState(par1Entity, par2);
	}
	
	@Override
	public void setItemData(String par1Str, WorldSavedData par2WorldSavedData) {
		// TODO Auto-generated method stub
		realWorld.setItemData(par1Str, par2WorldSavedData);
	}
	
	@Override
	public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2,
			int par3, int par4, int par5) {
		// TODO Auto-generated method stub
		realWorld.setLightValue(par1EnumSkyBlock, par2, par3, par4, par5);
	}
	
	@Override
	public void setRainStrength(float par1) {
		// TODO Auto-generated method stub
		realWorld.setRainStrength(par1);
	}
	
	@Override
	public Random setRandomSeed(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		return realWorld.setRandomSeed(par1, par2, par3);
	}
	
	@Override
	public void setSpawnLocation() {
		// TODO Auto-generated method stub
		realWorld.setSpawnLocation();
	}
	
	@Override
	public void setSpawnLocation(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		realWorld.setSpawnLocation(par1, par2, par3);
	}
	
	@Override
	public void setWorldTime(long par1) {
		// TODO Auto-generated method stub
		realWorld.setWorldTime(par1);
	}
	
	@Override
	public boolean spawnEntityInWorld(Entity par1Entity) {
		// TODO Auto-generated method stub
		return realWorld.spawnEntityInWorld(par1Entity);
	}
	
	@Override
	public void spawnParticle(String par1Str, double par2, double par4,
			double par6, double par8, double par10, double par12) {
		// TODO Auto-generated method stub
		realWorld.spawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		realWorld.tick();
	}
	
	@Override
	public boolean tickUpdates(boolean par1) {
		// TODO Auto-generated method stub
		return realWorld.tickUpdates(par1);
	}
	
	@Override
	public void toggleRain() {
		// TODO Auto-generated method stub
		realWorld.toggleRain();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return realWorld.toString();
	}
	
	@Override
	public void unloadEntities(List par1List) {
		// TODO Auto-generated method stub
		realWorld.unloadEntities(par1List);
	}
	
	@Override
	public void updateAllLightTypes(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		realWorld.updateAllLightTypes(par1, par2, par3);
	}
	
	@Override
	public void updateAllPlayersSleepingFlag() {
		// TODO Auto-generated method stub
		realWorld.updateAllPlayersSleepingFlag();
	}
	
	@Override
	public void updateEntities() {
		// TODO Auto-generated method stub
		realWorld.updateEntities();
	}
	
	@Override
	public void updateEntity(Entity par1Entity) {
		// TODO Auto-generated method stub
		realWorld.updateEntity(par1Entity);
	}
	
	@Override
	public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2) {
		// TODO Auto-generated method stub
		realWorld.updateEntityWithOptionalForce(par1Entity, par2);
	}
	
	@Override
	public void updateLightByType(EnumSkyBlock par1EnumSkyBlock, int par2,
			int par3, int par4) {
		// TODO Auto-generated method stub
		realWorld.updateLightByType(par1EnumSkyBlock, par2, par3, par4);
	}
	
	@Override
	public void updateWeatherBody() {
		// TODO Auto-generated method stub
		realWorld.updateWeatherBody();
	}
	
	
	
	
	
	
	
	@Override
	protected IChunkProvider createChunkProvider() {
		return null;
	}

	@Override
	public Entity getEntityByID(int i) {
		return realWorld.getEntityByID(i);
	}

}
