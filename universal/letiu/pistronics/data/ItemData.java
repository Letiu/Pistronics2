package letiu.pistronics.data;

import java.util.ArrayList;

import letiu.modbase.core.ModClass;
import letiu.modbase.events.IArrowEventListener;
import letiu.pistronics.itemblocks.*;
import letiu.pistronics.items.*;

public class ItemData {

	// normal items //
	public static PItem glue, camoupaste, saw, pileOfRedstone, spade, super_glue, tool, redioGlue, redioSuperGlue, 
						petrifyArrow, petrifyExtract, bookOfGears, chisel;
	// itemblocks //
	public static PItem extension, extensionPart, rod, rodPart, slimeblock, sailPart, gear, statue, stopper;
	
	private static ArrayList<PItem> pItems, pItemBlocks;
	
	public static void init() {
		pItems = new ArrayList<PItem>();
		pItemBlocks = new ArrayList<PItem>();
		
		// init normal items //
		
		glue = new ItemGlue();
		pItems.add(glue);
		
		super_glue = new ItemSuperGlue();
		pItems.add(super_glue);
		
		camoupaste = new ItemCamoupaste();
		pItems.add(camoupaste);
		
		pileOfRedstone = new ItemPileOfRedstone();
		pItems.add(pileOfRedstone);
		
		tool = new ItemTool();
		pItems.add(tool);
		
		saw = new ItemSaw();
		pItems.add(saw);
		
		spade = new ItemSpade();
		pItems.add(spade);
		
		redioGlue = new ItemRedioGlue();
		pItems.add(redioGlue);
		
		redioSuperGlue = new ItemRedioSuperGlue();
		pItems.add(redioSuperGlue);
		
		petrifyArrow = new ItemPetrifyArrow();
		pItems.add(petrifyArrow);
		
		petrifyExtract = new ItemPetrifyExtract();
		pItems.add(petrifyExtract);

		bookOfGears = new ItemBookOfGears();
		pItems.add(bookOfGears);

        chisel = new ItemChisel();
        pItems.add(chisel);
		
		// Register EventListeners //
		ModClass.arrowEventHandler.addListener((IArrowEventListener) petrifyArrow);
		
		// init itemblocks //
		
		extension = new BIExtension();
		pItemBlocks.add(extension);
		
		extensionPart = new BIExtensionPart();
		pItemBlocks.add(extensionPart);
		
		rod = new BIRod();
		pItemBlocks.add(rod);
		
		rodPart = new BIRodPart();
		pItemBlocks.add(rodPart);
		
		slimeblock = new BISlimeblock();
		pItemBlocks.add(slimeblock);
		
		sailPart = new BISailPart();
		pItemBlocks.add(sailPart);
		
		gear = new BIGear();
		pItemBlocks.add(gear);
		
		statue = new BIStatue();
		pItemBlocks.add(statue);

        stopper = new BIStopper();
        pItemBlocks.add(stopper);
	}
	
	public static ArrayList<PItem> getItemData() {
		return pItems;
	}

	public static ArrayList<PItem> getItemBlockData() {
		return pItemBlocks;
	}
}
