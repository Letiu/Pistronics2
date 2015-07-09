package letiu.modbase.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.render.TextureMapper;
import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.data.PItem;
import letiu.pistronics.reference.ModInformation;

public class BaseItem extends Item implements IBaseItem {

	public PItem data;
	
	@Override
	public PItem getData() {
		return data;
	}
	
	@Override
	public void setData(PItem data) {
		this.data = data;
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		for (String path : data.textures) {
			TextureMapper.iconMap.put(path, iconRegister.registerIcon(ModInformation.RESOURCE_LOCATION + path));
		}
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {

		String iconKey = data.getIcon(stack, pass);
		
		if (iconKey.startsWith(BaseBlock.BLOCK_PREFIX)) {
			String[] parts = iconKey.split("x");
			int blockID = Integer.valueOf(parts[1]);
			int blockMeta = Integer.valueOf(parts[2]);

			return BlockItemUtil.getBlockByID(blockID).getIcon(pass, blockMeta);
		}
		
		return TextureMapper.iconMap.get(iconKey);
		
//		return TextureMapper.iconMap.get(data.getIcon(stack, pass));
	}
	
	@Override
	public IIcon getIconIndex(ItemStack stack) {
		return getIcon(stack, 0);
	}
	
	@Override
	public String getUnlocalizedName() {
		return data.name;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return data.name;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y,
			int z, int side, float xHit, float yHit, float zHit) {
		
		return data.onItemUse(stack, player, world, x, y, z, side, xHit, yHit, zHit);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return data.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return data.onItemRightClick(stack, world, player);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		data.addInformation(stack, player, list, value);
	}
	
	@Override
	public int getSpriteNumber() {
		return data.getSpriteNumber();
	}
	
	@Override
	public boolean getHasSubtypes() {
		return data.getHasSubtypes();
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return data.onLeftClickEntity(stack, player, entity);
	}
}
