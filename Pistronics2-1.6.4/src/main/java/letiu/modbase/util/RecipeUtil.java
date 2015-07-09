package letiu.modbase.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeUtil {

	public static void addShapedRecipe(ItemStack result, Object... recipe) {
		GameRegistry.addShapedRecipe(result, recipe);
	}
	
	public static void addShapelessRecipe(ItemStack result, Object... recipe) {
		GameRegistry.addShapelessRecipe(result, recipe);
	}
	
	public static void addIRecipe(IRecipe recipe) {
		GameRegistry.addRecipe(recipe);
	}
	
}
