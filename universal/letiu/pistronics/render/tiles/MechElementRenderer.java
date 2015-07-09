package letiu.pistronics.render.tiles;

import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.tiles.TileMotion;
import letiu.pistronics.util.Vector3;

public class MechElementRenderer extends ElementRenderer {

	@Override
	protected float getXOffset(TileElementHolder tile, float ticktime) {
		return ((TileMech) tile).getOffsetX(ticktime);
	}
	
	@Override
	protected float getYOffset(TileElementHolder tile, float ticktime) {
		return ((TileMech) tile).getOffsetY(ticktime);
	}
	
	@Override
	protected float getZOffset(TileElementHolder tile, float ticktime) {
		return ((TileMech) tile).getOffsetZ(ticktime);
	}
	
	@Override
	protected boolean useFakeWorld() {
		return false;
	}
	
	@Override
	protected float getAngle(TileElementHolder tile, float ticktime) {
		return ((TileMech) tile).getAngleForRender(ticktime);
	}
	
	@Override
	protected int getRotateDir(TileElementHolder tile) {
		return ((TileMech) tile).getRotateDir();
	}
	
	@Override
	protected boolean useRotateFix(TileElementHolder tile) {
		return true;
	}
}
