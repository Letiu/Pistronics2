package letiu.pistronics.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.util.BlockProxy;

public interface IPart {

	public boolean onPartActivated(World world, int x, int y, int z, EntityPlayer player, int part, int side);
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, TilePartblock tile, int part);
	public boolean connectsToSide(BlockProxy proxy, TilePartblock tile, int part, int side, SystemType type);
	public PBlock getPartBlock();
	public void onPartPlaced(TilePartblock tilePartblock, PTile tile, int part, int partClicked, EntityPlayer player);
	public void postRotate(TilePartblock tilePartblock, PTile tile, int part, int rotateDir);
	public boolean canPartBeAdded(IBlockAccess world, int x, int y, int z, int side);
	public boolean canPartStay(IBlockAccess world, int x, int y, int z, int side);
	
}
