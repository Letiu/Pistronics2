package letiu.modbase.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class SidedBlock extends BaseBlock {
	
	public int side;
	
	public static SidedBlock createInstance() {
		
		Block tempBlock = Block.blocksList[0];
		boolean tempOpaque = opaqueCubeLookup[0];
		int tempOpacity = lightOpacity[0];
        boolean tempGrass = canBlockGrass[0];
		
		Block.blocksList[0] = null;
		
		SidedBlock sidedBlock = new SidedBlock();
		
		Block.blocksList[0] = tempBlock;
		opaqueCubeLookup[0] = tempOpaque;
		lightOpacity[0] = tempOpacity;
		canBlockGrass[0] = tempGrass;
		
		return sidedBlock;
	}
	
	public SidedBlock() {
		super(0, Material.rock);
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return this.side == side;
	}
}
