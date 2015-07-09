package letiu.pistronics.render.tiles;

import letiu.modbase.util.WorldUtil;
import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.tiles.TileRodFolder;

public class RodFolderElementRenderer extends MechElementRenderer {

//	@Override
//	protected float getXOffset(TileElementHolder tile, float ticktime) {
//		return 0.0F;
//	}
//	
//	@Override
//	protected float getYOffset(TileElementHolder tile, float ticktime) {
//		return 0.0F;
//	}
//	
//	@Override
//	protected float getZOffset(TileElementHolder tile, float ticktime) {
//		return 0.0F;
//	}
	
	@Override
	protected boolean show(TileElementHolder tile) {
		
		if (!((TileRodFolder) tile).isMoving()) return true;
		
		return WorldUtil.getBlockFacing(tile) != ((TileRodFolder) tile).getMoveDir();
	}
}
