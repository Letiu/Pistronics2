package letiu.modbase.items;

import java.util.List;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.render.TextureMapper;
import letiu.modbase.util.BlockItemUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PItem;

public class BaseItemBlock extends ItemBlock implements IBaseItem {

	public PItem data;
	
	public BaseItemBlock(Block block) {
		super(block);
		this.data = ((BaseBlock) block).getItemBlock();
		this.data.item = this;
		
		if (getHasSubtypes()) setHasSubtypes(true);
		setMaxDamage(getMaxDamage());
		setMaxStackSize(data.getMaxStackSize());
	}
	
	public PItem getData() {
		return data;
	}
	
	public void setData(PItem data) {
		this.data = data;
	}
	
	public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
		if (data.canBlockTrigger(world, x, y, z, side, player, stack)) {
			return true;
		}
		else return super.func_150936_a(world, x, y, z, side, player, stack);
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		
		String iconKey = data.getIcon(stack, pass);
		
		if (iconKey == null) return getBoxIcon();
		
		if (iconKey.startsWith(BaseBlock.BLOCK_PREFIX)) {
			String[] parts = iconKey.split("x");
			int blockID = Integer.valueOf(parts[1]);
			int blockMeta = Integer.valueOf(parts[2]);
			
			Block block = BlockItemUtil.getBlockByID(blockID);
			if (block == null) return getBoxIcon();
			
			return block.getIcon(pass, blockMeta);
		}
		
		return TextureMapper.iconMap.get(iconKey);
	}
	
	private IIcon getBoxIcon() {
		return BlockData.gear.block.getIcon(0, 0);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		data.addInformation(stack, player, list, value);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return data.getUnlocalizedName(super.getUnlocalizedName(stack), stack);
	}
	
	@Override
	public int getSpriteNumber() {
		return data.getSpriteNumber();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return data.onItemRightClick(stack, world, player);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y,
			int z, int side, float xHit, float yHit, float zHit) {
		if (!data.onItemUse(stack, player, world, x, y, z, side, xHit, yHit, zHit)) {
			return super.onItemUse(stack, player, world, x, y, z, side, xHit, zHit, yHit);
		}
		else return true;
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return data.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
	
	@Override
	public int getMetadata (int damageValue) {
		return data.getDamageValue(damageValue);
	}
	
	@Override
	public int getMaxDamage() {
		return data.getMaxDamage();
	}
	
	@Override
	public boolean getHasSubtypes() {
		return data.getHasSubtypes();
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ, int metadata) {
		if (data.overwritesPlaceBlockAt()) {
			return data.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
		}
		else {
			return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return data.onLeftClickEntity(stack, player, entity);
	}
}
