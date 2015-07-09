package letiu.pistronics.recipes.custom;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.recipes.PRecipeRegistry;
import letiu.pistronics.recipes.PShapelessRecipe;
import letiu.pistronics.tiles.TileSail;
import letiu.pistronics.util.SailUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;

public class SailPartRecipes {

    public static void registerRecipes() {
        ArrayList<ItemStack> stacks = SailUtil.getAllSails();
        for (ItemStack stack : stacks) {
            ArrayList<PShapelessRecipe> recipes = getRecipesForCrafting(stack);
            for (PShapelessRecipe recipe : recipes) {
                PRecipeRegistry.registerShapelessRecipe(recipe);
            }
        }
    }

    public static ArrayList<PShapelessRecipe> getRecipesForCrafting(ItemStack result) {
        ArrayList<PShapelessRecipe> recipes = new ArrayList<PShapelessRecipe>();

        if (CompareUtil.compareIDs(result, BlockData.sailPart.block)) {

            ItemStack result1 = result.copy();
            result1.stackSize = 1;

            int sailColor = result.stackTagCompound.getInteger("color");
            boolean camou = result.stackTagCompound.getBoolean("camou");

            if (camou) {
                ItemStack nonCamouStack = result1.copy();
                nonCamouStack.stackTagCompound.setBoolean("camou", false);

                recipes.add(new PShapelessRecipe(result1, nonCamouStack, BlockItemUtil.getStack(ItemData.camoupaste)));
            }

            for (int i = 0; i < 16; i++) {
                if (i != sailColor) {
                    ItemStack coloredStack = result1.copy();
                    coloredStack.stackTagCompound.setInteger("color", i);
                    recipes.add(new PShapelessRecipe(result1, coloredStack, ItemReference.getDye(15 - sailColor)));

                    if (camou) {
                        ItemStack nonCamouStack = coloredStack.copy();
                        nonCamouStack.stackTagCompound.setBoolean("camou", false);
                        recipes.add(new PShapelessRecipe(result1, nonCamouStack, ItemReference.getDye(15 - sailColor), BlockItemUtil.getStack(ItemData.camoupaste)));
                    }
                }
            }
        }

        return recipes;
    }
}
