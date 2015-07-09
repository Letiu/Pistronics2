package letiu.pistronics.itemblocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.blocks.BRod;
import letiu.pistronics.blocks.BRodPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.util.BlockProxy;

public class BIRodPart extends BIRod implements IPart {

		@Override
		public void getConnectedForSystem(PistonSystem system, BlockProxy proxy,
				TilePartblock tile, int part) {
			((BRodPart) BlockData.rodPart).getConnectedForSystem(system, proxy, tile, part);
		}

		@Override
		public boolean connectsToSide(BlockProxy proxy, TilePartblock tile,
				int part, int side, SystemType type) {
			return ((BRodPart) BlockData.rodPart).connectsToSide(proxy, tile, part, side, type);
		}

		@Override
		public PBlock getPartBlock() {
			return BlockData.rodPart;
		}

		@Override
		public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
				World world, int x, int y, int z, int side, float hitX, float hitY,
				float hitZ, int metadata) {	
			
			WorldUtil.setBlock(world, x, y, z, BlockData.partBlock.block);
			PTile tile = WorldUtil.getPTile(world, x, y, z);
			
			int facing = WorldUtil.getFacing(world, x, y, z, player) ^ 1;
			
			if (!player.isSneaking() && ConfigData.predictPlacement) {
				
				PTile clickedTile = WorldUtil.getPTile(world, x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]);
				
				if (clickedTile != null) {
					if (clickedTile instanceof TileRod) facing = side ^ 1;
					else if (clickedTile instanceof TileExtension) facing = side ^ 1;
					else if (clickedTile instanceof TilePartblock) {
						PBlock part = ((TilePartblock) clickedTile).getPart(side);
						if (part instanceof BRodPart) facing = side ^ 1;
					}
					else if (clickedTile instanceof TileMech) {
						if (side == WorldUtil.getBlockFacing(clickedTile)
							&& ((TileMech) clickedTile).getPElement() instanceof BRod) {
							facing = side ^ 1;
						}
					}
				}
			}
			
			((TilePartblock) tile).setPart(BlockData.rodPart, facing);
			
			if (stack.stackTagCompound != null) {
				((TilePartblock) tile).getTile(facing).readFromNBT(stack.stackTagCompound);
			}
			
			return true;
		}
		
		@Override
		public boolean onPartActivated(World world, int x, int y, int z,
				EntityPlayer player, int part, int side) {
			return false;
		}

		@Override
		public void onPartPlaced(TilePartblock tilePartblock, PTile tile,
				int part, int partClicked, EntityPlayer player) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void postRotate(TilePartblock tilePartblock, PTile tile,
				int part, int rotateDir) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean canPartBeAdded(IBlockAccess world, int x, int y, int z,
				int side) {
			return true;
		}

		@Override
		public boolean canPartStay(IBlockAccess world, int x, int y, int z,
				int side) {
			// TODO Auto-generated method stub
			return false;
		}
}
