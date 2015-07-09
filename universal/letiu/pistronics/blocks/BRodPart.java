package letiu.pistronics.blocks;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.render.simple.PartRenderer;
import letiu.pistronics.tiles.ITransmitter;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.util.BlockProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BRodPart extends BRod implements IPart {

	public BRodPart() {
		super();
		this.name = "Rod Part Block";
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		if (PartRenderer.metaForRender != -1) {
			meta = PartRenderer.metaForRender;
		}
		
		if (tile != null && tile instanceof TilePartblock) {
			tile = ((TilePartblock) tile).getTile(meta);
		}
		
		return super.getTextureIndex(tile, meta, side);
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
	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		
		AxisAlignedBB box = null;
		switch (meta & 7) {
		case 1: box = AxisAlignedBB.getBoundingBox(0.375D, 0.625D, 0.375D, 0.625D, 1D, 0.625D); break;
		case 0: box = AxisAlignedBB.getBoundingBox(0.375D, 0.0D, 0.375D, 0.625D, 0.375D, 0.625D); break;
		case 5: box = AxisAlignedBB.getBoundingBox(0.625D, 0.375D, 0.375D, 1D, 0.625, 0.625D); break;
		case 4: box = AxisAlignedBB.getBoundingBox(0D, 0.375D, 0.375D, 0.375D, 0.625D, 0.625D); break;
		case 3: box = AxisAlignedBB.getBoundingBox(0.375D, 0.375D, 0.625D, 0.625D, 0.625D, 1D); break;
		case 2: box = AxisAlignedBB.getBoundingBox(0.375D, 0.375D, 0D, 0.625D, 0.625D, 0.375D); break;
		}
		if (box == null) return null; 
		
		boxes.add(box);
		return boxes;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		WorldUtil.setBlock(world, x, y, z, BlockData.partBlock.block);
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		
		if (tile != null && tile instanceof TilePartblock) {
			int partPos = WorldUtil.getFacing(world, x, y, z, player) ^ 1;
			((TilePartblock) tile).setPart(this, partPos);
			
			if (stack.stackTagCompound != null) {
				((TilePartblock) tile).getTile(partPos).readFromNBT(stack.stackTagCompound);
			}
		}
	}
	

	@Override
	public boolean onPartActivated(World world, int x, int y, int z, EntityPlayer player, int part, int side) {
		
		ItemStack stack = player.getCurrentEquippedItem();
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null && tile instanceof TilePartblock) {
			int facing = part;
			
			if (ItemReference.isOre(stack, "slabWood")) {
				TilePartblock tileP = (TilePartblock) tile;
				boolean redstone = ((TileRod) tileP.getTile(facing)).redstone;
				tileP.setPart(BlockData.extensionPart, facing);
				((TileExtension) tileP.getTile(facing)).redstone = redstone;
				
				WorldUtil.toggltBit4(world, x, y, z);
				if (!player.capabilities.isCreativeMode) stack.stackSize--;
				return true;
			}
			
			
			PTile partTile = ((TilePartblock) tile).getTile(facing);
			if (partTile != null && partTile instanceof TileRod) {
				if (ItemReference.isRedstone(stack)) {
					
					((TileRod) partTile).redstone = true;
					WorldUtil.toggltBit4(world, x, y, z);
					
					if (!world.isRemote && !player.capabilities.isCreativeMode) {
						WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.pileOfRedstone));
					}
					
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					return true;
				}
				if (CompareUtil.compareIDs(stack, ItemData.pileOfRedstone.item)) {
					((TileRod) partTile).redstone = true;
					WorldUtil.toggltBit4(world, x, y, z);
					
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					return true;
				}
				if (CompareUtil.compareIDs(stack, ItemData.spade.item)) {
					if (((TileRod) partTile).redstone) {
						((TileRod) partTile).redstone = false;
						if (!world.isRemote && !player.capabilities.isCreativeMode) {
							if (this instanceof BRodPart) {
								WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.pileOfRedstone));
							}
							else {
								WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemReference.REDSTONE_ITEM));
							}
						}
						WorldUtil.toggltBit4(world, x, y, z);
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, TilePartblock tile, int part) {
		
		if (system.getSystemType() == SystemType.REDSTONE && !((ITransmitter) tile.getTile(part)).isConductive()) return;
		
		BlockProxy neighbor = proxy.getNeighbor(part);
		if (neighbor.connectsToSide(part ^ 1, system.getSystemType())) {
			neighbor.getConnectedForSystem(system, false);
		}
	}

	@Override
	public boolean connectsToSide(BlockProxy proxy, TilePartblock tile, int part, int side, SystemType type) {

		if (type == SystemType.REDSTONE) {
			PTile elementTile = tile.getTile(part);
			if (elementTile != null && elementTile instanceof ITransmitter) {
				return ((ITransmitter) elementTile).isConductive();
			}
			return false;
		}
		else return true;
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
	
	@Override
	public ItemStack getDroppedStack(PTile tile, int meta) {
		ItemStack stack = BlockItemUtil.getStack(BlockData.rodPart);
		if (tile != null && tile instanceof TileRod) {
			stack.stackTagCompound = ((TileRod) tile).getNBTForItem();
		}
		return stack;
	}

	@Override
	public PBlock getPartBlock() {
		return this;
	}
	
	@Override
	public PItem getItemBlock() {
		return ItemData.rodPart;
	}

	@Override
	public void onPartPlaced(TilePartblock tilePartblock, PTile tile, int part,
			int partClicked, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRotate(TilePartblock tilePartblock, PTile tile, int part,
			int rotateDir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canPartBeAdded(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean canPartStay(IBlockAccess world, int x, int y, int z, int side) {
		// TODO Auto-generated method stub
		return true;
	}
}
