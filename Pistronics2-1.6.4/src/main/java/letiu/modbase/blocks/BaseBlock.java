package letiu.modbase.blocks;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.render.TextureMapper;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.reference.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BaseBlock extends Block {

	public static final String BLOCK_PREFIX = "[Block]";
	
	public PBlock data;
	
	public BaseBlock(int ID, Material material) {
		super(ID, material);
	}
	
	public void setBlockData(PBlock data) {
		this.data = data;
	}
	
	public PItem getItemBlock() {
		return data.getItemBlock();
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		if (data.textures != null) {
			for (String path : data.textures) {
				TextureMapper.iconMap.put(path, iconRegister.registerIcon(ModInformation.RESOURCE_LOCATION + path));
			}
		}
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z); 
		String iconKey = data.getTextureIndex(WorldUtil.getPTile(world, x, y, z), meta, side);
		
		if (iconKey.startsWith(BLOCK_PREFIX)) {
			String[] parts = iconKey.split("x");
			int blockID = Integer.valueOf(parts[1]);
			int blockMeta = Integer.valueOf(parts[2]);
			
			Block block = BlockItemUtil.getBlockByID(blockID);
			if (block == null) return null;
			
			return block.getIcon(side, blockMeta);
		}
		
		return TextureMapper.iconMap.get(iconKey);
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return TextureMapper.iconMap.get(data.getTexture(side, meta));
	}
	
	@Override
	public int getRenderType() {
		return data.getRenderID();
	}
	
	@Override
	public boolean isOpaqueCube() {
		// The Block Constructor is calling this before the data is set //
		if (data == null) return false;
		return data.isOpaque();
	}
	
//	/** This is called by the BlockMaker to set the opacity after data initialization */
//	public void setOpacity(boolean value) {
//		this.opaque = value;
//	}
	
	@Override
	public boolean renderAsNormalBlock() {
        return data.renderAsNormalBlock();
    }
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return data.shouldSideBeRenderedRaw(world, x, y, z, side);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		data.setBlockBoundsBasedOnState(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		if (data.hasCollisionBox()) {
			return super.getCollisionBoundingBoxFromPool(world, x, y, z);
		}
		else return null;
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {

		if (!data.hasCollisionBox()) return;
		
		ArrayList<AxisAlignedBB> boxes = data.getBoxes(world, x, y, z, WorldUtil.getBlockMeta(world, x, y, z));
		
		if (boxes != null && !boxes.isEmpty()) {
			for (AxisAlignedBB box : boxes) {
				WorldUtil.setBlockBounds(world, x, y, z, box);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			}
			this.setBlockBoundsBasedOnState(world, x, y, z);
		}
		else {
			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
		}
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 playerPos, Vec3 targetPos) {
		MovingObjectPosition result = data.collisionRayTrace(world, x, y, z, playerPos, targetPos);
		if (result != null) return result;
		else return super.collisionRayTrace(world, x, y, z, playerPos, targetPos);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		AxisAlignedBB box = data.getSelectedBox(world, x, y, z); 
		if (box != null) return box.getOffsetBoundingBox(x, y, z);
		else return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public String getUnlocalizedName() {
		return data.getUnlocalizedName();
	}

	@Override
	public String getLocalizedName() {
		return data.name;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		data.onBlockPlacedBy(world, x, y, z, player, stack);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		return data.onBlockActivated(world, x, y, z, player, side, xHit, yHit, zHit);
	}
	
	// isSideSolid on 1.7
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z,	ForgeDirection side) {
		return data.isSideSolid(world, x, y, z, side.ordinal());
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return data.getLightValue(world, x, y, z);
	}
	
	@Override
	public void getSubBlocks(int itemID, CreativeTabs tab, List list) {
		if (!data.getSubBlocks(itemID, tab, list)) {
			super.getSubBlocks(itemID, tab, list);
		}
	}
	
	@Override
	public int damageDropped(int damage) {
		return data.damageDropped(damage);
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = data.getDrops(world, x, y, z, metadata, fortune);
		if (drops == null) return super.getBlockDropped(world, x, y, z, metadata, fortune);
		else return drops;
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int fortune, EntityPlayer player) {
		data.onBlockHarvested(world, x, y, z, fortune, player);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int block) {
		data.onNeighborBlockChange(world, x, y, z, BlockItemUtil.getBlockByID(block));
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return data.shouldUseDefaultPick() ? super.getPickBlock(target, world, x, y, z) : data.getPickBlock(target, world, x, y, z);
	}

	@Override
	public int getRenderBlockPass() {
		return data.getRenderPass();
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return data.getWeakPower(world, x, y, z, side);
	}
	
	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
		return data.getStrongPower(world, x, y, z, side);
	}
	
	@Override
	public boolean shouldCheckWeakPower(World world, int x, int y, int z, int side) {
		return data.shouldCheckWeakPower(world, x, y, z, side);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && data.canPlaceBlockAt(world, x, y, z);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		data.onBlockDestroyedByPlayer(world, x, y, z, meta);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int ID, int meta) {
	
		data.onBlockBreak(world, x, y, z, blocksList[ID], meta);
		super.breakBlock(world, x, y, z, ID, meta);
	}
}
