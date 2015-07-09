package letiu.pistronics.itemblocks;

import letiu.pistronics.blocks.BExtensionPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.util.BlockProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BIExtensionPart extends BIExtension implements IPart {

    @Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy,
			TilePartblock tile, int part) {
		((BExtensionPart) BlockData.extensionPart).getConnectedForSystem(system, proxy, tile, part);
	}

	@Override
	public boolean connectsToSide(BlockProxy proxy, TilePartblock tile,
			int part, int side, SystemType type) {
		return ((BExtensionPart) BlockData.extensionPart).connectsToSide(proxy, tile, part, side, type);
	}

	@Override
	public PBlock getPartBlock() {
		return BlockData.extensionPart;
	}
	
	@Override
	public boolean overwritesPlaceBlockAt() {
		return false;
	}

	@Override
	public boolean onPartActivated(World world, int x, int y, int z,
			EntityPlayer player, int part, int side) {
		return false;
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
	public boolean canPartBeAdded(IBlockAccess world, int x, int y, int z,
			int side) {
		return true;
	}

	@Override
	public boolean canPartStay(IBlockAccess world, int x, int y, int z, int side) {
		// TODO Auto-generated method stub
		return false;
	}
}
