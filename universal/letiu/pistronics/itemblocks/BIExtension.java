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
import letiu.pistronics.data.PTile;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.util.FacingUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import letiu.pistronics.data.PItemBlock;

public class BIExtension extends PItemBlock implements IPartCompound {

	private static TileExtension tile = new TileExtension();

    @Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt != null) {
			if (nbt.getBoolean("super_sticky")) list.add(EnumChatFormatting.RED + "Super Sticky");
			else if (nbt.getBoolean("sticky")) list.add(EnumChatFormatting.GREEN + "Sticky");
			
			if (nbt.getBoolean("redio")) list.add(EnumChatFormatting.DARK_RED + "Redioactive");
			else if (nbt.getBoolean("redstone")) list.add(EnumChatFormatting.DARK_RED + "Redstone");
			
			if (nbt.getBoolean("camou")) {
				String tex = "None";
				int camouID = nbt.getInteger("camouID");
				if (camouID != -1) {
					Block block = BlockItemUtil.getBlockByID(camouID);
					tex = block.getLocalizedName();
				}
				
				list.add(EnumChatFormatting.AQUA + "Camou: " + tex);
			}
		}
	}
	
	@Override
	public int getSpriteNumber() {
		return 0;
	}
	
	@Override
	public String getIcon(ItemStack stack, int pass) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt == null) return Textures.EXTENSION_NORMAL;
		
		tile.readFromNBT(nbt);
		return BlockData.extension.getTextureIndex(tile, 3, pass);
	}
	
	@Override
	public PItemRenderer getSpecialRenderer() {
		return PRenderManager.extensionItemRenderer;
	}
	
	@Override
	public boolean overwritesPlaceBlockAt() {
		return true;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ, int metadata) {	
		
		WorldUtil.setBlock(world, x, y, z, BlockData.extension.block);
		
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
		int i = FacingUtil.rotateSideTo(facing, side);
		if (i == 0) return (IPart) BlockData.extensionPart;
		else if (i == 1) return (IPart) BlockData.rodPart;
		else return null;
	}
	
}
