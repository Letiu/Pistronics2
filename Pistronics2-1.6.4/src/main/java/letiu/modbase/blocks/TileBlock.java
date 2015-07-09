package letiu.modbase.blocks;

import letiu.modbase.tiles.TileMaker;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileBlock extends BaseBlock implements ITileEntityProvider {

	public TileBlock(int ID, Material material) {
		super(ID, material);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return TileMaker.makeTile(data.createPTile());
	}


}
