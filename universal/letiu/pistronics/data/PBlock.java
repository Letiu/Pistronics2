package letiu.pistronics.data;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.render.RenderTweaker;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class PBlock {
	
	public String name, material;
	public float hardness, resistance;
	public boolean creativeTab;
	public String[] textures;
	public String blockIcon;
	
	/** Do not use before init */
	public BaseBlock block;
	
	public abstract String getTextureIndex(PTile tile, int meta, int side);
	public abstract String getTexture(int meta, int side);
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {}
	
	public int getRenderID() {
		return 0;
	}
	
	public boolean isOpaque() {
		return true;
	}
	
	public boolean renderAsNormalBlock() {
		return true;
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		if (WorldUtil.getPBlock(world, x, y, z) == this) {	
			ArrayList<AxisAlignedBB> boxes = getBoxes(world, x, y, z, WorldUtil.getBlockMeta(world, x, y, z));
			
			if (boxes == null || boxes.isEmpty()) {
				WorldUtil.setBlockBounds(world, x, y, z, 0F, 0F, 0F, 1F, 1F, 1F);
			}
			else {
				WorldUtil.setBlockBounds(world, x, y, z, boxes.get(0));
			}
		}
	}
	
	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
		return null;
	}
	
	public boolean hasTileEntity() {
		return false;
	}
	
	public PTile createPTile() {
		return null;
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		return false;
	}
	
	public final boolean shouldSideBeRenderedRaw(IBlockAccess world, int x, int y, int z, int side) {
		if (RenderTweaker.sideRender[side] == false) return false;
		if (RenderTweaker.renderAllSides == true) return true;
		return this.shouldSideBeRendered(world, x, y, z, side);
	}
	
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return BlockItemUtil.defaultShouldSideBeRendered(world, x, y, z, side);
	}
	
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 playerPos, Vec3 targetPos) {
		return null;
	}
	
	public AxisAlignedBB getSelectedBox(World world, int x, int y, int z) {
		return null;
	}
	
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, int side) {
		return isOpaque() && renderAsNormalBlock();
	}
	
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return 0;
	}
	
	public ItemStack getDroppedStack(PTile tile, int meta) {
		return BlockItemUtil.getStack(this);
	}
	
	public boolean getSubBlocks(int itemID, CreativeTabs tab, List list) {
		return false;
	}
	
	public int damageDropped(int damage) {
		return 0;
	}
	
	public boolean hasCollisionBox() {
		return true;
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
	}
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return null;
	}
	
	public void onBlockHarvested(World world, int x, int y, int z, int fortune, EntityPlayer player) {
		
	}
	
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return null;
	}
	
	public boolean shouldUseDefaultPick() {
		return true;
	}
	
	public int getRenderPass() {
		return 0;
	}
	public String getUnlocalizedName() {
		return name;
	}
	
	public PItem getItemBlock() {
		return null;
	}
	
	public int getWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return 0;
	}
	
	public int getStrongPower(IBlockAccess world, int x, int y, int z, int side) {
		return 0;
	}
	
	public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return false;
	}
	
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return true;
	}
	
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		
	}
	
	public void onBlockBreak(World world, int x, int y, int z, Block block2, int meta) {
		
	}
	
	public boolean canDropFromExplosion() {
		return true;
	}
}
