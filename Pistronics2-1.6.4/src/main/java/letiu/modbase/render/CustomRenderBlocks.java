package letiu.modbase.render;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockComparator;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneLogic;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.src.FMLRenderAccessLibrary;
import net.minecraft.world.IBlockAccess;

public class CustomRenderBlocks extends RenderBlocks {

	private FakeWorld fakeWorld;
	
	public CustomRenderBlocks(IBlockAccess blockAccess, FakeWorld fakeWorld) {
		super(blockAccess);
		this.fakeWorld = fakeWorld;
		this.blockAccess = fakeWorld;
	}
	
	@Override
	public boolean renderBlockByRenderType(Block par1Block, int par2, int par3, int par4)
    {
        int l = par1Block.getRenderType();

        if (l == -1)
        {
            return false;
        }
        else
        {
            par1Block.setBlockBoundsBasedOnState(fakeWorld, par2, par3, par4);
            this.setRenderBoundsFromBlock(par1Block);

            switch (l)
            {
                //regex: ' : \(l == ([\d]+) \?' replace: ';\ncase \1: return' ::: IMPORTANT: REMEMBER THIS ON FIRST line!
                case 0: return this.renderStandardBlock(par1Block, par2, par3, par4);
                case 4: return this.renderBlockFluids(par1Block, par2, par3, par4);
                case 31: return this.renderBlockLog(par1Block, par2, par3, par4);
                case 1: return this.renderCrossedSquares(par1Block, par2, par3, par4);
                case 2: return this.renderBlockTorch(par1Block, par2, par3, par4);
                case 20: return this.renderBlockVine(par1Block, par2, par3, par4);
                case 11: return this.renderBlockFence((BlockFence)par1Block, par2, par3, par4);
                case 39: return this.renderBlockQuartz(par1Block, par2, par3, par4);
                case 5: return this.renderBlockRedstoneWire(par1Block, par2, par3, par4);
                case 13: return this.renderBlockCactus(par1Block, par2, par3, par4);
                case 9: return this.renderBlockMinecartTrack((BlockRailBase)par1Block, par2, par3, par4);
                case 19: return this.renderBlockStem(par1Block, par2, par3, par4);
                case 23: return this.renderBlockLilyPad(par1Block, par2, par3, par4);
                case 6: return this.renderBlockCrops(par1Block, par2, par3, par4);
                case 3: return this.renderBlockFire((BlockFire)par1Block, par2, par3, par4);
                case 8: return this.renderBlockLadder(par1Block, par2, par3, par4);
                case 7: return this.renderBlockDoor(par1Block, par2, par3, par4);
                case 10: return this.renderBlockStairs((BlockStairs)par1Block, par2, par3, par4);
                case 27: return this.renderBlockDragonEgg((BlockDragonEgg)par1Block, par2, par3, par4);
                case 32: return this.renderBlockWall((BlockWall)par1Block, par2, par3, par4);
                case 12: return this.renderBlockLever(par1Block, par2, par3, par4);
                case 29: return this.renderBlockTripWireSource(par1Block, par2, par3, par4);
                case 30: return this.renderBlockTripWire(par1Block, par2, par3, par4);
                case 14: return this.renderBlockBed(par1Block, par2, par3, par4);
                case 15: return this.renderBlockRepeater((BlockRedstoneRepeater)par1Block, par2, par3, par4);
                case 36: return this.renderBlockRedstoneLogic((BlockRedstoneLogic)par1Block, par2, par3, par4);
                case 37: return this.renderBlockComparator((BlockComparator)par1Block, par2, par3, par4);
                case 16: return this.renderPistonBase(par1Block, par2, par3, par4, false);
                case 17: return this.renderPistonExtension(par1Block, par2, par3, par4, true);
                case 18: return this.renderBlockPane((BlockPane)par1Block, par2, par3, par4);
                case 21: return this.renderBlockFenceGate((BlockFenceGate)par1Block, par2, par3, par4);
                case 24: return this.renderBlockCauldron((BlockCauldron)par1Block, par2, par3, par4);
                case 33: return this.renderBlockFlowerpot((BlockFlowerPot)par1Block, par2, par3, par4);
                case 35: return this.renderBlockAnvil((BlockAnvil)par1Block, par2, par3, par4);
                case 25: return this.renderBlockBrewingStand((BlockBrewingStand)par1Block, par2, par3, par4);
                case 26: return this.renderBlockEndPortalFrame((BlockEndPortalFrame)par1Block, par2, par3, par4);
                case 28: return this.renderBlockCocoa((BlockCocoa)par1Block, par2, par3, par4);
                case 34: return this.renderBlockBeacon((BlockBeacon)par1Block, par2, par3, par4);
                case 38: return this.renderBlockHopper((BlockHopper)par1Block, par2, par3, par4);
                // Using the FAKEWORLD for Custom Renderers!
//                default: return FMLRenderAccessLibrary.renderWorldBlock(this, fakeWorld, par2, par3, par4, par1Block, l);
                default: return RenderingRegistry.instance().renderWorldBlock(this, fakeWorld, par2, par3, par4, par1Block, l);
            }
        }
    }
	
	
}
