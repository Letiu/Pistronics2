package letiu.modbase.blocks;

import letiu.modbase.tiles.TileMaker;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileBlock extends BaseBlock implements ITileEntityProvider {

	public TileBlock(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return TileMaker.makeTile(data.createPTile());
	}

}
