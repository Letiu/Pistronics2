package letiu.pistronics.data;

import java.util.List;

import letiu.pistronics.render.PItemRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class PItemBlock extends PItem {

	public String getUnlocalizedName(String defaultName, ItemStack stack) {
		return defaultName;
	}

	public PItemRenderer getSpecialRenderer() {
		return null;
	}

	public int getSpriteNumber() {
		return 0;
	}
}
