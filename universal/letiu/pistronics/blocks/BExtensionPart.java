package letiu.pistronics.blocks;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
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
import letiu.pistronics.render.simple.PartRenderer;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.ExtensionUtil;
import letiu.pistronics.util.FacingUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BExtensionPart extends BExtension implements IPart {

	public BExtensionPart() {
		super();
		this.name = "Extension Part Block";
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		
		if (PartRenderer.metaForRender != -1) {
			meta = PartRenderer.metaForRender;
		}
		
		// TODO: CLEAN THIS UP!
		
		if (tile != null && tile instanceof TilePartblock && side != meta && side != (meta ^ 1)) {
			
			TileExtension partTile = (TileExtension) ((TilePartblock) tile).getTile(meta);
			if (partTile == null) {
				return super.getTextureIndex(tile, meta, side);
			}
			
			int texOffset = partTile.redstone ? (partTile.isActive() ? 6 : 3) : 0;
			
			PBlock part = ((TilePartblock) tile).getPart(side);
			if (part != null && part instanceof BExtensionPart) {
				return this.textures[15];
			}
			PBlock partLeft = ((TilePartblock) tile).getPart(FacingUtil.getLeftCross(side, meta));
			PBlock partRight = ((TilePartblock) tile).getPart(FacingUtil.getLeftCross(side, meta ^ 1));
			if (partLeft != null && partLeft instanceof BExtensionPart) {
				if (partRight != null && partRight instanceof BExtensionPart) {
					return this.textures[16 + texOffset];
				}
				return this.textures[18 + texOffset];
			}
			if (partRight != null && partRight instanceof BExtensionPart) {
				return this.textures[17 + texOffset];
			}
		}
		
		if (tile != null && tile instanceof TilePartblock && meta != -1) {
			TileExtension partTile = (TileExtension) ((TilePartblock) tile).getTile(meta);
			if (partTile != null) {
				return super.getTextureIndex(partTile, meta, side); 
			}
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
		
		switch (meta & 7) {
		case 0: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.375D, 0.25D, 0.375D, 0.625D, 0.375D, 0.625D)); break;
		case 1: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.375D, 0.625D, 0.375D, 0.625D, 0.75D, 0.625D)); break;
		case 2: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.375D, 0.375D, 0.25D, 0.625D, 0.625D, 0.375D)); break;
		case 3: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.375D, 0.375D, 0.625D, 0.625D, 0.625D, 0.75D)); break;
		case 4: boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D));
				boxes.add(AxisAlignedBB.getBoundingBox(0.25D, 0.375D, 0.375D, 0.375D, 0.625D, 0.625D)); break;
		case 5: boxes.add(AxisAlignedBB.getBoundingBox(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F));
				boxes.add(AxisAlignedBB.getBoundingBox(0.625D, 0.375D, 0.375D, 0.75D, 0.625D, 0.625D)); break;
		default: return null;
		}
		
		return boxes;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		WorldUtil.setBlock(world, x, y, z, BlockData.partBlock.block);
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		
		if (tile != null && tile instanceof TilePartblock) {
			int partPos = WorldUtil.getFacing(world, x, y, z, player);
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
		if (tile != null && tile instanceof TilePartblock) {;
			tile = ((TilePartblock) tile).getTile(part);
		}
		else {
			part = WorldUtil.getBlockFacing(world, x, y, z);
		}
		
		if (tile == null) return false;
		
		if (tile instanceof TileExtension) {
			if (!((TileExtension) tile).redstone) {
				if (ItemReference.isRedstone(stack)) {
					((TileExtension) tile).redstone = true;
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					
					if (!world.isRemote && !player.capabilities.isCreativeMode) {
						WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.pileOfRedstone));
					}
					
					//if (!world.isRemote) WorldUtil.toggltBit4(world, x, y, z);
					return true;
				}
				if (CompareUtil.compareIDs(stack, ItemData.pileOfRedstone.item)) {
					((TileExtension) tile).redstone = true;
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					
					//if (!world.isRemote) WorldUtil.toggltBit4(world, x, y, z);
					return true;
				}
			}
		}
		
		if (this.handleGUILCrafting(world, x, y, z, player, side, (TileExtension) tile, part, stack)) {
			//if (!world.isRemote) WorldUtil.toggltBit4(world, x, y, z);
			return true;
		}
		else return false;
	}

	@Override
	public boolean connectsToSide(BlockProxy proxy, TilePartblock tile, int part, int side, SystemType type) {
		return false;
	}

	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy,
			TilePartblock tile, int part) {
		
		super.getConnectedForSystem(system, proxy, part);
	}
	
	@Override
	public boolean getSubBlocks(int itemID, CreativeTabs tab, List list) {
        if (!ConfigData.showAllExtensionParts) {
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
            for (ItemStack stack : stacks) {
                ItemStack part = BlockItemUtil.getStack(this);
                part.stackTagCompound = (NBTTagCompound) stack.stackTagCompound.copy();
                list.add(part);
            }
        }

		return true;
	}
	
	@Override
	public ItemStack getDroppedStack(PTile tile, int meta) {
		ItemStack stack = BlockItemUtil.getStack(BlockData.extensionPart.block);
		if (tile != null && tile instanceof TileExtension) {
			stack.stackTagCompound = ((TileExtension) tile).getNBTForItem();
		}
		return stack;
	}

	@Override
	public PBlock getPartBlock() {
		return this;
	}
	
	@Override
	public PItem getItemBlock() {
		return ItemData.extensionPart;
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
