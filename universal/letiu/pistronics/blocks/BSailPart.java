package letiu.pistronics.blocks;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.render.simple.PartRenderer;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.tiles.TileSail;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.FacingUtil;
import letiu.pistronics.util.SailUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BSailPart extends PBlock implements IPart {

	public static final float SAIL_A = 0.47F;
	public static final float SAIL_B = 0.53F;
	
	public BSailPart() {
		super();
		this.name = "Sail Part";
		this.material = "cloth";
		this.hardness = 3F;
		this.resistance = 10F;
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.CAMOU_SAIL;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		if (PartRenderer.metaForRender != -1) {
			meta = PartRenderer.metaForRender;
		}
		
		if (tile != null && tile instanceof TilePartblock) {
			tile = ((TilePartblock) tile).getTile(meta);
		}
		
		int color = 0;
		
		if (tile != null && tile instanceof TileSail) {
			
			TileSail sailTile = (TileSail) tile;
			
			if (sailTile.camou) {
				if (sailTile.camouID != -1) {
					return BaseBlock.BLOCK_PREFIX + "x" + sailTile.camouID + "x" + sailTile.camouMeta;
				}
				else return Textures.CAMOU_SAIL;
			}
			
			color = sailTile.color;
		}
		
		return BaseBlock.BLOCK_PREFIX + "x" + BlockItemUtil.getBlockID(ItemReference.WOOL) + "x" + color;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		int facing;
		
		if (PartRenderer.metaForRender != -1) facing = PartRenderer.metaForRender;
		else facing = WorldUtil.getBlockFacing(world, x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]);
		
		if (facing == side) {
			return super.shouldSideBeRendered(world, x, y, z, side);
		}
		return true;
	}
	
	@Override
	public int getRenderID() {
		return PRenderManager.getRenderID(PRenderManager.sailRenderer);
	}
	
	@Override
	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			PTile tilePart = ((TilePartblock) tile).getTile(meta);
			if (tilePart != null && tilePart instanceof TileSail) {
				
				TileSail sailTile = (TileSail) tilePart;
				AxisAlignedBB box = null;
				
				if (sailTile.face == 0 || sailTile.face == 1) {
					if (meta == 2 || meta == 3) box = AxisAlignedBB.getBoundingBox(0.0F, SAIL_A, 0.0F, 1.0F, SAIL_B, 0.75F);
					else if (meta == 4 || meta == 5) box = AxisAlignedBB.getBoundingBox(0.0F, SAIL_A, 0.0F, 0.75F, SAIL_B, 1.0F);
				}
				else if (sailTile.face == 2 || sailTile.face == 3) {
					if (meta == 0 || meta == 1) box = AxisAlignedBB.getBoundingBox(0.0F, 0.0F, SAIL_A, 1.0F, 0.75F, SAIL_B);
					else if (meta == 4 || meta == 5) box = AxisAlignedBB.getBoundingBox(0.0F, 0.0F, SAIL_A, 0.75F, 1.0F, SAIL_B);
				}
				else if (sailTile.face == 4 || sailTile.face == 5) {
					if (meta == 0 || meta == 1) box = AxisAlignedBB.getBoundingBox(SAIL_A, 0.0F, 0.0F, SAIL_B, 0.75F, 1.0F);
					else if (meta == 2 || meta == 3) box = AxisAlignedBB.getBoundingBox(SAIL_A, 0.0F, 0.0F, SAIL_B, 1.0F, 0.75F);
				}
				
				if (box == null) return boxes;
				
				switch (meta) {
				case 1: box = box.getOffsetBoundingBox(0F, 0.25F, 0F); break;
				case 3: box = box.getOffsetBoundingBox(0F, 0F, 0.25F); break;
				case 5: box = box.getOffsetBoundingBox(0.25F, 0F, 0F); break;
				}
				
				boxes.add(box);
			}
		}
		
		return boxes;
	}
	

	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public PTile createPTile() {
		return new TileSail();
	}

	@Override
	public boolean onPartActivated(World world, int x, int y, int z, EntityPlayer player, int part, int side) {
		
		ItemStack stack = player.getCurrentEquippedItem();
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			int facing = part;
			
			TilePartblock tileP = (TilePartblock) tile;
			PTile partTile = tileP.getTile(part);
			if (partTile != null && partTile instanceof TileSail) {
				TileSail tileSail = (TileSail) partTile;
				int color = ItemReference.getStackDyeColor(stack);
				if (color != -1 && color != tileSail.color) {
					tileSail.color = color;
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					return true;
				}
				if (CompareUtil.compareIDs(stack, ItemData.camoupaste.item) && !tileSail.camou) { 
					tileSail.camou = true;
					tileSail.camouID = -1;
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					return true;
				}
				Block block = BlockItemUtil.getBlockFromStack(stack);
				if (block != null && tileSail.camou && block.getRenderType() == 0) {
					int blockID = BlockItemUtil.getBlockID(block);
					int blockMeta = stack.getItemDamage();
					if (blockID != tileSail.camouID || blockMeta != tileSail.camouMeta) {
						tileSail.camouID = BlockItemUtil.getBlockID(block);
						tileSail.camouMeta = stack.getItemDamage();
						return true;
					}
				}
				if (CompareUtil.compareIDs(stack, ItemData.spade.item)) {
					if (tileSail.camou && tileSail.camouID != -1) {
						tileSail.camouID = -1;
						tileSail.camouMeta = 0;
						return true;
					}
					if (tileSail.camou) {
						tileSail.camou = false;
						tileSail.camouID = -1;
						tileSail.camouMeta = 0;
						if (!world.isRemote && !player.capabilities.isCreativeMode) {
							WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.camoupaste));
						}
						return true;
					}
				}
				if (side != part && side != (part ^ 1)) {
					if (tileP.getPart(side) instanceof BRodPart && tileP.getPart(side ^ 1) instanceof BRodPart) {
						tileSail.face = FacingUtil.getLeftCross(side, part);
					}
				}
			}
		}
		
		
			
			
