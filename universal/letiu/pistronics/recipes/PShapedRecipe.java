package letiu.pistronics.recipes;

import codechicken.nei.PositionedStack;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.pistronics.data.ItemData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.xml.transform.sax.SAXSource;
import java.util.ArrayList;

public class PShapedRecipe {

    private boolean mirror = false;

    private class PositionedStack {
        private ItemStack stack;
        private IStackComparator comparator;
        public int x, y;
        public PositionedStack(ItemStack stack, int x, int y) {
            this.stack = stack;
            this.x = x;
            this.y = y;
        }
        public PositionedStack(IStackComparator comparator, int x, int y) {
            this.comparator = comparator;
            this.x = x;
            this.y = y;
        }
        public boolean matches(ItemStack stack) {
            if (stack == null) return false;
            if (this.stack != null) {
                return CompareUtil.compare(this.stack, stack);
            }
            if (this.comparator != null) {
                return comparator.matches(stack);
            }
            return false;
        }
        public ItemStack getExampleStack() {
            if (stack != null) return stack;
            else return comparator.getExampleStack();
        }
    }

    private class Dimension2 {
        public int x, y;
        public Dimension2(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public ArrayList<Dimension2> getPossibleOffsetsForGrid(int gridWidth, int gridHeight) {
            ArrayList<Dimension2> result = new ArrayList<Dimension2>();
            for (int a = 0; a <= gridWidth - x; a++) {
                for (int b = 0; b <= gridHeight - y; b++) {
                    result.add(new Dimension2(a, b));
                }
            }
            return result;
        }
    }

    private ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
    private ItemStack result;

    public void addStack(ItemStack stack, int x, int y) {
        stacks.add(new PositionedStack(stack, x, y));
    }

    public void addStack(Block block, int x, int y) {
        stacks.add(new PositionedStack(BlockItemUtil.getStack(block), x, y));
    }

    public void addStack(Item item, int x, int y) {
        stacks.add(new PositionedStack(BlockItemUtil.getStack(item), x, y));
    }

    public void addStack(IStackComparator comparator, int x, int y) {
        stacks.add(new PositionedStack(comparator, x, y));
    }

    public void setMirror(boolean value) {
        this.mirror = value;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public boolean isIngredient(ItemStack stack) {
        for (PositionedStack posStack : stacks) {
            if (posStack.matches(stack)) return true;
        }
        return false;
    }

    public boolean isResult(ItemStack result) {
        return CompareUtil.compare(result, this.result);
    }

    public boolean matches(InventoryCrafting inv) {
        int invSize = inv.getSizeInventory() == 9 ? 3 : 2;
        ArrayList<Dimension2> offsets = calculateDimension().getPossibleOffsetsForGrid(invSize, invSize);
        for (Dimension2 offset : offsets) {
            if (doesOffsetMatch(offset, inv, false) || (mirror && doesOffsetMatch(offset, inv, true))) return true;
        }
        return false;
    }

    private boolean doesOffsetMatch(Dimension2 offset, InventoryCrafting inv, boolean doMirror) {
        int invSize = inv.getSizeInventory() == 4 ? 2 : 3;
        for (int x = 0; x < invSize; x++) {
            for (int y = 0; y < invSize; y++) {
                PositionedStack ingr = null;
                if (!doMirror) ingr = findStack(x - offset.x, y - offset.y);
                else ingr = findStack((invSize - 1 - x) - offset.x, y - offset.y);

                ItemStack invStack = inv.getStackInRowAndColumn(x, y);
                if (ingr == null && invStack == null) {
                    continue;
                }
                if (ingr == null || invStack == null) {
                    return false;
                }
                if (!(ingr.matches(invStack) || ItemReference.isSameOre(ingr.stack, invStack))) {
                    return false;
                }
            }
        }
        return true;
    }

    private PositionedStack findStack(int x, int y) {
        for (PositionedStack posStack : stacks) {
            if (posStack.x == x && posStack.y == y) return posStack;
        }
        return null;
    }

    public ItemStack getResult() {
        return result.copy();
    }

    private Dimension2 calculateDimension() {
        int maxX = 0, maxY = 0;
        for (PositionedStack posStack : stacks) {
            if (posStack.x > maxX) maxX = posStack.x;
            if (posStack.y > maxY) maxY = posStack.y;
        }
        return new Dimension2(++maxX, ++maxY);
    }

    public ShapedOreRecipe toShapedOreRecipe() {
        Dimension2 dimensions = calculateDimension();
        Object[] args = new Object[dimensions.y + stacks.size() * 2];
        int idx = 0;
        char id = 0;
        for (int y = 0; y < dimensions.y; y++) {
            String line = "";
            for (int x = 0; x < dimensions.x; x++) {
                PositionedStack stack = findStack(x, y);
                if (stack != null) {
                    line += id;
                    args[dimensions.y + idx++] = id++;
                    args[dimensions.y + idx++] = stack.getExampleStack();
                }
                else line += " ";;
            }
            args[y] = line;
        }

        return new ShapedOreRecipe(result, args);
    }
}
