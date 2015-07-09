package letiu.pistronics.itemblocks;

import java.util.List;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.PRenderManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import letiu.pistronics.data.PItemBlock;

public class BIStatue extends PItemBlock {
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt != null) {
			if (nbt.hasKey("entityKey")) {
				list.add("Creature: " + nbt.getString("entityKey"));
			}
			else {
				list.add("Creature: Sheep");
			}
			
			if (nbt.hasKey("scale")) {
				list.add("Size: " + nbt.getInteger("scale") + "%");
			}
			
			if (nbt.getBoolean("camou")) {
				String tex = "None";
				int camouID = nbt.getInteger("camouID");
				if (camouID != -1 && camouID != -2) {
					Block block = BlockItemUtil.getBlockByID(camouID);
					tex = block.getLocalizedName();
				}
				
				list.add(EnumChatFormatting.AQUA + "Camou: " + tex);
			}
		}
	}
	
	@Override
	public int getSpriteNumber() {
		return 0;
	}
	
	@Override
	public String getIcon(ItemStack stack, int pass) {
		return Textures.STATUE;
	}
	
//	@Override
//	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
//		NBTTagCompound nbt = stack.stackTagCompound = new NBTTagCompound();
//		NBTTagCompound entityNBT = new NBTTagCompound();
//		entity.writeToNBT(entityNBT);
//		nbt.setString("entityKey", EntityList.getEntityString(entity));
//		nbt.setTag("entityNBT", entityNBT);
//		
//		System.out.println("BIStatue.onLeftClickEntity() " + player.worldObj.isRemote);
//		
//		return true;
//	}
	
	@Override
	public PItemRenderer getSpecialRenderer() {
		return ConfigData.renderStatuesInInventory ? PRenderManager.statueItemRenderer : null;
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
}
