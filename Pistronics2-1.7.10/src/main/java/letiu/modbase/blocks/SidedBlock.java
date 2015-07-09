package letiu.modbase.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class SidedBlock extends BaseBlock {
	
	public int side;
	
	public static SidedBlock createInstance() {
		return new SidedBlock();
	}
	
	public SidedBlock() {
		super(Material.rock);
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return this.side == side;
	}
}
