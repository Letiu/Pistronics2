package letiu.pistronics.blocks;

import net.minecraft.world.IBlockAccess;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.IPistonElement;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileMotion;
import letiu.pistronics.util.BlockProxy;

public class BMotionblock extends PBlock implements IPistonElement {
	
	public BMotionblock() {
		this.name = "Motion Block";
		this.material = "rock";
		this.hardness = -1F;
		this.resistance = -1F;
		this.creativeTab = false;
		this.textures = new String[1];
		this.textures[0] = Textures.BOX;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		return this.blockIcon;
	}

	@Override
	public String getTexture(int meta, int side) {
		return this.blockIcon;
	}
	
	@Override
	public int getRenderID() {
		return -1;
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean hasCollisionBox() {
		return false;
	}
	
//	@Override
//	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
//		PTile tile = WorldUtil.getPTile(world, x, y, z);
//		if (tile != null && tile instanceof TileMotion) {
//			ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
//			boxes.add(((TileMotion) tile).getBoxForPush(0.0F));
//			return boxes;
//		}
//		return super.getBoxes(world, x, y, z, meta);
//	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public PTile createPTile() {
		return new TileMotion();
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy,
			boolean strongConnection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean connectsToSide(BlockProxy proxy, int side, SystemType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTransmitter(BlockProxy proxy) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, boolean strongConnection) {
		
//		if (system.getSystemType() == SystemType.REDSTONE) return;
//		
//		system.addElement(proxy, strongConnection);
//		
//		PTile tile = proxy.getPTile();
//		if (tile != null && tile instanceof TileMotion) {
//			PBlock element = ((TileMotion) tile).getPElement();
//			if (element instanceof IPistonElement) {
//				((IPistonElement) element).getConnectedForSystem(system, proxy, strongConnection);
//			}
//		}
		
//	}

//	@Override
//	public boolean connectsToSide(BlockProxy proxy, int side, SystemType type) {
//		PTile tile = proxy.getPTile();
//		if (tile != null && tile instanceof TileMotion) {
//			PBlock element = ((TileMotion) tile).getPElement();
////			if (element instanceof IPistonElement) {
////				return ((IPistonElement) element).connectsToSide(proxy, side, type);
////			}
//		}
//		return false;
//	}

//	@Override
//	public boolean isTransmitter(BlockProxy proxy) {
//		return false;
//	}
	
}
