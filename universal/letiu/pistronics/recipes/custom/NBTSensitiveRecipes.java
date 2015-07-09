package letiu.pistronics.recipes.custom;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.ItemReference;
import letiu.pistronics.blocks.BExtension;
import letiu.pistronics.blocks.BRod;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.recipes.Comparators;
import letiu.pistronics.recipes.PRecipeRegistry;
import letiu.pistronics.recipes.PShapedRecipe;
import letiu.pistronics.recipes.PShapelessRecipe;
import letiu.pistronics.util.ExtensionUtil;
import net.minecraft.item.ItemStack;
import sun.org.mozilla.javascript.internal.ast.Block;

public class NBTSensitiveRecipes {

    public static void registerRecipes() {

        PShapedRecipe pistonRecipe = new PShapedRecipe();
        pistonRecipe.setResult(BlockItemUtil.getStack(BlockData.piston));
        pistonRecipe.addStack(ItemReference.WOODEN_SLAB, 0, 0);
        pistonRecipe.addStack(ItemReference.WOODEN_SLAB, 1, 0);
        pistonRecipe.addStack(ItemReference.WOODEN_SLAB, 2, 0);
        pistonRecipe.addStack(Comparators.getGearComparator(), 0, 1);
        pistonRecipe.addStack(ItemReference.PISTON, 1, 1);
        pistonRecipe.addStack(Comparators.getGearComparator(), 2, 1);
        pistonRecipe.addStack(ItemReference.PLANKS, 0, 2);
        pistonRecipe.addStack(ItemReference.REDSTONE_ITEM, 1, 2);
        pistonRecipe.addStack(ItemReference.PLANKS, 2, 2);
        PRecipeRegistry.registerShapedRecipe(pistonRecipe);

        PShapedRecipe rotatorRecipe = new PShapedRecipe();
        rotatorRecipe.setResult(BlockItemUtil.getStack(BlockData.rotator));
        rotatorRecipe.addStack(ItemReference.WOODEN_SLAB, 0, 0);
        rotatorRecipe.addStack(ItemReference.WOODEN_SLAB, 1, 0);
        rotatorRecipe.addStack(ItemReference.WOODEN_SLAB, 2, 0);
        rotatorRecipe.addStack(ItemReference.PISTON, 0, 1);
        rotatorRecipe.addStack(Comparators.getGearComparator(), 1, 1);
        rotatorRecipe.addStack(ItemReference.PISTON, 2, 1);
        rotatorRecipe.addStack(ItemReference.PLANKS, 0, 2);
        rotatorRecipe.addStack(ItemReference.REDSTONE_ITEM, 1, 2);
        rotatorRecipe.addStack(ItemReference.PLANKS, 2, 2);
        PRecipeRegistry.registerShapedRecipe(rotatorRecipe);

        PShapedRecipe rodfolderRecipe = new PShapedRecipe();
        rodfolderRecipe.setResult(BlockItemUtil.getStack(BlockData.rodFolder));
        rodfolderRecipe.addStack(ItemReference.WOODEN_SLAB, 0, 0);
        rodfolderRecipe.addStack(ItemReference.TRAP_DOOR, 1, 0);
        rodfolderRecipe.addStack(ItemReference.WOODEN_SLAB, 2, 0);
        rodfolderRecipe.addStack(Comparators.getGearComparator(), 0, 1);
        rodfolderRecipe.addStack(Comparators.getGearComparator(), 2, 1);
        rodfolderRecipe.addStack(ItemReference.PLANKS, 0, 2);
        rodfolderRecipe.addStack(ItemReference.PLANKS, 1, 2);
        rodfolderRecipe.addStack(ItemReference.PLANKS, 2, 2);
        PRecipeRegistry.registerShapedRecipe(rodfolderRecipe);

        if (ConfigData.creativeMachineRecipe) {
            PShapedRecipe cmRecipe = new PShapedRecipe();
            cmRecipe.setResult(BlockItemUtil.getStack(BlockData.creativeMachine));
            cmRecipe.addStack(ItemReference.IRON_INGOT, 0, 0);
            cmRecipe.addStack(ItemReference.IRON_INGOT, 1, 0);
            cmRecipe.addStack(ItemReference.IRON_INGOT, 2, 0);
            cmRecipe.addStack(ItemReference.PISTON, 0, 1);
            cmRecipe.addStack(Comparators.getGearComparator(), 1, 1);
            cmRecipe.addStack(ItemReference.PISTON, 2, 1);
            cmRecipe.addStack(ItemReference.DIAMOND, 0, 2);
            cmRecipe.addStack(ItemReference.REDSTONE_ITEM, 1, 2);
            cmRecipe.addStack(ItemReference.DIAMOND, 2, 2);
            PRecipeRegistry.registerShapedRecipe(cmRecipe);
        }

        PShapedRecipe toolRecipe = new PShapedRecipe();
        toolRecipe.setResult(BlockItemUtil.getStack(ItemData.tool));
        toolRecipe.addStack(ItemReference.STICK, 0, 0);
        toolRecipe.addStack(ItemReference.STICK, 2, 0);
        toolRecipe.addStack(Comparators.getGearComparator(), 1, 1);
        toolRecipe.addStack(Comparators.getRodComparator(), 1, 2);
        PRecipeRegistry.registerShapedRecipe(toolRecipe);

        PShapedRecipe sawRecipe = new PShapedRecipe();
        sawRecipe.setResult(BlockItemUtil.getStack(ItemData.saw));
        sawRecipe.addStack(ItemReference.STICK, 0, 0);
        sawRecipe.addStack(ItemReference.STICK, 1, 0);
        sawRecipe.addStack(Comparators.getRodComparator(), 2, 0);
        sawRecipe.addStack(ItemReference.IRON_INGOT, 0, 1);
        sawRecipe.addStack(ItemReference.IRON_INGOT, 1, 1);
        sawRecipe.addStack(Comparators.getRodComparator(), 2, 1);
        sawRecipe.setMirror(true);
        PRecipeRegistry.registerShapedRecipe(sawRecipe);

        PShapedRecipe spadeRecipe = new PShapedRecipe();
        spadeRecipe.setResult(BlockItemUtil.getStack(ItemData.spade));
        spadeRecipe.addStack(ItemReference.IRON_INGOT, 0, 0);
        spadeRecipe.addStack(ItemReference.IRON_INGOT, 0, 1);
        spadeRecipe.addStack(Comparators.getRodComparator(), 0, 2);
        PRecipeRegistry.registerShapedRecipe(spadeRecipe);

        PShapedRecipe chiselRecipe = new PShapedRecipe();
        chiselRecipe.setResult(BlockItemUtil.getStack(ItemData.chisel));
        chiselRecipe.addStack(ItemReference.IRON_INGOT, 0, 0);
        chiselRecipe.addStack(Comparators.getRodComparator(), 1, 1);
        chiselRecipe.setMirror(true);
        PRecipeRegistry.registerShapedRecipe(chiselRecipe);

        ////

        PShapedRecipe extensionRecipe = new PShapedRecipe();
        extensionRecipe.setResult(ExtensionUtil.getExtension(0));
        extensionRecipe.addStack(ItemReference.WOODEN_SLAB, 0, 0);
        extensionRecipe.addStack(Comparators.getRodComparator(), 0, 1);
        PRecipeRegistry.registerShapedRecipe(extensionRecipe);

        PShapedRecipe redstoneExtensionRecipe = new PShapedRecipe();
        redstoneExtensionRecipe.setResult(ExtensionUtil.getExtensionStack(false, false, true, false, false));
        redstoneExtensionRecipe.addStack(ItemReference.WOODEN_SLAB, 0, 0);
        redstoneExtensionRecipe.addStack(Comparators.getRsRodComparator(), 0, 1);
        PRecipeRegistry.registerShapedRecipe(redstoneExtensionRecipe);

        PShapedRecipe extensionPartRecipe = new PShapedRecipe();
        extensionPartRecipe.setResult(ExtensionUtil.getExtensionPart(0));
        extensionPartRecipe.addStack(ItemReference.WOODEN_SLAB, 0, 0);
        extensionPartRecipe.addStack(Comparators.getRodPartComparator(), 0, 1);
        PRecipeRegistry.registerShapedRecipe(extensionPartRecipe);

        PShapedRecipe redstoneExtensionPartRecipe = new PShapedRecipe();
        redstoneExtensionPartRecipe.setResult(ExtensionUtil.getExtensionPartStack(false, false, true, false, false));
        redstoneExtensionPartRecipe.addStack(ItemReference.WOODEN_SLAB, 0, 0);
        redstoneExtensionPartRecipe.addStack(Comparators.getRsRodPartComparator(), 0, 1);
        PRecipeRegistry.registerShapedRecipe(redstoneExtensionPartRecipe);

        //

        PShapedRecipe rodRecipe = new PShapedRecipe();
        ItemStack rod = BlockItemUtil.getStack(BlockData.rod);
        rod.stackTagCompound = BRod.getDefaultNBT();
        rod.stackSize = 6;
        rodRecipe.setResult(rod);
        rodRecipe.addStack(ItemReference.PLANKS, 1, 0);
        rodRecipe.addStack(ItemReference.IRON_INGOT, 1, 1);
        rodRecipe.addStack(ItemReference.PLANKS, 1, 2);
        PRecipeRegistry.registerShapedRecipe(rodRecipe);

        ItemStack simpleRod = rod.copy();
        simpleRod.stackSize = 1;

        ItemStack rsRod = simpleRod.copy();
        rsRod.stackTagCompound.setBoolean("redstone", true);
        PShapelessRecipe rsRodRecipe = new PShapelessRecipe(rsRod);
        rsRodRecipe.addIngredient(simpleRod);
        rsRodRecipe.addIngredient(BlockItemUtil.getStack(ItemReference.REDSTONE_ITEM));
        PRecipeRegistry.registerShapelessRecipe(rsRodRecipe);

        ItemStack rodParts = BlockItemUtil.getStack(BlockData.rodPart);
        rodParts.stackTagCompound = BRod.getDefaultNBT();
        rodParts.stackSize = 2;
        PRecipeRegistry.registerShapelessRecipe(new PShapelessRecipe(rodParts, simpleRod));

        ItemStack rsRodParts = rodParts.copy();
        rsRodParts.stackTagCompound.setBoolean("redstone", true);
        PRecipeRegistry.registerShapelessRecipe(new PShapelessRecipe(rsRodParts, rsRod));

        ItemStack simpleRodPart = rodParts.copy();
        simpleRodPart.stackSize = 1;

        ItemStack simpleRsRodPart = rsRodParts.copy();
        simpleRsRodPart.stackSize = 1;

        PRecipeRegistry.registerShapelessRecipe(new PShapelessRecipe(simpleRod, simpleRodPart, simpleRodPart));
        PRecipeRegistry.registerShapelessRecipe(new PShapelessRecipe(rsRod, simpleRsRodPart, simpleRsRodPart));

    }
}

