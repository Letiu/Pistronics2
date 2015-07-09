package letiu.pistronics.itemblocks;

import java.util.List;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BRod;
import letiu.pistronics.blocks.BRodPart;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.IPartCompound;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItemBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.util.FacingUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BIRod extends PItemBlock implements IPartCompound {

	@Override
	public String getIcon(ItemStack stack, int pass) {
		
		NBTTagCompound nbt = stack.stackTagCompound;
		
		if (nbt == null) return Textures.ROD_SIDE;
		
		if (pass == 2 || pass == 3) {
			return Textures.ROD_ENDS;
		}
		else {
			if (nbt.getBoolean("redstone")) {
				return Textures.ROD_REDSTONE;
			}
			else {
				return Textures.ROD_SIDE;
			}
		}
	}
	
	@Override
	public PItemRenderer getSpecialRenderer() {
		return PRenderManager.rodItemRenderer;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt != null) {
			if (nbt.getBoolean("redstone")) list.add(EnumChatFormatting.DARK_RED + "Redstone");
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		
		return super.onItemRightClick(stack, world, player);
	}
	
	@Override
	public boolean overwritesPlaceBlockAt() {
		return true;
	}
	
	@Override
	public boolean canBlockTrigger(World world, int x, int y, int z, int side,
			EntityPlayer player, ItemStack stack) {
		
		//PBlock block = WorldUtil.getPBlock(world, x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side]);
		
		return true; //block instanceof BPartblock;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side, float xHit, float yHit, float zHit) {
	
		x = x + Facing.offsetsXForSide[side];
		y = y + Facing.offsetsYForSide[side];
		z = z + Facing.offsetsZForSide[side];
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		
		if (tile != null) {
			
			if (tile instanceof TileRod) {
				BlockData.rod.onBlockActivated(world, x, y, z, player, side ^ 1, xHit, yHit, zHit);
			}
			else if (tile instanceof TileExtension) {
				BlockData.extension.onBlockActivated(world, x, y, z, player, side ^ 1, xHit, yHit, zHit);
			}
			else if (tile instanceof TilePartblock) {
//				TilePartblock partTile = (TilePartblock) tile;
//				if (partTile.getPart(side) == null && partTile.getPart(side ^ 1) == null) {
					BlockData.partBlock.onBlockActivated(world, x, y, z, player, side ^ 1, xHit, yHit, zHit);
//				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ, int metadata) {	
		
		WorldUtil.setBlock(world, x, y, z, BlockItemUtil.getBlockFromStack(stack));
		
		int facing = -1;
		
		if (!player.isSneaking() && ConfigData.predictPlacement) {
			
			PTile clickedTile = WorldUtil.getPTile(world, x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]);
			
			if (clickedTile != null) {
				if (clickedTile instanceof TileRod) facing = side;
				else if (clickedTile instanceof TileExtension) facing = side;
				else if (clickedTile instanceof TilePartblock) {
					PBlock part = ((TilePartblock) clickedTile).getPart(side);
					if (part instanceof BRodPart) facing = side;
				}
				else if (clickedTile instanceof TileMech) {
					if (side == WorldUtil.getBlockFacing(clickedTile)
						&& ((TileMech) clickedTile).getPElement() instanceof BRod) {
						facing = side;
					}
				}
			}
		}
		
		if (facing == -1) {
			WorldUtil.setBlockFacing(world, x, y, z, player);
		}
		else {
			WorldUtil.setBlockFacing(world, x, y, z, facing);
		}
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		
		if (tile != null && stack.stackTagCompound != null) {
			tile.readFromNBT(stack.stackTagCompound);
		}
		
		return true;
	}
	
	@Override
	public IPart getPart(int facing, int side) {
		int i = FacingUtil.rotateSideTo(side, facing);
		if (i == 0 || i == 1) return (IPart) BlockData.rodPart;
		else return null;
	}
}
