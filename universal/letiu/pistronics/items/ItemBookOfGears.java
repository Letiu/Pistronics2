package letiu.pistronics.items;

import java.util.List;

import letiu.modbase.core.ModClass;
import letiu.pistronics.data.GuiHandler;
import letiu.pistronics.data.PItem;
import letiu.pistronics.reference.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBookOfGears extends PItem {

	public ItemBookOfGears() {
		this.name = "Book of Gears";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.BOOK_OF_GEARS;
	}

	@Override
	public String getIcon(ItemStack stack, int pass) {
		return this.textures[0];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {	
		list.add("Author: Letiu");
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
	
//	@Override
//	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
//			int x, int y, int z, int side, float xHit, float yHit, float zHit) {
//	
//		player.openGui(ModClass.instance, GuiHandler.BOOK_OF_GEARS_ID, world, x, y, z);
//		return true;
//	}
	
//	@Override
//	public boolean canBlockTrigger(World world, int x, int y, int z, int side,
//			EntityPlayer player, ItemStack stack) {
//	
//		return true;
//	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		
		player.openGui(ModClass.instance, GuiHandler.BOOK_OF_GEARS_ID, world, x, y, z);
		return true;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		player.openGui(ModClass.instance, GuiHandler.BOOK_OF_GEARS_ID, world, -1, -1, -1);
		return stack;
	}
	
	
}
