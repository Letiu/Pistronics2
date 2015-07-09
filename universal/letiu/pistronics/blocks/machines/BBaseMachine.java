package letiu.pistronics.blocks.machines;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.render.PRenderManager;

/** Basic 3 Quarter (piston like) shaped block */
public abstract class BBaseMachine extends PBlock {

	@Override
	public String getTexture(int meta, int side) {
		return getTextureIndex(null, meta, side);
	}
	
	@Override
	public int getRenderID() {
		return PRenderManager.getRenderID(PRenderManager.pistonRenderer);
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

        switch (WorldUtil.getBlockFacing(world, x, y, z)) {
            case 0:
            	WorldUtil.setBlockBounds(world, x, y, z, 0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;
            case 1:
            	WorldUtil.setBlockBounds(world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                break;
            case 2:
            	WorldUtil.setBlockBounds(world, x, y, z, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                break;
            case 3:
            	WorldUtil.setBlockBounds(world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                break;
            case 4:
            	WorldUtil.setBlockBounds(world, x, y, z, 0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;
            case 5:
            	WorldUtil.setBlockBounds(world, x, y, z, 0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
        }
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		int facing = WorldUtil.getBlockFacing(world, x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]);
		if (facing != side) {
			return super.shouldSideBeRendered(world, x, y, z, side);
		}
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		WorldUtil.setBlockFacing(world, x, y, z, player);
	}
}
