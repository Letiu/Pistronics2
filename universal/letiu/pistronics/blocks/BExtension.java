package letiu.pistronics.blocks;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.core.ModBaseInfo;
import letiu.modbase.items.IBaseItem;
import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.machines.BElementMachine;
import letiu.pistronics.blocks.machines.BRodFolder;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.IPartCompound;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
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
import letiu.pistronics.tiles.TileCreativeMachine;
import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TileMechPiston;
import letiu.pistronics.tiles.TileMechRotator;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.util.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BExtension extends PBlock implements IPistonElement, ISpecialRotator {

	public BExtension() {
		this.name = "Extension Block";
		this.material = "wood";
		this.hardness = 3F;
		this.resistance = 10F;
		this.creativeTab = true;
		this.textures = new String[71];
		this.textures[0] = Textures.EXTENSION_NORMAL;
		this.textures[1] = Textures.EXTENSION_STICKY;
		this.textures[2] = Textures.EXTENSION_SUPER_STICKY;
		this.textures[3] = Textures.EXTENSION_SIDE;
		this.textures[4] = Textures.CAMOU_EXT;
		this.textures[5] = Textures.CAMOU_EXT_STICKY;
		this.textures[6] = Textures.CAMOU_EXT_SUPER_STICKY;
		this.textures[7] = Textures.RS_EXT_OFF;
		this.textures[8] = Textures.RS_EXT_OFF_S;
		this.textures[9] = Textures.RS_EXT_OFF_S_S;
		this.textures[10] = Textures.RS_EXT_ON;
		this.textures[11] = Textures.RS_EXT_ON_S;
		this.textures[12] = Textures.RS_EXT_ON_S_S;
		this.textures[13] = Textures.RS_EXT_SIDE_OFF;
		this.textures[14] = Textures.RS_EXT_SIDE_ON;
		this.textures[15] = Textures.VOID;
		this.textures[16] = Textures.EXT_SIDE_MID;
		this.textures[17] = Textures.EXT_SIDE_RIGHT;
		this.textures[18] = Textures.EXT_SIDE_LEFT;
		this.textures[19] = Textures.RS_EXT_OFF_SIDE_MID;
		this.textures[20] = Textures.RS_EXT_OFF_SIDE_RIGHT;
		this.textures[21] = Textures.RS_EXT_OFF_SIDE_LEFT;
		this.textures[22] = Textures.RS_EXT_ON_SIDE_MID;
		this.textures[23] = Textures.RS_EXT_ON_SIDE_RIGHT;
		this.textures[24] = Textures.RS_EXT_ON_SIDE_LEFT;
		this.textures[25] = Textures.RS_EXT_CAMOU_OFF;
		this.textures[26] = Textures.RS_EXT_CAMOU_OFF_S;
		this.textures[27] = Textures.RS_EXT_CAMOU_OFF_S_S;
		this.textures[28] = Textures.RS_EXT_CAMOU_ON;
		this.textures[29] = Textures.RS_EXT_CAMOU_ON_S;
		this.textures[30] = Textures.RS_EXT_CAMOU_ON_S_S;
		this.textures[31] = Textures.REDIO_EXT_ON;
		this.textures[32] = Textures.REDIO_EXT_OFF;
		this.textures[33] = Textures.REDIO_SUPER_EXT_ON;
		this.textures[34] = Textures.REDIO_SUPER_EXT_OFF;
		this.textures[35] = Textures.REDIO_CAMOU_EXT_ON;
		this.textures[36] = Textures.REDIO_CAMOU_EXT_OFF;
		this.textures[37] = Textures.REDIO_CAMOU_SUPER_EXT_ON;
		this.textures[38] = Textures.REDIO_CAMOU_SUPER_EXT_OFF;
		
		for (int i = 1; i <= 16; i++) {
			this.textures[38 + i] = Textures.RS_COMP_EXT_OFF_SIDE_X + i;
		}
		
		for (int i = 1; i <= 16; i++) {
			this.textures[54 + i] = Textures.RS_COMP_EXT_ON_SIDE_X + i;
		}
		
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		
		meta = meta & 7;
		
		if (tile instanceof TileElementHolder) {
			tile = ((TileElementHolder) tile).getPElementTile();
		}
		
		if (tile == null || !(tile instanceof TileExtension)) {
			if (side == (meta ^ 1) || side == meta) return textures[0];
			else return textures[3];
		}
		
		TileExtension tileExt = (TileExtension) tile;
		
		if (meta == side) {
			
			if (tileExt.camouID != -1) {
				return BaseBlock.BLOCK_PREFIX + "x" + tileExt.camouID + "x" + tileExt.camouMeta;
			}
			
			if (tileExt.redstone) {
				if (tileExt.isActive()) {
					if (tileExt.camou) {
						if (tileExt.redio) {
							return tileExt.super_sticky ? Textures.REDIO_CAMOU_SUPER_EXT_ON : Textures.REDIO_CAMOU_EXT_ON; 
						}
						else return tileExt.super_sticky ? Textures.RS_EXT_CAMOU_ON_S_S : tileExt.sticky ? Textures.RS_EXT_CAMOU_ON_S : Textures.RS_EXT_CAMOU_ON; 
					}
					else {
						if (tileExt.redio) {
							return tileExt.super_sticky ? Textures.REDIO_SUPER_EXT_ON : Textures.REDIO_EXT_ON; 
						}
						else return tileExt.super_sticky ? Textures.RS_EXT_ON_S_S : tileExt.sticky ? Textures.RS_EXT_ON_S : Textures.RS_EXT_ON;
					}
				}
				else {
					if (tileExt.camou) {
						if (tileExt.redio) {
							return tileExt.super_sticky ? Textures.REDIO_CAMOU_SUPER_EXT_OFF : Textures.REDIO_CAMOU_EXT_OFF; 
						}
						else return tileExt.super_sticky ? Textures.RS_EXT_CAMOU_OFF_S_S : tileExt.sticky ? Textures.RS_EXT_CAMOU_OFF_S : Textures.RS_EXT_CAMOU_OFF; 
					}
					else {
						if (tileExt.redio) {
							return tileExt.super_sticky ? Textures.REDIO_SUPER_EXT_OFF : Textures.REDIO_EXT_OFF; 
						}
						else return tileExt.super_sticky ? Textures.RS_EXT_OFF_S_S : tileExt.sticky ? Textures.RS_EXT_OFF_S : Textures.RS_EXT_OFF;
					}
				}
			}
			else {
				if (tileExt.camou) {
					return tileExt.super_sticky ? Textures.CAMOU_EXT_SUPER_STICKY : tileExt.sticky ? Textures.CAMOU_EXT_STICKY : Textures.CAMOU_EXT; 
				}
				else return tileExt.super_sticky ? Textures.EXTENSION_SUPER_STICKY: tileExt.sticky ? Textures.EXTENSION_STICKY : Textures.EXTENSION_NORMAL;
			}
		}
		else if (meta == (side ^ 1)) {
			if (tileExt.redstone) {
				if (tileExt.isActive()) {
					return Textures.RS_EXT_ON;
				}
				else return Textures.RS_EXT_OFF;
			}
			else return Textures.EXTENSION_NORMAL;
		}
		else {
			if (tileExt.redstone) {
				if (tileExt.isActive()) {
					if (tileExt.getComp() != 0) {
						return Textures.RS_COMP_EXT_ON_SIDE_X + tileExt.getComp();
					}
					else return Textures.RS_EXT_SIDE_ON;
				}
				else {
					if (tileExt.getComp() != 0) {
						return Textures.RS_COMP_EXT_OFF_SIDE_X + tileExt.getComp();
					}
					return Textures.RS_EXT_SIDE_OFF;
				}
			}
			else return Textures.EXTENSION_SIDE;
		}
	}

	@Override
	public String getTexture(int meta, int side) {
		return getTextureIndex(null, meta, side);
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		int facing = WorldUtil.getBlockFacing(world, x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]);
		if (facing == side) {
			return super.shouldSideBeRendered(world, x, y, z, side);
		}
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
	public int getRenderID() {
		return PRenderManager.getRenderID(PRenderManager.extensionRenderer);
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, int side) {
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		return facing == side;
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public PTile createPTile() {
		return new TileExtension();
	}
	
	@Override
	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		
		switch (meta & 7) {
		case 0: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.375D, 0.25D, 0.375D, 0.625D, 1.0D, 0.625D)); break;
		case 1: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.375D, 0.0D, 0.375D, 0.625D, 0.75D, 0.625D)); break;
		case 2: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.375D, 0.375D, 0.25D, 0.625D, 0.625D, 1.0D)); break;
		case 3: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 0.75D)); break;
		case 4: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.25D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D)); break;
		case 5: boxes.add(AxisAlignedBB.getBoundingBox(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F));
				boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.375D, 0.375D, 0.75D, 0.625D, 0.625D)); break;
		default: return null;
		}
		
		return boxes;
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 playerPos, Vec3 targetPos) {
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		ArrayList<Byte> subHitMap = new ArrayList<Byte>();
		subHitMap.add((byte) 0);
		subHitMap.add((byte) 1);
		ArrayList<AxisAlignedBB> boxes = this.getBoxes(world, x, y, z, facing);
		return CollisionUtil.multiCollisionRayTraceWithSubHitMap(world, x, y, z, playerPos, targetPos, boxes, subHitMap);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		PTile tileRaw = WorldUtil.getPTile(world, x, y, z);
		if (tileRaw != null && tileRaw instanceof TileExtension) {
			TileExtension tile = (TileExtension) tileRaw;
			if (tile.isConductive()) {
				BlockProxy proxy = new BlockProxy(world, x, y, z);
				int input = proxy.getPowerInputFrom(proxy.getFacing());
				
				if (tile.getComp() != 0 && tile.getComp() != input) {
					input = 0;
				}
				
				PistonSystem system = new PistonSystem(proxy, 0, 0, SystemType.REDSTONE);
				
				if (tile.isInput() && input < tile.getStrength() || input > tile.getStrength()) {
					system.setToInput(proxy, input);
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
		if (WorldUtil.getBlockFacing(world, x, y, z) == (side ^ 1)) {
			PTile tileRaw = WorldUtil.getPTile(world, x, y, z);
			if (tileRaw != null && tileRaw instanceof TileExtension) {
				TileExtension tile = (TileExtension) tileRaw;
				if (!tile.isInput()) {
					if (tile.getComp() != 0 && tile.getStrength() != tile.getComp()) return 0;
					return tile.getStrength();
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
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		boolean inMachine = false;
		
		PTile tileRaw = WorldUtil.getPTile(world, x, y, z);
		if (tileRaw != null && tileRaw instanceof TileElementHolder) {
			tileRaw = ((TileElementHolder) tileRaw).getPElementTile();
			inMachine = true;
		}
		
		TileExtension tile = (TileExtension) tileRaw;
		
		ItemStack stack = player.getCurrentEquippedItem();
		
		if (stack == null) return false;
		
		if (tile != null) {
			if (tile.redstone) {
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
		
		if (tile != null && ItemReference.isOre(stack, "slabWood")) {
			
			int partFacing = FacingUtil.getFacingFromClick(facing, xHit, yHit, zHit);
			if (facing == (partFacing ^ 1)) {
				toPartblock(world, x, y, z);
				TilePartblock tileP = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
				tileP.setPart(BlockData.extensionPart, partFacing);
				((TileExtension) tileP.getTile(partFacing)).redstone = tile.redstone;
			}
			
			return true;
		}
		
		if (!inMachine && CompareUtil.compareIDs(stack, ItemData.saw.item)) {
			
			int partFacing = FacingUtil.getFacingFromClick(facing, xHit, yHit, zHit);
			
			toPartblock(world, x, y, z);
			
			if (!world.isRemote) {
				if (!player.capabilities.isCreativeMode) {
					
					ItemStack drop;
					
					if (facing == partFacing) {
						drop = BlockItemUtil.getStack(BlockData.extensionPart);
						if (tile != null) drop.stackTagCompound = tile.getNBTForItem();
					}
					else {
						drop = BlockItemUtil.getStack(BlockData.rodPart);
						if (tile != null) drop.stackTagCompound = TileRod.getNBTForItem(tile.getNBTForItem());
					}
					
					WorldUtil.spawnItemStack(world, x, y, z, drop);
				}
				
				TilePartblock tilePB = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
				
				tilePB.setPart(null, partFacing);
				
				PacketHandler.sendToAllInDimension(new SawPacket(x, y, z, partFacing, player), player.dimension);
			}
			return true;
		}

		Item item = stack.getItem();
		
		if (!inMachine && tile != null && item != null && (item instanceof IBaseItem)) {
			if (side != facing && side != (facing ^ 1)) {
				PItem pItem = ((IBaseItem) item).getData();
				if (pItem != null && pItem instanceof IPart && ((IPart) pItem).canPartBeAdded(world, x, y, z, side)) {
			
					toPartblock(world, x, y, z);
					
					TilePartblock partblockTile = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
				 
					if (partblockTile != null) {
						partblockTile.setPart(((IPart) pItem).getPartBlock(), side);
					
						PTile partTile = partblockTile.getTile(side);
						if (partTile != null && stack.stackTagCompound != null) {
							partTile.readFromNBT(stack.stackTagCompound);
						}
						
						if (!player.capabilities.isCreativeMode) stack.stackSize--;
					}
					
					WorldUtil.updateBlock(world, x, y, z);
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
					
					WorldUtil.updateBlock(world, x, y, z);
					return true;
				}
			}
		}
		
		if (this.handleGUILCrafting(world, x, y, z, player, side, tile, facing, stack)) {
			if (!world.isRemote) {
				int dimID = player.dimension;
				PacketHandler.sendToAllInDimension(new RodExDataPacket(tile, dimID, inMachine), dimID);
			}
			return true;
		}
		else return false;
	}
	
	private void toPartblock(World world, int x, int y, int z) {
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		TileExtension tile = (TileExtension) WorldUtil.getPTile(world, x, y, z);
		WorldUtil.setBlock(world, x, y, z, BlockData.partBlock.block);
		TilePartblock tilePB = (TilePartblock) WorldUtil.getPTile(world, x, y, z);
		tilePB.setPart(BlockData.extensionPart, facing);
		tilePB.setPart(BlockData.rodPart, facing ^ 1);
		((TileRod) tilePB.getTile(facing ^ 1)).redstone = tile.redstone;
		tilePB.setTile(tile, facing);
	}
	
	protected boolean handleGUILCrafting(World world, int x, int y, int z,
			EntityPlayer player, int side, TileExtension tile, int facing, ItemStack stack) {
		
		if (tile == null) return false;
		
		if (facing == side) {
			if (!tile.super_sticky && !tile.sticky && tile.redstone && (CompareUtil.compareIDs(stack, ItemData.redioSuperGlue.item))) {
				tile.sticky = true;
				tile.super_sticky = true;
				tile.redio = true;
				if (!player.capabilities.isCreativeMode) stack.stackSize--;
				return true;
			}
			if (!tile.super_sticky && !tile.sticky && tile.redstone && (CompareUtil.compareIDs(stack, ItemData.redioGlue.item))) {
				tile.sticky = true;
				tile.redio = true;
				if (!player.capabilities.isCreativeMode) stack.stackSize--;
				return true;
			}
			if (!tile.super_sticky && !tile.sticky && (CompareUtil.compareIDs(stack, ItemData.super_glue.item))) {
				tile.sticky = true;
				tile.super_sticky = true;
				if (!player.capabilities.isCreativeMode) stack.stackSize--;
				return true;
			}
			if (!tile.super_sticky && !tile.sticky && (CompareUtil.compareIDs(stack, ItemReference.SLIME) || CompareUtil.compareIDs(stack, ItemData.glue.item))) {
				tile.sticky = true;
				if (!player.capabilities.isCreativeMode) stack.stackSize--;
				return true;
			}
			if (!tile.super_sticky && tile.sticky && ItemReference.isDye(stack, 14)) {
				tile.super_sticky = true;
				if (!player.capabilities.isCreativeMode) stack.stackSize--;
				return true;
			}
			if (!tile.camou && CompareUtil.compareIDs(stack, ItemData.camoupaste.item)) {
				tile.camou = true;
				if (!player.capabilities.isCreativeMode) stack.stackSize--;
				return true;
			}
			Block block = BlockItemUtil.getBlockFromStack(stack);
			if (block != null && tile.camou && block.getRenderType() == 0) {
				tile.camouID = BlockItemUtil.getBlockID(block);
				tile.camouMeta = stack.getItemDamage();
				return true;
			}
			if (CompareUtil.compareIDs(stack, ItemData.spade.item)) {
				if (tile.camou && tile.camouID != -1) {
					tile.camouID = -1;
					tile.camouMeta = 0;
					return true;
				}
				if (tile.camou) {
					tile.camou = false;
					tile.camouID = -1;
					tile.camouMeta = 0;
					if (!world.isRemote && !player.capabilities.isCreativeMode) {
						WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.camoupaste));
					}
					return true;
				}
				if (tile.super_sticky) {
					tile.super_sticky = false;
					tile.sticky = false;
					if (!world.isRemote && !player.capabilities.isCreativeMode) {
						if (tile.redio) WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.redioSuperGlue));
						else WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.super_glue));
					}
					tile.redio = false;
					return true;
				}
				if (tile.sticky) {
					tile.sticky = false;
					if (!world.isRemote && !player.capabilities.isCreativeMode) {
						if (tile.redio) WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.redioGlue));
						else WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.glue));
					}
					tile.redio = false;
					return true;
				}
				if (tile.redstone) {
					tile.redstone = false;
					tile.setComp(0);
					if (!world.isRemote && !player.capabilities.isCreativeMode) {
						if (this instanceof BExtensionPart) {
							WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.pileOfRedstone));
						}
						else {
							WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemReference.REDSTONE_ITEM));
						}
					}
					return true;
				}
			}
		}
		
		if (CompareUtil.compareIDs(stack, ItemData.spade.item)) {
			if (tile.redstone) {
				tile.redstone = false;
				tile.setComp(0);
				if (!world.isRemote && !player.capabilities.isCreativeMode) {
					if (this instanceof BExtensionPart) {
						WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.pileOfRedstone));
					}
					else {
						WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemReference.REDSTONE_ITEM));
					}
					if (tile.redio) {
						if (tile.super_sticky) {
							WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.redioSuperGlue));
						}
						else {
							WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.redioGlue));
						}
					}
				}
				if (tile.redio) {
					tile.redio = false;
					tile.sticky = false;
					tile.super_sticky = false;
				}
				return true;
			}
		}
		
		if (!tile.redstone) {
			if (ItemReference.isRedstone(stack)) {
				tile.redstone = true;
				if (!player.capabilities.isCreativeMode) stack.stackSize--;
				return true;
			}
			if (stack.stackSize >= 2 && CompareUtil.compareIDs(stack, ItemData.pileOfRedstone.item)) {
				tile.redstone = true;
				if (!player.capabilities.isCreativeMode) stack.stackSize -= 2;
				return true;
			}
		}
		if (tile.redstone && CompareUtil.compareIDs(stack, ItemReference.COMPARATOR)) {
			tile.incrementComp();
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean getSubBlocks(int itemID, CreativeTabs tab, List list) {

        if (!ConfigData.showAllExtensions) {
            ItemStack stack;

            stack = BlockItemUtil.getStack(this);
            stack.stackTagCompound = getDefaultNBT();
            list.add(stack);

            stack = BlockItemUtil.getStack(this);
            stack.stackTagCompound = getDefaultNBT();
            stack.stackTagCompound.setBoolean("sticky", true);
            list.add(stack);

            stack = BlockItemUtil.getStack(this);
            stack.stackTagCompound = getDefaultNBT();
            stack.stackTagCompound.setBoolean("sticky", true);
            stack.stackTagCompound.setBoolean("super_sticky", true);
            list.add(stack);
        }
        else {
            ArrayList<ItemStack> stacks = ExtensionUtil.getExtensionStacksWith(true, true, true, true, true);
            list.addAll(stacks);
        }

		return true;
	}
	
	@Override
	public PItem getItemBlock() {
		return ItemData.extension;
	}
	
	public static NBTTagCompound getDefaultNBT() {
		NBTTagCompound nbt = NBTUtil.getNewCompound();
		
		nbt.setBoolean("sticky", false);
		nbt.setBoolean("super_sticky", false);
		nbt.setBoolean("redstone", false);
		nbt.setBoolean("camou", false);
		nbt.setInteger("camouID", -1);
		nbt.setInteger("camouMeta", 0);
        nbt.setBoolean("redio", false);
		
		return nbt;
	}
	
//	@Override
//	public int damageDropped(int damage) {
//		return damage;
//	}
//
//	@Override
//	public ItemStack getDroppedStack(PTile tile, int meta) {
//		if (tile != null && tile instanceof TileExtension) {
//			TileExtension tileE = (TileExtension) tile;
//			int damage = 0;
//			damage = damage | (tileE.sticky ? 1 : 0);
//			damage = damage << 1;
//			damage = damage | (tileE.super_sticky ? 1 : 0);
//			damage = damage << 1;
//			damage = damage | (tileE.redstone ? 1 : 0);
//			damage = damage << 1;
//			damage = damage | (tileE.camou ? 1 : 0);
//			damage = damage << 1;
//			ItemStack result = super.getDroppedStack(tile, meta);
//			result.setItemDamage(damage);
//			System.out.println("setting damage to " + damage);
//			return result;
//		}
//		
//		return null;
//	}
	
	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, boolean strongConnection) {
		
		if (system.getSystemType() == SystemType.REDSTONE && !isTransmitter(proxy)) return;
		
		system.addElement(proxy, strongConnection);
		
		if (system.getSystemType() == SystemType.REDSTONE && isTransmitter(proxy)) {
			RedstoneUtil.connectAdjacentExtensions(system, proxy);
		}
		
		int facing = proxy.getFacing();
		
		BlockProxy neighbor = proxy.getNeighbor(facing ^ 1);
		if (neighbor.connectsToSide(facing, system.getSystemType())) {
			neighbor.getConnectedForSystem(system, false);
		}
		
		getConnectedForSystem(system, proxy, facing);
	}
	
	protected void getConnectedForSystem(PistonSystem system, BlockProxy proxy, int facing) {
		
		PTile pTile = proxy.getPTile();
		
		int dir = system.getDir();
		int antiDir = dir ^ 1;
		
		if (pTile != null && pTile instanceof TileElementHolder) {
			pTile = ((TileElementHolder) pTile).getPElementTile();
		}
		if (pTile != null && pTile instanceof TilePartblock) {
			pTile = ((TilePartblock) pTile).getTile(facing);
		}
		
		if (pTile != null && pTile instanceof TileExtension) {
			TileExtension tile = (TileExtension) pTile;
			BlockProxy neighbor = proxy.getNeighbor(facing);
			
			if (system.getSystemType() == SystemType.REDSTONE) {
				PTile neighborTile = neighbor.getPTile();
				// Don't transmit signals "through" pistons/rotators/creativeMachine //
				if (neighborTile != null && neighborTile instanceof ITransmitter 
						&& !(neighborTile instanceof TileMechPiston) 
						&& !(neighborTile instanceof TileMechRotator)
						&& !(neighborTile instanceof TileCreativeMachine)) {
					if (((ITransmitter) neighborTile).isConductive()) {
						neighbor.getConnectedForSystem(system, true);
					}
				}
			}
			else if (facing == system.getDir() && system.getSystemType() == SystemType.MOVE) {
				for (int i = 0; i < 12; i++) {
					if (neighbor.isSolid()) {
						if (neighbor.isPistonElement()) {
							int eFacing = neighbor.getFacing();
							//
							// Special RodFolder/Machine behavior! 
							//
							if (neighbor.getPBlock() instanceof BRodFolder) {
								if (eFacing == antiDir && system.isInsertable(neighbor, neighbor.getNeighbor(antiDir))) {
									 neighbor.getConnectedForSystem(system, false);
									 break; // stopping the loop if rodFolder accepts element
								}
								else neighbor.getConnectedForSystem(system, true);
							}
							else if (neighbor.getPBlock() instanceof BElementMachine) {
								if ((eFacing == antiDir || eFacing == dir) && system.isInsertable(neighbor, neighbor.getNeighbor(antiDir))) {
									 neighbor.getConnectedForSystem(system, false);
								}
								else neighbor.getConnectedForSystem(system, true);
							}
							else neighbor.getConnectedForSystem(system, true);
						}
						else system.addElement(neighbor, true);
						
						neighbor = neighbor.getNeighbor(facing);
					}
					else return;
				}
			}
			else if ((tile.super_sticky || (tile.sticky && facing == (system.getDir() ^ 1))) 
					&& (!tile.redio || tile.isActive())) {
				if (neighbor.isPistonElement()) {
					//
					// Special Machine behavior! 
					//
					if (neighbor.getPBlock() instanceof BElementMachine && neighbor.getFacing() == (facing ^ 1)) {
						neighbor.getConnectedForSystem(system, false);
					}
					else {
						neighbor.getConnectedForSystem(system, true);
					}
				}
				else if (neighbor.isSolid()) system.addElement(neighbor, true);
			}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
		
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		facing = RotateUtil.rotateDir(facing, rotateDir);
		WorldUtil.setBlockFacing(world, x, y, z, facing);
	}
	
	@Override
	public ItemStack getDroppedStack(PTile tile, int meta) {
		ItemStack stack = BlockItemUtil.getStack(BlockData.extension.block);
		if (tile != null && tile instanceof TileExtension) {
			stack.stackTagCompound = ((TileExtension) tile).getNBTForItem();
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
	public boolean isTransmitter(BlockProxy proxy) {
		PTile tile = proxy.getPTile();
		if (tile != null && tile instanceof TileElementHolder) {
			tile = ((TileElementHolder) tile).getPElementTile();
		}
		if (tile != null && tile instanceof TileExtension)
			return ((TileExtension) tile).isConductive();
		else return false;
	}
}
