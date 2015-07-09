package letiu.pistronics.data;

import java.util.ArrayList;

import letiu.pistronics.blocks.*;
import letiu.pistronics.blocks.machines.BCreativeMachine;
import letiu.pistronics.blocks.machines.BPiston;
import letiu.pistronics.blocks.machines.BRodFolder;
import letiu.pistronics.blocks.machines.BRotator;

public class BlockData {

	public static PBlock piston, rotator, creativeMachine, rodFolder, rod, extension, camouBlock,
						 partBlock, rodPart, extensionPart, motionblock, slimeBlock, gear, gearPart,
						 sailPart, statue, stopper;
	
	private static ArrayList<PBlock> pBlocks;
	
	public static void init() {
		pBlocks = new ArrayList<PBlock>();
		
		piston = new BPiston();
		pBlocks.add(piston);
		
		rotator = new BRotator();
		pBlocks.add(rotator);
		
		creativeMachine = new BCreativeMachine();
		pBlocks.add(creativeMachine);
		
		rodFolder = new BRodFolder();
		pBlocks.add(rodFolder);
		
		rod = new BRod();
		pBlocks.add(rod);
		
		extension = new BExtension();
		pBlocks.add(extension);
		
		camouBlock = new BCamoublock();
		pBlocks.add(camouBlock);
		
		partBlock = new BPartblock();
		pBlocks.add(partBlock);
		
		rodPart = new BRodPart();
		pBlocks.add(rodPart);
		
		extensionPart = new BExtensionPart();
		pBlocks.add(extensionPart);
		
		motionblock = new BMotionblock();
		pBlocks.add(motionblock);
		
		slimeBlock = new BSlimeblock();
		pBlocks.add(slimeBlock);
		
		gear = new BGear();
		pBlocks.add(gear);
		
		gearPart = new BGearPart();
		pBlocks.add(gearPart);
		
		sailPart = new BSailPart();
		pBlocks.add(sailPart);
		
		statue = new BStatue();
		pBlocks.add(statue);

        stopper = new BStopper();
        pBlocks.add(stopper);
	}
	
	public static ArrayList<PBlock> getBlockData() {
		return pBlocks;
	}
}
