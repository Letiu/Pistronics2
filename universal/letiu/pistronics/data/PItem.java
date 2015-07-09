package letiu.pistronics.data;

import java.util.List;

import letiu.modbase.items.IBaseItem;
import letiu.pistronics.render.PItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class PItem {

	public int ID;
	public String name;
	public boolean creativeTab;
	public String[] textures;
	public String itemIcon;
	
	/** Do not use before init */
	public IBaseItem item;
	
	public String getIcon(ItemStack stack, int pass) {
		return null;
	}

	public int getMaxStackSize() {
		return 64;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side, float xHit, float yHit, float zHit) {
		
		return false;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean value) {
		
	}

	public String getUnlocalizedName(String defaultName, ItemStack stack) {
		return defaultName;
	}
	
	public int getSpriteNumber() {
		return 1;
	}
	
	public PItemRenderer getSpecialRenderer() {
		return null;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return stack;
	}

	public boolean getHasSubtypes() {
		return false;
	}

	public int getDamageValue(int damageValue) {
		return 0;
	}

	public int getMaxDamage() {
		return 0;
	}

	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		return false;
	}
	
	public boolean overwritesPlaceBlockAt() {
		return false;
	}
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		return false;
	}

	public boolean canBlockTrigger(World world, int x, int y, int z, int side,
			EntityPlayer player, ItemStack stack) {
		return false;
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return false;
	}
}
