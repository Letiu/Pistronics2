package letiu.pistronics.util;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.blocks.BSailPart;
import letiu.pistronics.data.BlockData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SailUtil {

    public static ArrayList<ItemStack> getStacksForCreativeInv() {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        for (int i = 0; i < 16; i++) {
            stacks.add(getSailWith(i, false));
        }
        stacks.add(getSailWith(0, true));
        return stacks;
    }

    public static ArrayList<ItemStack> getAllSails() {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        for (int i = 0; i < 16; i++) {
            stacks.add(getSailWith(i, false));
            stacks.add(getSailWith(i, true));
        }
        return stacks;
    }

    public static ItemStack getSailWith(int color, boolean camou) {
        ItemStack stack = BlockItemUtil.getStack(BlockData.sailPart);
        stack.stackTagCompound = BSailPart.getDefaultNBT();
        stack.stackTagCompound.setInteger("color", color);
        stack.stackTagCompound.setBoolean("camou", camou);
        return stack;
    }
}
