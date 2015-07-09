package letiu.pistronics.util;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.blocks.BExtension;
import letiu.pistronics.data.BlockData;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ExtensionUtil {

    public static final int STATS = 5;

    /** sticky, super_sticky, redstone, camou, redio */
    public static ArrayList<ItemStack> getExtensionStacksWith(boolean... stats) {

        ArrayList<ItemStack> result = new ArrayList<ItemStack>();

        int variations = (int) Math.pow(2, stats.length);
        int exclusiveMask = 0;

        for (int i = 0; i < stats.length; i++) {
            if (!stats[i]) exclusiveMask = (exclusiveMask | (int) Math.pow(2, i));
        }

        for (int i = 0; i < variations; i++) {
            // compares with mask and removes non sticky superstickies/redios and non redstone redios
            if ((i & exclusiveMask) == 0 && (i & 3) != 2 && (i & 17) != 16 && (i & 20) != 16) {
                ItemStack stack = BlockItemUtil.getStack(BlockData.extension);
                stack.stackTagCompound = BExtension.getDefaultNBT();
                for (int k = 0; k < stats.length; k++) {
                    setStat(stack, k, (i & ((int) Math.pow(2, k))) != 0);
                }

                result.add(stack);
            }
        }

        return result;
    }

    /** sticky, super_sticky, redstone, camou, redio */
    public static ItemStack getExtensionStack(boolean... stats) {
        ItemStack stack = BlockItemUtil.getStack(BlockData.extension.block);
        stack.stackTagCompound = BExtension.getDefaultNBT();
        for (int i = 0; i < stats.length; i++) {
            setStat(stack, i, stats[i]);
        }
        return stack;
    }

    /** sticky, super_sticky, redstone, camou, redio */
    public static ItemStack getExtensionPartStack(boolean... stats) {
        ItemStack stack = BlockItemUtil.getStack(BlockData.extensionPart.block);
        stack.stackTagCompound = BExtension.getDefaultNBT();
        for (int i = 0; i < stats.length; i++) {
            setStat(stack, i, stats[i]);
        }
        return stack;
    }

    public static boolean isPossibleCombination(int data) {
        return (data & 3) != 2 && (data & 17) != 16 && ((data & 20) != 16);
    }

    public static int getDataFor(ItemStack stack) {
        int result = 0;
        if (stack.stackTagCompound.getBoolean("sticky")) result = result | 1;
        if (stack.stackTagCompound.getBoolean("super_sticky")) result = result | 2;
        if (stack.stackTagCompound.getBoolean("redstone")) result = result | 4;
        if (stack.stackTagCompound.getBoolean("camou")) result = result | 8;
        if (stack.stackTagCompound.getBoolean("redio")) result = result | 16;
        return result;
    }

    public static ItemStack getExtension(int data) {
        ItemStack stack = BlockItemUtil.getStack(BlockData.extension);
        stack.stackTagCompound = BExtension.getDefaultNBT();
        for (int i = 0; i < STATS; i++) {
            setStat(stack, i, (data & ((int) Math.pow(2, i))) != 0);
        }
        return stack;
    }

    public static ItemStack getExtensionPart(int data) {
        ItemStack stack = BlockItemUtil.getStack(BlockData.extensionPart);
        stack.stackTagCompound = BExtension.getDefaultNBT();
        for (int i = 0; i < STATS; i++) {
            setStat(stack, i, (data & ((int) Math.pow(2, i))) != 0);
        }
        return stack;
    }

    public static void setStat(ItemStack stack, int index, boolean value) {
        switch (index) {
            case 0: stack.stackTagCompound.setBoolean("sticky", value); return;
            case 1: stack.stackTagCompound.setBoolean("super_sticky", value); return;
            case 2: stack.stackTagCompound.setBoolean("redstone", value); return;
            case 3: stack.stackTagCompound.setBoolean("camou", value); return;
            case 4: stack.stackTagCompound.setBoolean("redio", value); return;
        }
    }
}
