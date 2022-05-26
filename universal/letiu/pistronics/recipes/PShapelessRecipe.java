package letiu.pistronics.recipes;

import letiu.modbase.util.CompareUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class PShapelessRecipe {

    private ItemStack result;
    private ArrayList<ItemStack> ingredients;

    public PShapelessRecipe(ItemStack result) {
        this.result = result;
        this.ingredients = new ArrayList<ItemStack>();
    }

    public PShapelessRecipe(ItemStack result, ItemStack... ingredients) {
        this.result = result;
        this.ingredients = new ArrayList<ItemStack>();
        for (ItemStack stack : ingredients) {
            this.ingredients.add(stack);
        }
    }

    public void addIngredient(ItemStack... ingriedent) {
        for (ItemStack stack : ingriedent) {
            ingredients.add(stack);
        }
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public ItemStack getResult() {
        return this.result.copy();
    }

    public ArrayList<ItemStack> getIngredients() {
        return this.ingredients;
    }

    public boolean isIngredient(ItemStack stack) {
        for (ItemStack ingridient : ingredients) {
            if (CompareUtil.compare(ingridient, stack)) {
                return true;
            }
        }
        return false;
    }

    public boolean isResult(ItemStack result) {
        return CompareUtil.compare(result, this.result);
    }

    public boolean matches(InventoryCrafting inv) {
        boolean[] foundIngredients = new boolean[ingredients.size()];

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            boolean foundMatch = false;
            for (int k = 0; k < ingredients.size(); k++) {
                if (!foundIngredients[k] && CompareUtil.compare(ingredients.get(k), inv.getStackInSlot(i))) {
                    foundIngredients[k] = foundMatch = true;
                    break;
                }
            }
            if (!foundMatch && (inv.getStackInSlot(i) != null)) return false;
        }

        for (int k = 0; k < foundIngredients.length; k++) {
            if (!foundIngredients[k]) return false;
        }

        return true;
    }

    public PShapelessRecipe copy() {
        PShapelessRecipe result = new PShapelessRecipe(this.result);
        for (ItemStack ingr : ingredients) {
            result.addIngredient(ingr);
        }
        return result;
    }
}
