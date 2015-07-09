package letiu.pistronics.blocks;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.items.IBaseItem;
import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.IPartCompound;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.gears.Gear;
import letiu.pistronics.packets.RodExDataPacket;
import letiu.pistronics.packets.SawPacket;
import letiu.pistronics.piston.IPistonElement;
import letiu.pistronics.piston.ISpecialRotator;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.Redstone;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.tiles.ITransmitter;
import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TileGear;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.FacingUtil;
import letiu.pistronics.util.MiscUtil;
import letiu.pistronics.util.RedstoneUtil;
import letiu.pistronics.util.RotateUtil;
import letiu.pistronics.util.Vector3;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BRod extends PBlock implements IPistonElement, ISpecialRotator {

	public BRod() {
		this.name = "Rod Block";
		this.material = "wood";
		this.hardness = 3F;
		this.resistance = 10F;
		this.creativeTab = true;
		this.textures = new String[5];
		this.textures[0] = Textures.ROD_ENDS;
		this.textures[1] = Textures.ROD_SIDE;
		this.textures[2] = Textures.ROD_REDSTONE;
		this.textures[3] = Textures.ROD_REDSTONE_ON;
		this.textures[4] = Textures.VOID;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		
		boolean redstone = false;
		boolean active = false;
		
		if (tile != null && tile instanceof TileElementHolder) {
			if (side == (meta ^ 1) && tile instanceof TileMech && !((TileMech) tile).isMoving()) {
				return Textures.VOID; // don't show the back when in a not moving machine
			}
			tile = ((TileElementHolder) tile).getPElementTile();
			
		}
		
		if (tile != null && tile instanceof ITransmitter) {
			ITransmitter trans = (ITransmitter) tile;
			redstone = trans.isConductive();
			active = trans.isActive();
		}
		
		meta = meta & 7;
		if (side == meta || (side ^ 1) == meta) {
			return textures[0];
		}
		else return redstone ? (active ? textures[3] : textures[2]) : textures[1]; 
	}

	@Override
	public String getTexture(int meta, int side) {
		return getTextureIndex(null, meta, side);
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		
		//System.out.println("BRod.shouldSideBeRendered()");
		
		int xPos = x - Facing.offsetsXForSide[side];
		int yPos = y - Facing.offsetsYForSide[side];
		int zPos = z - Facing.offsetsZForSide[side];
		
		int facing = WorldUtil.getBlockFacing(world, xPos, yPos, zPos);
		
//		PTile tile = WorldUtil.getPTile(world, xPos, yPos, zPos);
//		if (tile != null && tile instanceof TileMech) {
//			if (side == (facing ^ 1)) return false;
//		}
//		
//		
//		
		if (side == facing || side == (facing ^ 1)) {
			return super.shouldSideBeRendered(world, x, y, z, side);
		}
		
		return true;
		/*
		int facing = WorldUtil.getBlockFacing(world, x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]);
		if (facing == side || facing == (side ^ 1)) {
			return super.shouldSideBeRendered(world, x, y, z, side);
		}
		return true;*/
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
	public int getRenderID() {
		return PRenderManager.getRenderID(PRenderManager.rodRenderer);
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public PTile createPTile() {
		return new TileRod();
	}
	
	@Override
	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		
		AxisAlignedBB box = null;
		switch (meta & 7) {
		case 1: case 0: box = AxisAlignedBB.getBoundingBox(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D); break;
		case 2: case 3: box = AxisAlignedBB.getBoundingBox(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1.0D); break;
		case 4: case 5: box = AxisAlignedBB.getBoundingBox(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D); break;
		}
		if (box == null) return null; 
		
		boxes.add(box);
		return boxes;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		
		ItemStack stack = player.getCurrentEquippedItem();
		
		if (stack == null) return false;
		
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		boolean inMachine = false;
		
		PTile tileRaw = WorldUtil.getPTile(world, x, y, z);
		if (tileRaw != null && tileRaw instanceof TileElementHolder) {
			tileRaw = ((TileElementHolder) tileRaw).getPElementTile();
			inMachine = true;
		}
		
		PTile tile = tileRaw;
		
		if (tile != null && tile instanceof TileRod) {
			TileRod tileR = (TileRod) tile;
			if (!tileR.redstone) {
				if (ItemReference.isRedstone(stack)) {
					if (!world.isRemote) {			
						tileR.redstone = true;
						if (!player.capabilities.isCreativeMode) stack.stackSize--;
						PacketHandler.sendToAllInDimension(new RodExDataPacket(tile, player.dimension, inMachine), player.dimension);
					}
					return true;
				}
				if (stack.stackSize >= 2 && CompareUtil.compareIDs(stack, ItemData.pileOfRedstone.item)) {
					if (!world.isRemote) {
						tileR.redstone = true;
						if (!player.capabilities.isCreativeMode) stack.stackSize -= 2;
						PacketHandler.sendToAllInDimension(new RodExDataPacket(tile, player.dimension, inMachine), player.dimension);
					}
					return true;
				}
			}
		}
		
		if (tile != null && tile instanceof TileRod && ItemReference.isOre(stack, "slabWood")) {
			WorldUtil.setBlock(world, x, y, z, BlockData.extension.block);
			int meta = FacingUtil.getFacingFromClick(facing, xHit, yHit, zHit);
			WorldUtil.setBlockFacing(world, x, y, z, meta);
			TileExtension extTile = (TileExtension) WorldUtil.getPTile(world, x, y, z);
			extTile.redstone = ((TileRod) tile).redstone;
			if (!player.capabilities.isCreativeMode) stack.stackSize--;
			if (inMachine && !world.isRemote) PacketHandler.sendToAllInDimension(new RodExDataPacket(tile, player.dimension, true), player.dimension);
			return true;
		}
		
		
		if (tile != null && tile instanceof TileRod) {
			TileRod tileR = (TileRod) tile;
			if (tileR.redstone) {
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
		
		if (CompareUtil.compareIDs(stack, ItemData.spade.item)) {
			if (((TileRod) tile).redstone) {
				((TileRod) tile).redstone = false;
				if (!world.isRemote) {
					if (!player.capabilities.isCreativeMode) {		
						if (this instanceof BRodPart) {
							WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.pileOfRedstone));
						}
						else {
							WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemReference.REDSTONE_ITEM));
						}
					}
					PacketHandler.sendToAllInDimension(new RodExDataPacket(tile, player.dimension, inMachine), player.dimension);
				}
				//WorldUtil.toggltBit4(world, x, y, z);
				return true;
			}
		}
		
		if (!inMachine && CompareUtil.compareIDs(stack, ItemData.saw.item)) {
			
			int partFacing = FacingUtil.getFacingFromClick(facing, xHit, yHit, zHit);
			
			toPartblock(world, x, y, z);
			
			if (!world.isRemote) {
				if (!player.capabilities.isCreativeMode) {
					ItemStack drop = BlockItemUtil.getStack(BlockData.rodPart);
					if (tile != null && tile instanceof TileRod) drop.stackTagCompound = ((TileRod) tile).getNBTForItem();
					WorldUtil.spawnItemStack(world, x, y, z, drop);
				}
				
				TilePartblock tilePB = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
				
				tilePB.setPart(null, partFacing);
				
				int dimID = player.dimension;
				PacketHandler.sendToAllInDimension(new SawPacket(x, y, z, partFacing, dimID), dimID);
			}
			return true;
		}
		
		if (CompareUtil.compareIDs(stack, BlockData.gear.block)) {
			if (stack.stackTagCompound != null) {
				Gear gear = Gear.getGearFromNBT(stack.stackTagCompound);
				if (BGear.checkGearRingSpace(world, x, y, z, facing, gear.size)) {
					
					WorldUtil.setBlock(world, x, y, z, BlockData.gear.block, facing, 3);
					TileGear gearTile = (TileGear) WorldUtil.getPTile(world, x, y, z);
					gearTile.setGear(gear, MiscUtil.getGearPosFromClick(facing, xHit, yHit, zHit));
					if (gear.size > 1) BGear.createGearParts(world, x, y, z, facing, gear.size);
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					return true;							
				}
			}
		}
		
		
		Item item = stack.getItem();
		
		if (!inMachine && tile != null && tile instanceof TileRod && item != null && (item instanceof IBaseItem)) {
			if (side != facing && side != (facing ^ 1)) {
				PItem pItem = ((IBaseItem) item).getData();
				if (pItem != null && pItem instanceof IPart && ((IPart) pItem).canPartBeAdded(world, x, y, z, side)) {
			
					toPartblock(world, x, y, z);
					
					TilePartblock partblockTile = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
				 
					if (partblockTile != null) {
						PBlock part = ((IPart) pItem).getPartBlock(); 
						partblockTile.setPart(part, side);	
					
						PTile partTile = partblockTile.getTile(side);
						if (partTile != null && stack.stackTagCompound != null) {
							partTile.readFromNBT(stack.stackTagCompound);
						}
						
						((IPart) part).onPartPlaced(partblockTile, partTile, side, MiscUtil.getPartFromClick(xHit, yHit, zHit), player);
						
						if (!player.capabilities.isCreativeMode) stack.stackSize--;
					}
					
					//WorldUtil.toggltBit4(world, x, y, z);
					return true;
				}
				if (pItem != null && pItem instanceof IPartCompound) {
					
					toPartblock(world, x, y, z);
					
					TilePartblock partblockTile = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
				 
					if (partblockTile != null) {
						
						for (int i = 0; i < 6; i++) {
							PBlock part = (PBlock) ((IPartCompound) pItem).getPart(side, i);
							
							if (part != null) {
								partblockTile.setPart(part, i);
							
								PTile partTile = partblockTile.getTile(i);
								if (partTile != null && stack.stackTagCompound != null) {
									partTile.readFromNBT(stack.stackTagCompound);
								}
							}
							
						}
						
						if (!player.capabilities.isCreativeMode) stack.stackSize--;
					}
					
					//WorldUtil.toggltBit4(world, x, y, z);
					return true;
				}
			}
		}
		
		return false;
	}

	private void toPartblock(World world, int x, int y, int z) {
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		TileRod tile = (TileRod) WorldUtil.getPTile(world, x, y, z);
		WorldUtil.setBlock(world, x, y, z, BlockData.partBlock.block);
		TilePartblock tilePB = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
		tilePB.setPart(BlockData.rodPart, facing);
		tilePB.setPart(BlockData.rodPart, facing ^ 1);
		((TileRod) tilePB.getTile(facing ^ 1)).redstone = ((TileRod) tilePB.getTile(facing)).redstone = tile.redstone;
	}
	
	@Override
	public ItemStack getDroppedStack(PTile tile, int meta) {
		ItemStack stack = BlockItemUtil.getStack(BlockData.rod.block);
		if (tile != null && tile instanceof TileRod) {
			stack.stackTagCompound = ((TileRod) tile).getNBTForItem();
		}
		return stack;
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
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return getDroppedStack(WorldUtil.getPTile(world, x, y, z), 0);
	}
	
	@Override
	public boolean shouldUseDefaultPick() {
		return false;
	}
	
	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, boolean strongConnection) {
		
		if (system.getSystemType() == SystemType.REDSTONE) {
			if (isTransmitter(proxy)) RedstoneUtil.connectAdjacentExtensions(system, proxy);
			else return;
		}
		
		system.addElement(proxy, strongConnection);

		int facing = proxy.getFacing();
		
		BlockProxy neighbor = proxy.getNeighbor(facing);
		if (neighbor.connectsToSide(facing ^ 1, system.getSystemType())) {
			neighbor.getConnectedForSystem(system, false);
		}
		
		neighbor = proxy.getNeighbor(facing ^ 1);
		if (neighbor.connectsToSide(facing, system.getSystemType())) {
			neighbor.getConnectedForSystem(system, false);
		}
	}
	
	@Override
	public boolean connectsToSide(BlockProxy proxy, int side, SystemType type) {
		int facing = proxy.getFacing();
		return (side == facing || side == (facing ^ 1)) && (type != SystemType.REDSTONE || isTransmitter(proxy));
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
	public boolean getSubBlocks(int itemID, CreativeTabs tab, List list) {
		
		ItemStack stack;
		
		stack = BlockItemUtil.getStack(this);
		stack.stackTagCompound = getDefaultNBT();
		list.add(stack);
		
		stack = BlockItemUtil.getStack(this);
		stack.stackTagCompound = getDefaultNBT();
		stack.stackTagCompound.setBoolean("redstone", true);
		list.add(stack);
		
		return true;
	}

	public static NBTTagCompound getDefaultNBT() {
		NBTTagCompound nbt = NBTUtil.getNewCompound();
		
		nbt.setBoolean("redstone", false);
		
		return nbt;
	}
	
	@Override
	public PItem getItemBlock() {
		return ItemData.rod;
	}

	@Override
	public boolean isTransmitter(BlockProxy proxy) {
		PTile tile = proxy.getPTile();
		if (tile != null && tile instanceof TileElementHolder) {
			tile = ((TileElementHolder) tile).getPElementTile();
		}
		if (tile != null && tile instanceof TileRod)
			return ((TileRod) tile).isConductive();
		else return false;
	}
}
