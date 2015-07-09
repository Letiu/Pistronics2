package letiu.modbase.tiles;

import letiu.pistronics.data.PTile;
import letiu.pistronics.tiles.ISpecialRenderTile;

public class TileMaker {

	public static BaseTile makeTile(PTile data) {
		
		BaseTile tile;
		
		if (!data.hasInventory()) {
			if (data instanceof ISpecialRenderTile) {
				tile = new SpecialRenderTile();
			}
			else {
				tile = new BaseTile();
			} 
		}
		else {
			if (data instanceof ISpecialRenderTile) {
				tile = new SRInventoryTile();
			}
			else {
				tile = new InventoryTile();
			}
		}
		
		tile.data = data;
		data.tileEntity = tile;
		
		return tile;
	}
	
}
