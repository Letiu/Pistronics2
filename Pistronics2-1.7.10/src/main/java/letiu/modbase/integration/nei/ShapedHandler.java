package letiu.modbase.integration.nei;

import codechicken.nei.recipe.ShapedRecipeHandler;
import letiu.pistronics.recipes.PRecipeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

public class ShapedHandler extends ShapedRecipeHandler {

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<ShapedOreRecipe> recipes = PRecipeRegistry.getShapedCraftingRecipesFor(result);//RecipeData.getShapedNeiCraftingRecipes(result);
        for (ShapedOreRecipe recipe : recipes) {
            this.arecipes.add(forgeShapedRecipe(recipe));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<ShapedOreRecipe> recipes = PRecipeRegistry.getShapedUsageRecipesFor(ingredient);//RecipeData.getShapedNeiUsageRecipes(ingredient);
        for (ShapedOreRecipe recipe : recipes) {
            this.arecipes.add(forgeShapedRecipe(recipe));
        }
    }

    @Override
    public String getRecipeName() {
        return "Pistronics Shaped";
    }
}
