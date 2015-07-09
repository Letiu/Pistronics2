package letiu.pistronics.recipes;

import net.minecraft.item.ItemStack;

public interface IStackComparator {
    public boolean matches(ItemStack stack);
    public ItemStack getExampleStack();
}
