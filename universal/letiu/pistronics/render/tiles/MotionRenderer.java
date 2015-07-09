package letiu.pistronics.render.tiles;

import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.tiles.TileMotion;
import letiu.pistronics.util.Vector3;

public class MotionRenderer extends ElementRenderer {

	@Override
	protected float getAngle(TileElementHolder tile, float ticktime) {
		return ((TileMotion) tile).getAngleForRender(ticktime);
	}
	
	@Override
	protected int getRotateDir(TileElementHolder tile) {
		return ((TileMotion) tile).getRotateDir();
	}
	
	@Override
	protected boolean useRotateFix(TileElementHolder tile) {
		return true;
	}
	
	@Override
	protected Vector3 getRotatePoint(TileElementHolder tile) {
		if (!((TileMotion) tile).isRotating()) return new Vector3(0, 0, 0);
		return ((TileMotion) tile).getRotatePoint();
	}
	
	@Override
	protected float getXOffset(TileElementHolder tile, float ticktime) {
		return ((TileMotion) tile).getOffsetX(ticktime);
	}
	
	@Override
	protected float getYOffset(TileElementHolder tile, float ticktime) {
		return ((TileMotion) tile).getOffsetY(ticktime);
	}
	
	@Override
	protected float getZOffset(TileElementHolder tile, float ticktime) {
		return ((TileMotion) tile).getOffsetZ(ticktime);
	}
}