//			
//			if (ItemReference.isOre(stack, "slabWood")) {
//				TilePartblock tileP = (TilePartblock) tile;
//				boolean redstone = ((TileRod) tileP.getTile(facing)).redstone;
//				tileP.setPart(BlockData.extensionPart, facing);
//				((TileExtension) tileP.getTile(facing)).redstone = redstone;
//				
//				WorldUtil.toggltBit4(world, x, y, z);
//				if (!player.capabilities.isCreativeMode) stack.stackSize--;
//				return true;
//			}
//			
//			
//			PTile partTile = ((TilePartblock) tile).getTile(facing);
//			if (partTile != null && partTile instanceof TileRod) {
//				if (ItemReference.isRedstone(stack)) {
//					
//					((TileRod) partTile).redstone = true;
//					WorldUtil.toggltBit4(world, x, y, z);
//					
//					if (!world.isRemote && !player.capabilities.isCreativeMode) {
//						WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.pileOfRedstone));
//					}
//					
//					if (!player.capabilities.isCreativeMode) stack.stackSize--;
//					return true;
//				}
//				if (CompareUtil.compareIDs(stack, ItemData.pileOfRedstone.item)) {
//					((TileRod) partTile).redstone = true;
//					WorldUtil.toggltBit4(world, x, y, z);
//					
//					if (!player.capabilities.isCreativeMode) stack.stackSize--;
//					return true;
//				}
//				if (CompareUtil.compareIDs(stack, ItemData.spade.item)) {
//					if (((TileRod) partTile).redstone) {
//						((TileRod) partTile).redstone = false;
//						if (!world.isRemote && !player.capabilities.isCreativeMode) {
//							if (this instanceof BSailPart) {
//								WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.pileOfRedstone));
//							}
//							else {
//								WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemReference.REDSTONE_ITEM));
//							}
//						}
//						WorldUtil.toggltBit4(world, x, y, z);
//						return true;
//					}
//				}
//			}
//		}
		
		return true;
	}

	
	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, TilePartblock tile, int part) {
		
	}

	@Override
	public boolean connectsToSide(BlockProxy proxy, TilePartblock tile, int part, int side, SystemType type) {
		return false;
	}
	
	@Override
	public boolean getSubBlocks(int itemID, CreativeTabs tab, List list) {

        if (!ConfigData.showAllSails) {
            ItemStack stack;

            stack = BlockItemUtil.getStack(this);
            stack.stackTagCompound = getDefaultNBT();
            list.add(stack);
        }
        else {
            list.addAll(SailUtil.getStacksForCreativeInv());
        }
		  
		return true;
	}
	
	public static NBTTagCompound getDefaultNBT() {
		NBTTagCompound nbt = NBTUtil.getNewCompound();
		
		nbt.setInteger("color", 0);
        nbt.setBoolean("camou", false);
        nbt.setInteger("camouID", -1);
        nbt.setInteger("camouMeta", 0);

		return nbt;
	}

	@Override
	public ItemStack getDroppedStack(PTile tile, int meta) {
		ItemStack stack = BlockItemUtil.getStack(BlockData.sailPart);
		if (tile != null && tile instanceof TileSail) {
			stack.stackTagCompound = ((TileSail) tile).getNBTForItem();
		}
		return stack;
	}

	@Override
	public PBlock getPartBlock() {
		return this;
	}
	
	@Override
	public PItem getItemBlock() {
		return ItemData.sailPart;
	}

	@Override
	public String getTexture(int meta, int side) {
		return null;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return false;
	}
	
	@Override
	public boolean canPartBeAdded(IBlockAccess world, int x, int y, int z, int side) {
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null) {
			if (tile instanceof TileRod) return true;
			if (tile instanceof TilePartblock) {
				TilePartblock tilePartblock = (TilePartblock) tile;
				for (int i = 0; i < 6; i += 2) {
					if (i != side && i != (side ^ 1) 
						&& tilePartblock.getPart(i) instanceof BRodPart 
						&& tilePartblock.getPart(i ^ 1) instanceof BRodPart) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean canPartStay(IBlockAccess world, int x, int y, int z, int side) {
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			PTile partTile = ((TilePartblock) tile).getTile(side);
			if (partTile != null && partTile instanceof TileSail) {
				int rodDir = FacingUtil.getLeftCross(((TileSail) partTile).face, side);
				if (!(((TilePartblock) tile).getPart(rodDir) instanceof BRodPart) 
					|| !(((TilePartblock) tile).getPart(rodDir ^ 1) instanceof BRodPart)) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public void onPartPlaced(TilePartblock tilePartblock, PTile tile, int part,
			int partClicked, EntityPlayer player) {
		
		if (tile != null && tile instanceof TileSail) {
			
			if (partClicked == -1 || !(tilePartblock.getPart(partClicked) instanceof BRodPart)
					|| !(tilePartblock.getPart(partClicked ^ 1) instanceof BRodPart)) { 
				if (tilePartblock != null) {
					for (int i = 0; i < 6; i += 2) {
						if (i != part && i != (part ^ 1) 
							&& tilePartblock.getPart(i) instanceof BRodPart 
							&& tilePartblock.getPart(i ^ 1) instanceof BRodPart) {
							
							partClicked = i;
						}
					}
				}
				else partClicked = 0;
			}
			
			((TileSail) tile).face = FacingUtil.getLeftCross(partClicked, part);
		}
	}

	@Override
	public void postRotate(TilePartblock tilePartblock, PTile tile, int part, int rotateDir) {
		if (tile != null && tile instanceof TileSail) {
			TileSail sailTile = (TileSail) tile;
			sailTile.face = FacingUtil.rotate(sailTile.face, rotateDir);
		}
	}


}
