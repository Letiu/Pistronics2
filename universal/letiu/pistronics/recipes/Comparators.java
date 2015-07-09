package letiu.pistronics.recipes;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.pistronics.blocks.BGear;
import letiu.pistronics.blocks.BRod;
import letiu.pistronics.data.BlockData;
import net.minecraft.item.ItemStack;

public class Comparators {

    private static IStackComparator gearComparator, rodComparator, rsRodComparator,
                                    rodPartComparator, rsRodPartComparator;

    public static IStackComparator getGearComparator() {
        if (gearComparator == null) {
            gearComparator = new IStackComparator() {
                @Override
                public boolean matches(ItemStack stack) {
                    if (!CompareUtil.compareIDs(stack, BlockData.gear.block)) return false;
                    if (stack.stackTagCompound == null) return false;
                    if (!stack.stackTagCompound.hasKey("size")) return false;
                    if (stack.stackTagCompound.getInteger("size") != 1) return false;
                    return true;
                }

                @Override
                public ItemStack getExampleStack() {
                    ItemStack stack = BlockItemUtil.getStack(BlockData.gear);
                    stack.stackTagCompound = BGear.getDefaultNBT();
                    return stack;
                }
            };
        }

        return gearComparator;
    }

    public static IStackComparator getRodComparator() {
        if (rodComparator == null) {
            rodComparator = new IStackComparator() {
                @Override
                public boolean matches(ItemStack stack) {
                    if (CompareUtil.compareIDs(stack, BlockData.rod.block)) {
                        if (stack.stackTagCompound != null && !stack.stackTagCompound.getBoolean("redstone")) {
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public ItemStack getExampleStack() {
                    ItemStack stack = BlockItemUtil.getStack(BlockData.rod);
                    stack.stackTagCompound = BRod.getDefaultNBT();
                    return stack;
                }
            };
        }

        return rodComparator;
    }

    public static IStackComparator getRsRodComparator() {
        if (rsRodComparator == null) {
            rsRodComparator = new IStackComparator() {
                @Override
                public boolean matches(ItemStack stack) {
                    if (CompareUtil.compareIDs(stack, BlockData.rod.block)) {
                        if (stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("redstone")) {
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public ItemStack getExampleStack() {
                    ItemStack stack = BlockItemUtil.getStack(BlockData.rod);
                    stack.stackTagCompound = BRod.getDefaultNBT();
                    stack.stackTagCompound.setBoolean("redstone", true);
                    return stack;
                }
            };
        }

        return rsRodComparator;
    }

    public static IStackComparator getRodPartComparator() {
        if (rodPartComparator == null) {
            rodPartComparator = new IStackComparator() {
                @Override
                public boolean matches(ItemStack stack) {
                    if (CompareUtil.compareIDs(stack, BlockData.rodPart.block)) {
                        if (stack.stackTagCompound != null && !stack.stackTagCompound.getBoolean("redstone")) {
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public ItemStack getExampleStack() {
                    ItemStack stack = BlockItemUtil.getStack(BlockData.rodPart);
                    stack.stackTagCompound = BRod.getDefaultNBT();
                    return stack;
                }
            };
        }

        return rodPartComparator;
    }

    public static IStackComparator getRsRodPartComparator() {
        if (rsRodPartComparator == null) {
            rsRodPartComparator = new IStackComparator() {
                @Override
                public boolean matches(ItemStack stack) {
                    if (CompareUtil.compareIDs(stack, BlockData.rodPart.block)) {
                        if (stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("redstone")) {
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public ItemStack getExampleStack() {
                    ItemStack stack = BlockItemUtil.getStack(BlockData.rodPart);
                    stack.stackTagCompound = BRod.getDefaultNBT();
                    stack.stackTagCompound.setBoolean("redstone", true);
                    return stack;
                }
            };
        }

        return rsRodPartComparator;
    }
}
