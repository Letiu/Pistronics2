package letiu.modbase.items;

import java.util.List;

import letiu.modbase.render.TextureMapper;
import letiu.pistronics.data.PItem;
import letiu.pistronics.reference.ModInformation;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BaseItem extends Item implements IBaseItem {

	public PItem data;
	
	public BaseItem(int ID) {
		super(ID);
	}
	
	@Override
	public PItem getData() {
		return data;
	}
	
	@Override
	public void setData(PItem data) {
		this.data = data;
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		for (String path : data.textures) {
			TextureMapper.iconMap.put(path, iconRegister.registerIcon(ModInformation.RESOURCE_LOCATION + path));
		}
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return TextureMapper.iconMap.get(data.getIcon(stack, pass));
	}
	
	@Override
	public Icon getIconIndex(ItemStack stack) {
		return getIcon(stack, 0);
	}
	
	@Override
	public String getUnlocalizedName() {
		return data.name;
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
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
