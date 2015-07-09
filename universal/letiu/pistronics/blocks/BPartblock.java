package letiu.pistronics.blocks;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.core.ModBaseInfo;
import letiu.modbase.events.ViewEventHandler;
import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.IPartCompound;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.packets.RodExDataPacket;
import letiu.pistronics.packets.SawPacket;
import letiu.pistronics.packets.SubHitClickPacket;
import letiu.pistronics.piston.IPistonElement;
import letiu.pistronics.piston.ISpecialRotator;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.Redstone;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.render.simple.PartRenderer;
import letiu.pistronics.tiles.ITransmitter;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.tiles.TileSail;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.CollisionUtil;
import letiu.pistronics.util.MiscUtil;
import letiu.pistronics.util.RedstoneUtil;
import letiu.pistronics.util.RotateUtil;
import letiu.pistronics.util.Vector3;
import letiu.modbase.items.IBaseItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BPartblock extends PBlock implements IPistonElement, ISpecialRotator {
	
	public BPartblock() {
		this.name = "Partblock";
		this.material = "wood";
		this.hardness = 3F;
		this.resistance = 10F;
		this.creativeTab = false;
		this.textures = new String[1];
		this.textures[0] = Textures.ROD_KNOT;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		int partMeta = PartRenderer.metaForRender;
		if (partMeta != -1) {
			TilePartblock tilePB = (TilePartblock) tile; 
			PTile partTile = tilePB.getTile(partMeta);
			return tilePB.getPart(partMeta).getTextureIndex(partTile, partMeta, side);
		}
		
		return textures[0];
	}

	@Override
	public String getTexture(int meta, int side) {
		return this.textures[0];
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof ITransmitter) {
			return ((ITransmitter) tile).getStrength();
		}
		return 0;
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, int side) {
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			PBlock part = ((TilePartblock) tile).getPart(side);
			if (part != null && part instanceof BExtensionPart) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int getRenderID() {
		return PRenderManager.getRenderID(PRenderManager.partRenderer);
	}
	
	@Override
	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(0.375D, 0.375D, 0.375D, 0.625D, 0.625D, 0.625D);

		boxes.add(box);
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			TilePartblock tileP = (TilePartblock) tile;
			for (int i = 0; i < 6; i++) {
				PBlock part = tileP.getPart(i);
				if (part != null) {
					ArrayList<AxisAlignedBB> partBoxes = part.getBoxes(world, x, y, z, i);
					if (partBoxes != null) {
						boxes.addAll(partBoxes);
					}
				}
			}
		}
		
		return boxes;
	}
	
	public ArrayList<AxisAlignedBB> getBoxesWithSubHitMap(World world, int x, int y, int z, int meta, List<Byte> subHitMap) {
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(0.375D, 0.375D, 0.375D, 0.625D, 0.625D, 0.625D);
		boxes.add(box);
		subHitMap.add((byte) -1);
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			TilePartblock tileP = (TilePartblock) tile;
			for (int i = 0; i < 6; i++) {
				PBlock part = tileP.getPart(i);
				if (part != null) {
					ArrayList<AxisAlignedBB> partBoxes = part.getBoxes(world, x, y, z, i);
					if (partBoxes != null) {
						boxes.addAll(partBoxes);
						for (int k = 0; k < partBoxes.size(); k++) subHitMap.add((byte) i);
					}
				}
			}
		}
		
		return boxes;
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 playerPos, Vec3 targetPos) {
		ArrayList<Byte> subHitMap = new ArrayList<Byte>();
		ArrayList<AxisAlignedBB> boxes = this.getBoxesWithSubHitMap(world, x, y, z, -1, subHitMap);
		return CollisionUtil.multiCollisionRayTraceWithSubHitMap(world, x, y, z, playerPos, targetPos, boxes, subHitMap);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public PTile createPTile() {
		return new TilePartblock();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		
		ItemStack stack = player.getCurrentEquippedItem();
		
		if (stack == null) return false;
		
		// Tool doesn't work because function always returns true, so thats why... //
		if (CompareUtil.compareIDs(stack, ItemData.tool.item)) return false;
		
		Block block = BlockItemUtil.getBlockFromStack(stack);
		Item item = stack.getItem();
		TilePartblock tile = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
		
		if (tile != null) {
			
			// Adding //
			if (!tile.hasPart(side) && item != null && (item instanceof IBaseItem)) {
				PItem pItem = ((IBaseItem) item).getData();
				if (pItem != null && pItem instanceof IPart && ((IPart) pItem).canPartBeAdded(world, x, y, z, side)) {
					if (!world.isRemote) {
						PBlock part = ((IPart) pItem).getPartBlock(); 
						tile.setPart(part, side);		
						
						PTile partTile = tile.getTile(side);
						if (partTile != null && stack.stackTagCompound != null) {
							partTile.readFromNBT(stack.stackTagCompound);
						}
						
						((IPart) part).onPartPlaced(tile, partTile, side, MiscUtil.getPartFromClick(xHit, yHit, zHit), player);
						
						if (!player.capabilities.isCreativeMode) stack.stackSize--;
						PacketHandler.sendToAllInDimension(new RodExDataPacket(partTile, side, player.dimension), player.dimension);
					}
					return true;
					
				}
				else if (pItem != null && pItem instanceof IPartCompound) {
					if (!tile.hasPart(side ^ 1)) {
						if (!world.isRemote) {
							tile.setPart((PBlock) ((IPartCompound) pItem).getPart(side, side), side);
							tile.setPart((PBlock) ((IPartCompound) pItem).getPart(side, side ^ 1), side ^ 1);	
							
							PTile partTile = tile.getTile(side);
							if (partTile != null && stack.stackTagCompound != null) {
								partTile.readFromNBT(stack.stackTagCompound);
							}
							PacketHandler.sendToAllInDimension(new RodExDataPacket(partTile, side, player.dimension), player.dimension);
							
							partTile = tile.getTile(side ^ 1);
							if (partTile != null && stack.stackTagCompound != null) {
								partTile.readFromNBT(stack.stackTagCompound);
							}
							PacketHandler.sendToAllInDimension(new RodExDataPacket(partTile, side ^ 1, player.dimension), player.dimension);
							
							if (!player.capabilities.isCreativeMode) stack.stackSize--;
						}
						return true;
					}
				}
			}
			
			// return false if possible //
			PTile partTile;
			boolean hasCamouSail = false;
			
			for (int i = 0; i < 6; i++) {
				partTile = tile.getTile(i);
				if (partTile != null && partTile instanceof TileSail && ((TileSail) partTile).camou) {
					hasCamouSail = true;
				}
			}

			if (!hasCamouSail) {
				partTile = tile.getTile(side);
				if (partTile == null || !(partTile instanceof TileExtension) || !((TileExtension) partTile).camou) {
					if (stack.getItem() instanceof ItemBlock && !CompareUtil.compareIDs(stack, ItemReference.WOODEN_SLAB)) return false;
				}
			}
			
			// Clicking //
			if (ViewEventHandler.subHitEvent != null) {
				int subHit = ViewEventHandler.subHitEvent.target.subHit;
				if (subHit >= 0 && subHit < 6) {
					PBlock part = tile.getPart(subHit);
					if (part != null) {
						if (world.isRemote) {
							if (CompareUtil.compareIDs(stack, ItemData.saw.item)) {
								PacketHandler.sendToServer(new SawPacket(x, y, z, subHit, player));
							}
							else PacketHandler.sendToServer(new SubHitClickPacket(x, y, z, subHit, side, player));
						}
						return true;
					}
				}
			}
			
			// Pulsing //
			if (tile.isConductive()) {
				if (CompareUtil.compareIDs(stack, ItemReference.REDSTONE_TORCH)) {
					if (!world.isRemote) {
						RedstoneUtil.pulseBlock(world, x, y, z, player.dimension, Redstone.TORCH_PULSE_TICKS);
					}
					return true;
				}
				if (CompareUtil.compareIDs(stack, ItemReference.REDSTONE_BLOCK)) {
					if (!world.isRemote) {
						RedstoneUtil.pulseBlock(world, x, y, z, player.dimension, Redstone.BLOCK_PULSE_TICKS);
					}
					return true;
				}
			}
		}
		
		return true;
	}
	
	public boolean tryFuse(World world, int x, int y, int z) {
		
		PTile tileRaw = WorldUtil.getPTile(world, x, y, z);
		if (tileRaw != null && tileRaw instanceof TilePartblock) {
			TilePartblock tile = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
			
			int ext = isExtension(tile);
			if (ext != -1) {
				
				TileExtension tileExt1 = (TileExtension) tile.getTile(ext);
				WorldUtil.setBlock(world, x, y, z, BlockData.extension.block, ext, 0);
				TileExtension tileExt2 = (TileExtension) WorldUtil.getPTile(world, x, y, z);
				
				tileExt2.sticky = tileExt1.sticky;
				tileExt2.super_sticky = tileExt1.super_sticky;
				tileExt2.camou = tileExt1.camou;
				tileExt2.camouID = tileExt1.camouID;
				tileExt2.camouMeta = tileExt1.camouMeta;
				tileExt2.redstone = tileExt1.redstone;
				
				WorldUtil.setTileEntity(world, x, y, z, tileExt2.tileEntity);
				
				if (world.isRemote) {
					
					WorldUtil.setBlock(world, x, y, z, this.block, ext, 0);
				}
				
//				
//				WorldUtil.setBlockFacing(world, x, y, z, ext);
//				WorldUtil.updateBlock(world, x, y, z);
//				WorldUtil.toggltBit4(world, x, y, z);
				return true;
			}
		}
		return false;
	}
	
	private int isExtension(TilePartblock tile) {
		for (int i = 0; i < 6; i++) {
			if (tile.getPart(i) instanceof BExtensionPart && tile.getPart(i ^ 1) instanceof BRodPart) {
				if (((TileExtension) tile.getTile(i)).redstone == ((TileRod) tile.getTile(i ^ 1)).redstone) {
					for (int j = 0; j < 6; j++) {
						if (i != j && i != (j ^ 1) && tile.getPart(j) != null) {
							return -1;
						}
					}
					return i;
				}
			}
		}
		return -1;
	}
	
	@Override
	public ItemStack getDroppedStack(PTile tile, int meta) {
		return null;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		
		PTile tileRaw = WorldUtil.getPTile(world, x, y, z);
		if (tileRaw != null && tileRaw instanceof TilePartblock) {
			TilePartblock tile = (TilePartblock) tileRaw;
			for (int i = 0; i < 6; i++) {
				PBlock part = tile.getPart(i);
				PTile partTile = tile.getTile(i);
				
				if (part != null) {
					ItemStack drop = part.getDroppedStack(partTile, 0);
					if (drop != null) drops.add(drop);
				}
			}
		}
		
		return drops;
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int fortune, EntityPlayer player) {

		ItemStack stack = player.getCurrentEquippedItem();
		
		if (!player.capabilities.isCreativeMode || ItemReference.isHarvestTool(stack)) {
			for (ItemStack drop : getDrops(world, x, y, z, 0, fortune)) {
				WorldUtil.spawnItemStack(world, x, y, z, drop);
			}
		}
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		
		TilePartblock tile = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
		
		if (tile != null) {
			if (target.subHit >= 0 && target.subHit < 6) {
				return tile.getPart(target.subHit).getDroppedStack(tile.getTile(target.subHit), 0);
			}
		}
		
		return null;
	}
	
	@Override
	public boolean shouldUseDefaultPick() {
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		PTile tileRaw = WorldUtil.getPTile(world, x, y, z);
		if (tileRaw != null && tileRaw instanceof TilePartblock) {
			TilePartblock tile = (TilePartblock) tileRaw;
			if (tile.isConductive()) {
				
				BlockProxy proxy = new BlockProxy(world, x, y, z);
				
				TileExtension inputTile = null;
				int input = -1;
				
				for (int i = 0; i < 6; i++) {
					PTile partTile = tile.getTile(i);
					if (partTile != null && partTile instanceof TileExtension) {
						TileExtension tileExt = (TileExtension) partTile;
						if (tileExt.isConductive()) {
							int sideInput = proxy.getPowerInputFrom(i);
							if (tileExt.getComp() != 0 && tileExt.getComp() != sideInput) {
								sideInput = 0;
							}
							if (sideInput > input) {
								input = sideInput;
								inputTile = tileExt;
							}	
						}
					}
				}
				
				if (inputTile != null) {
					if (inputTile.isInput() && input < inputTile.getStrength() || input > inputTile.getStrength()) {
						PistonSystem system = new PistonSystem(proxy, 0, 0, SystemType.REDSTONE);
						system.setToInput(proxy, input);
						inputTile.setToInput(); // needs to be done manually here //
					}
				}
			}
		}
	}
	
	@Override
	public int getWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return this.getStrongPower(world, x, y, z, side);
	}
	
	@Override
	public int getStrongPower(IBlockAccess world, int x, int y, int z, int side) {
		PTile tileRaw = WorldUtil.getPTile(world, x, y, z);
		if (tileRaw != null && tileRaw instanceof TilePartblock) {
			TilePartblock tile = (TilePartblock) tileRaw;
			
			PTile partTile = tile.getTile(side ^ 1);
			if (partTile != null && partTile instanceof TileExtension) {
				TileExtension tileEx = (TileExtension) partTile;
				
				if (!tileEx.isInput()) {
					if (tileEx.getComp() != 0 && tileEx.getStrength() != tileEx.getComp()) return 0;
					return tileEx.getStrength();
				}
			}
		}
		
		return 0;
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return ModBaseInfo.MC_VERSION.equals("1.6.4");
	}
	
	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, boolean strongConnection) {
		
		if (system.getSystemType() == SystemType.REDSTONE) {
			if (isTransmitter(proxy)) RedstoneUtil.connectAdjacentExtensions(system, proxy);
			else return;
		}
		
		system.addElement(proxy, strongConnection);
		
		TilePartblock tile = (TilePartblock) proxy.getPTile();
		for (int i = 0; i < 6; i++) {
			IPart part = (IPart) tile.getPart(i);
			if (part != null) {
				part.getConnectedForSystem(system, proxy, tile, i);
			}
		}
	}

	@Override
	public boolean connectsToSide(BlockProxy proxy, int side, SystemType type) {
		TilePartblock tile = (TilePartblock) proxy.getPTile();
		IPart part = (IPart) tile.getPart(side);
		if (part != null) {
			return part.connectsToSide(proxy, tile, side, side, type);
		}
		else return false;
	}
	
	@Override
	public boolean canRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
		return true;
	}

	@Override
	public void preRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
	}

	@Override
	public void postRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
		
		PTile pTile = WorldUtil.getPTile(world, x, y, z);
		if (pTile != null && pTile instanceof TilePartblock) {
			TilePartblock tile = (TilePartblock) pTile;
			PTile[] partTiles = new PTile[6];
			PBlock[] parts = new PBlock[6];
			for (int i = 0; i < 6; i++) {
				int newDir = RotateUtil.rotateDir(i, rotateDir);
				parts[newDir] = tile.getPart(i);
				partTiles[newDir] = tile.getTile(i);
			}
			for (int i = 0; i < 6; i++) {
				tile.setPartSolo(parts[i], i);
				tile.setTile(partTiles[i], i);
				if (parts[i] != null) {
					((IPart) parts[i]).postRotate(tile, partTiles[i], i, rotateDir);
				}
			}
		}
	}

	@Override
	public boolean isTransmitter(BlockProxy proxy) {
		TilePartblock tile = (TilePartblock) proxy.getPTile();
		for (int i = 0; i < 6; i++) {
			PTile elementTile = tile.getTile(i);
			if (elementTile != null) {
				if (elementTile instanceof TileRod) {
					if (((TileRod) elementTile).redstone) return true;
				}
				if (elementTile instanceof TileExtension) {
					if (((TileExtension) elementTile).redstone) return true;
				}
			}
		}
		return false;
	}
}
