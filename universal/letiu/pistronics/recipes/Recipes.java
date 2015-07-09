package letiu.pistronics.recipes;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.RecipeUtil;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.recipes.custom.*;
import letiu.pistronics.reference.PotionReference;

public class Recipes {

    public static void registerRecipes() {

        System.out.println("Pistronics: Starting custom recipe registering");
        NBTSensitiveRecipes.registerRecipes();
        ExtensionMatRecipes.registerRecipes();
        SailPartRecipes.registerRecipes();
        System.out.println("Pistronics: Successfully registered " + PRecipeRegistry.recipeAmount() + " recipes.");
        RecipeUtil.addIRecipe(PRecipeRegistry.instance());
        registerNormalRecipes();
    }

	public static void registerNormalRecipes() {

		/// Blocks ///

		RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(BlockData.camouBlock), 
				"AAA", "AAA", "AAA", 
				'A', ItemData.camoupaste.item);

        if (!ConfigData.alternateSlimeblockRecipe) {
            RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.slimeblock, 1, 0),
                    "AA", "AA",
                    'A', ItemReference.SLIME);

            RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.slimeblock, 1, 1),
                    "AA", "AA",
                    'A', ItemData.glue.item);

            RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.slimeblock, 1, 2),
                    "AA", "AA",
                    'A', ItemData.super_glue.item);
        }
        else {
            RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.slimeblock, 1, 0),
                    " A ", "A A", " A ",
                    'A', ItemReference.SLIME);

            RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.slimeblock, 1, 1),
                    " A ", "A A", " A ",
                    'A', ItemData.glue.item);

            RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.slimeblock, 1, 2),
                    " A ", "A A", " A ",
                    'A', ItemData.super_glue.item);
        }

		/// Items ///
		
		RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.bookOfGears), 
				" A ", "ABA", " A ", 
				'A', ItemReference.STICK,
				'B', ItemReference.BOOK);

		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemReference.REDSTONE_ITEM),
				ItemData.pileOfRedstone.item, ItemData.pileOfRedstone.item);
		
		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemData.pileOfRedstone, 2, 0),
				ItemReference.REDSTONE_ITEM);
		
		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemData.camoupaste, 2, 0),
				ItemReference.NETHER_WART, ItemReference.getDye(1), ItemReference.getDye(2), ItemReference.getDye(4));
		
		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemData.camoupaste, 9, 0),
				BlockData.camouBlock.block);
		
		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemData.glue),
				ItemReference.WHEAT_SEEDS, ItemReference.WHEAT_SEEDS,
				ItemReference.SUGAR, ItemReference.getDye(15));
		
		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemData.super_glue),
				ItemData.glue.item, ItemReference.getDye(1));
		
		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemReference.SLIME, 4, 0),
				BlockItemUtil.getStack(ItemData.slimeblock, 1, 0));
		
		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemData.glue, 4, 0),
				BlockItemUtil.getStack(ItemData.slimeblock, 1, 1));
		
		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemData.super_glue, 4, 0),
				BlockItemUtil.getStack(ItemData.slimeblock, 1, 2));
		
		RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.redioGlue), 
				" A ", "ABA", " A ",
				'A', ItemData.pileOfRedstone.item,
				'B', ItemData.glue.item);
		
		RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.redioGlue), 
				" A ", "ABA", " A ",
				'A', ItemData.pileOfRedstone.item,
				'B', ItemReference.SLIME);
		
		RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.redioSuperGlue), 
				" A ", "ABA", " A ",
				'A', ItemData.pileOfRedstone.item,
				'B', ItemData.super_glue.item);
		
		RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemData.redioSuperGlue),
				ItemData.redioGlue.item, ItemReference.getDye(1));
		
		RecipeUtil.addShapedRecipe(BlockItemUtil.getStack(ItemData.petrifyExtract, 2, 0),
				"AAA", "ABA", "AAA",
				'A', ItemReference.STONE,
				'B', BlockItemUtil.getStack(ItemReference.POTION, 1, PotionReference.LONG_POSION));

        RecipeUtil.addShapelessRecipe(BlockItemUtil.getStack(ItemData.petrifyArrow),
				ItemData.petrifyExtract.item, ItemReference.ARROW);
	}

}
