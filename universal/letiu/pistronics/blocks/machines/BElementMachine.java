package letiu.pistronics.blocks.machines;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BRod;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.packets.RodExDataPacket;
import letiu.pistronics.piston.IPistonElement;
import letiu.pistronics.piston.ISpecialRotator;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.tiles.ITransmitter;
import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.CollisionUtil;
import letiu.pistronics.util.FacingUtil;
import letiu.pistronics.util.RotateUtil;
import letiu.pistronics.util.Vector3;

public abstract class BElementMachine extends BBaseMachine implements IPistonElement, ISpecialRotator {

	@Override
	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		
		switch (meta & 7) {
		case 0: boxes.add(AxisAlignedBB.getBoundingBox(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F)); break;
        case 1: boxes.add(AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F)); break;
        case 2: boxes.add(AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F)); break;
        case 3: boxes.add(AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F)); break;
        case 4: boxes.add(AxisAlignedBB.getBoundingBox(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F)); break;
        case 5: boxes.add(AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F)); break;
		default: return null;
		}
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TileElementHolder) {
			if (((TileElementHolder) tile).hasElement()) {
				PBlock element = ((TileElementHolder) tile).getPElement();
				if (element != null && !(element instanceof BElementMachine)) {
					ArrayList<AxisAlignedBB> eBoxes = element.getBoxes(world, x, y, z, meta);
					if (eBoxes != null) boxes.addAll(eBoxes);
				}
			}
		}
		
		return boxes;
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 playerPos, Vec3 targetPos) {
		int facing = WorldUtil.getBlockFacing(world, x, y, z);

		ArrayList<AxisAlignedBB> boxes = this.getBoxes(world, x, y, z, facing);
		ArrayList<Byte> subHitMap = new ArrayList<Byte>();
		
		subHitMap.add((byte) 0);
		for (int i = 1; i < boxes.size(); i++) {
			subHitMap.add((byte) 1);
		}
		
		return CollisionUtil.multiCollisionRayTraceWithSubHitMap(world, x, y, z, playerPos, targetPos, boxes, subHitMap);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		ItemStack stack = player.getCurrentEquippedItem();
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		
		if (tile != null && tile instanceof TileMech) {
			if (FacingUtil.getRelevantAxis(facing, xHit, yHit, zHit) < 0.75F) {
				TileMech tileMech = (TileMech) tile;
				if (stack != null) {
					if (!tileMech.camou) {
						if (CompareUtil.compareIDs(stack, ItemData.camoupaste.item)) {
							if (!world.isRemote) {
								tileMech.camou = true;
								if (!player.capabilities.isCreativeMode) stack.stackSize--;
								PacketHandler.sendToAllInDimension(new RodExDataPacket(tileMech, player.dimension), player.dimension);
							}
							return true;
						}
					}
					else {
						Block block = BlockItemUtil.getBlockFromStack(stack);
						if (block != null && block.getRenderType() == 0) {
							if (!world.isRemote) {
								tileMech.camouID = BlockItemUtil.getBlockID(block);
								tileMech.camouMeta = stack.getItemDamage();
								PacketHandler.sendToAllInDimension(new RodExDataPacket(tileMech, player.dimension), player.dimension);
							}
							return true;
						}
						else if (CompareUtil.compareIDs(stack, ItemData.spade.item)) {
							if (!world.isRemote) {
								if (tileMech.camouID != -1) {
									tileMech.camouID = -1;
									tileMech.camouMeta = 0;
								}
								else {
									tileMech.camou = false;
									if (!player.capabilities.isCreativeMode) WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.camoupaste));
								}
								PacketHandler.sendToAllInDimension(new RodExDataPacket(tileMech, player.dimension), player.dimension);
							}
							return true;
						}
					}
				}
			}
		}
	
		if (tile != null && tile instanceof TileElementHolder) {
			
			TileElementHolder tileEH = (TileElementHolder) tile;
			
			if (stack == null && player.isSneaking() && tileEH.hasElement()) {
				
				if (!world.isRemote) {
					if (!player.capabilities.isCreativeMode) {
						ItemStack drop = BlockItemUtil.getStack(tileEH.getElement());
						
						drop.stackTagCompound = NBTUtil.getNewCompound();
						tileEH.getPElementTile().writeToNBT(drop.stackTagCompound);
						
						WorldUtil.spawnItemStack(world, x, y, z, drop);
					}
					tileEH.clear();
					PacketHandler.sendToAllInDimension(new RodExDataPacket(tileEH, player.dimension, true, true), player.dimension);
				}
				
				return true;
			}
			
			if (FacingUtil.getRelevantAxis(facing, xHit, yHit, zHit) > 0.75F) {
				
				PBlock element = tileEH.getPElement();
				
				if (element instanceof BRod && ItemReference.isOre(stack, "slabWood")) {
					TileRod rodTile = (TileRod) tileEH.getPElementTile();
					tileEH.setElement(BlockData.extension);
					TileExtension extTile = (TileExtension) tileEH.getPElementTile();
					extTile.redstone = rodTile.redstone;
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					if (!world.isRemote) PacketHandler.sendToAllInDimension(new RodExDataPacket(tileEH, player.dimension, true), player.dimension);
					return true;
				}
				
				if (element != null) {
					return element.onBlockActivated(world, x, y, z, player, side, xHit, yHit, zHit);					
				}
				
			}
			
			if (!tileEH.hasElement()) {
				if (CompareUtil.compareIDs(stack, BlockData.rod.block)) {
					tileEH.setElement(BlockData.rod);
					tileEH.setElementMeta(facing);
					TileRod tileElement = (TileRod) tileEH.getPElementTile();
					if (tileElement != null && stack.stackTagCompound != null) {
						tileElement.readFromNBT(stack.stackTagCompound);
					}
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					if (!world.isRemote) PacketHandler.sendToAllInDimension(new RodExDataPacket(tileEH, player.dimension, true), player.dimension);
					return true;
				}
				if (CompareUtil.compareIDs(stack, BlockData.extension.block)) {
					tileEH.setElement(BlockData.extension);
					tileEH.setElementMeta(facing);
					TileExtension tileElement = (TileExtension) tileEH.getPElementTile();
					if (tileElement != null && stack.stackTagCompound != null) {
						tileElement.readFromNBT(stack.stackTagCompound);
					}
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					if (!world.isRemote) PacketHandler.sendToAllInDimension(new RodExDataPacket(tileEH, player.dimension, true), player.dimension);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		return drops;
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int fortune, EntityPlayer player) {

		ItemStack stack = player.getCurrentEquippedItem();
		
		if (!player.capabilities.isCreativeMode || ItemReference.isHarvestTool(stack)) {
			WorldUtil.spawnItemStack(world, x, y, z, getDroppedStack(WorldUtil.getPTile(world, x, y, z), 0));
			
			TileElementHolder tile = (TileElementHolder) WorldUtil.getPTile(world, x, y, z);
			if (tile.hasElement()) {
				ItemStack drop = BlockItemUtil.getStack(tile.getElement());
				
				drop.stackTagCompound = tile.getPElementTile().getNBTForItem();
				WorldUtil.spawnItemStack(world, x, y, z, drop);
			}
		}
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null) {
			if (tile instanceof ITransmitter) {
				if (((ITransmitter) tile).isActive()) {
					return ((ITransmitter) tile).getStrength();
				}
			}
			if (tile instanceof TileElementHolder) {
				PTile tileEH = ((TileElementHolder) tile).getPElementTile();
				if (tileEH != null && tileEH instanceof ITransmitter) {
					if (((ITransmitter) tileEH).isActive()) {
						return ((ITransmitter) tileEH).getStrength();
					}
				}
			}
		}
		return 0;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, int side) {
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		
		if (side == facing) {
			PTile tile = WorldUtil.getPTile(world, x, y, z);
			if (tile != null && tile instanceof TileElementHolder) {
				if (((TileElementHolder) tile).hasElement()) {
					return ((TileElementHolder) tile).getPElement().isSideSolid(world, x, y, z, side);
				}
			}
			return false;
		}
		
		return true;
	}
	
	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, boolean strongConnection) {
		system.addSpecial(proxy);
		
		PTile pTile = proxy.getPTile();
		if (pTile != null && pTile instanceof TileElementHolder) {
			Block element = ((TileElementHolder) pTile).getElement();
			if (element != null && element instanceof BaseBlock && ((BaseBlock) element).data instanceof IPistonElement) {
				PBlock pBlock = ((BaseBlock) element).data;
				((IPistonElement) pBlock).getConnectedForSystem(system, proxy, strongConnection);
				return;
			}
		}
		
		system.addElement(proxy, strongConnection);
	}

	@Override
	public boolean connectsToSide(BlockProxy proxy, int side, SystemType type) {
		PTile pTile = proxy.getPTile();
		if (pTile != null && pTile instanceof TileElementHolder) {
			Block element = ((TileElementHolder) pTile).getElement();
			if (element != null && element instanceof BaseBlock && ((BaseBlock) element).data instanceof IPistonElement) {
				PBlock pBlock = ((BaseBlock) element).data;
				return ((IPistonElement) pBlock).connectsToSide(proxy, side, type);
			}
		}
		return false;
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

		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		facing = RotateUtil.rotateDir(facing, rotateDir);
		WorldUtil.setBlockFacing(world, x, y, z, facing);
	}
	
	@Override
	public boolean isTransmitter(BlockProxy proxy) {
		TileElementHolder tile = (TileElementHolder) proxy.getPTile();
		
		PTile elementTile = tile.getPElementTile();
		if (elementTile != null) {
			if (elementTile instanceof TileRod) {
				if (((TileRod) elementTile).redstone) return true;
			}
			if (elementTile instanceof TileExtension) {
				if (((TileExtension) elementTile).redstone) return true;
			}
		}
		
		return false;
	}
}
