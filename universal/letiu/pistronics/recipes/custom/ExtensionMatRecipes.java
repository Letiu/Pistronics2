package letiu.pistronics.recipes.custom;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.pistronics.blocks.BRod;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.recipes.PRecipeRegistry;
import letiu.pistronics.recipes.PShapelessRecipe;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.util.ExtensionUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ExtensionMatRecipes {

    public static void registerRecipes() {
        ArrayList<ItemStack> extensions = ExtensionUtil.getExtensionStacksWith(true, true, true, true, true);
        for (ItemStack stack : extensions) {
            ArrayList<PShapelessRecipe> recipes = getRecipesForCrafting(stack);
            for (PShapelessRecipe recipe : recipes) {
                PRecipeRegistry.registerShapelessRecipe(recipe);
            }
            ItemStack partStack = BlockItemUtil.getStack(BlockData.extensionPart);
            partStack.stackTagCompound = (NBTTagCompound) stack.stackTagCompound.copy();
            recipes = getRecipesForCrafting(partStack);
            for (PShapelessRecipe recipe : recipes) {
                PRecipeRegistry.registerShapelessRecipe(recipe);
            }
        }
    }

    public static final int VARIATIONS = 32;

    public static final int STICKY = 1;
    public static final int SUPER_STICKY = 2;
    public static final int REDSTONE = 4;
    public static final int CAMOU = 8;
    public static final int REDIO = 16;

    public static ArrayList<PShapelessRecipe> getRecipesForCrafting(ItemStack result) {
        ArrayList<PShapelessRecipe> recipes = new ArrayList<PShapelessRecipe>();

        if (CompareUtil.compareIDs(result, BlockData.extension.block) || CompareUtil.compareIDs(result, BlockData.extensionPart.block)) {

            if (CompareUtil.compareIDs(result, BlockData.extension.block)) {
                recipes.add(getUpgradeRecipe(result));
            }

            int data = ExtensionUtil.getDataFor(result);
            for (int i = 0; i < VARIATIONS; i++) {
                ItemStack part = null;
                if (CompareUtil.compareIDs(result, BlockData.extension.block)) part = ExtensionUtil.getExtension(i);
                if (CompareUtil.compareIDs(result, BlockData.extensionPart.block))
                    part = ExtensionUtil.getExtensionPart(i);

                if (part != null && ExtensionUtil.isPossibleCombination(i) && data != i && (data == (data | i))) {
                    ItemStack ext = result.copy();
                    ext.stackSize = 1;
                    getRecipes(recipes, ext, part);
                }
            }
        }

        return recipes;
    }

    private static void getRecipes(List<PShapelessRecipe> recipes, ItemStack result, ItemStack part) {
        PShapelessRecipe recipe = new PShapelessRecipe(result);
        recipe.addIngredient(part);

        int ingrData = ExtensionUtil.getDataFor(result) ^ ExtensionUtil.getDataFor(part);

        if (((ingrData & STICKY) == 0 && (ingrData & REDIO) != 0)) return;

        if ((ingrData & REDSTONE) != 0) {
            if (CompareUtil.compareIDs(result, BlockData.extensionPart.block)) {
                recipe.addIngredient(BlockItemUtil.getStack(ItemData.pileOfRedstone));
            }
            else recipe.addIngredient(BlockItemUtil.getStack(ItemReference.REDSTONE_ITEM));
        }
        if ((ingrData & CAMOU) != 0) {
            recipe.addIngredient(BlockItemUtil.getStack(ItemData.camoupaste));
        }
        if ((ingrData & STICKY) == 0 && (ingrData & SUPER_STICKY) != 0) {
            recipe.addIngredient(ItemReference.getDye(1));
        }
        else if ((ingrData & STICKY) != 0 && (ingrData & SUPER_STICKY) != 0) {
            if ((ingrData & REDIO) != 0) {
                PShapelessRecipe glue1 = recipe.copy();
                glue1.addIngredient(BlockItemUtil.getStack(ItemData.redioGlue));
                glue1.addIngredient(ItemReference.getDye(1));
                recipes.add(glue1);

                recipe.addIngredient(BlockItemUtil.getStack(ItemData.redioSuperGlue));
            }
            else {
                PShapelessRecipe glue1 = recipe.copy();
                glue1.addIngredient(BlockItemUtil.getStack(ItemData.glue));
                glue1.addIngredient(ItemReference.getDye(1));
                recipes.add(glue1);
                PShapelessRecipe glue2 = recipe.copy();
                glue2.addIngredient(BlockItemUtil.getStack(ItemReference.SLIME));
                glue2.addIngredient(ItemReference.getDye(1));
                recipes.add(glue2);

                recipe.addIngredient(BlockItemUtil.getStack(ItemData.super_glue));
            }
        }
        else if ((ingrData & STICKY) != 0) {
            if ((ingrData & REDIO) != 0) {
                recipe.addIngredient(BlockItemUtil.getStack(ItemData.redioGlue));
            }
            else {
                PShapelessRecipe glue1 = recipe.copy();
                glue1.addIngredient(BlockItemUtil.getStack(ItemData.glue));
                recipes.add(glue1);

                recipe.addIngredient(BlockItemUtil.getStack(ItemReference.SLIME));
            }
        }

        recipes.add(recipe);
    }

    private static PShapelessRecipe getUpgradeRecipe(ItemStack stack) {
        ItemStack ext = BlockItemUtil.getStack(BlockData.extension.block);
        ext.stackTagCompound = (NBTTagCompound) stack.stackTagCompound.copy();
        ItemStack extPart = BlockItemUtil.getStack(BlockData.extensionPart.block);
        extPart.stackTagCompound = (NBTTagCompound) stack.stackTagCompound.copy();
        ItemStack rodPart = BlockItemUtil.getStack(BlockData.rodPart.block);
        rodPart.stackTagCompound = BRod.getDefaultNBT();
        if (ext.stackTagCompound.getBoolean("redstone")) {
            rodPart.stackTagCompound.setBoolean("redstone", true);
        }
        return new PShapelessRecipe(ext, extPart, rodPart);
    }
}
