package letiu.modbase.integration.nei;

import codechicken.nei.recipe.ShapelessRecipeHandler;
import letiu.pistronics.recipes.PShapelessRecipe;
import letiu.pistronics.recipes.PRecipeRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShapelessHandler extends ShapelessRecipeHandler {

    public class CachedBaseShapelessRecipe extends CachedShapelessRecipe {

        ArrayList<ItemStack> itemStacks = new ArrayList<ItemStack>();

        public CachedBaseShapelessRecipe(ItemStack result, ArrayList<ItemStack> ingredients) {
            super(result);
            this.itemStacks = ingredients;
            setIngredients(itemStacks);
            setResult(result);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<PShapelessRecipe> recipes = PRecipeRegistry.getShapelessCraftingRecipesFor(result);//RecipeData.getShapelessNeiCraftingRecipes(result);
        for (PShapelessRecipe recipe : recipes) {
            arecipes.add(new CachedBaseShapelessRecipe(recipe.getResult(), recipe.getIngredients()));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<PShapelessRecipe> recipes = PRecipeRegistry.getShapelessUsageRecipeFor(ingredient);//RecipeData.getShapelessNeiUsageRecipes(ingredient);
        for (PShapelessRecipe recipe : recipes) {
            arecipes.add(new CachedBaseShapelessRecipe(recipe.getResult(), recipe.getIngredients()));
        }
    }

    @Override
    public String getRecipeName() {
        return "Pistronics Shapeless";
    }
}
