package letiu.modbase.render;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
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
	
	/**
     * Renders the block at the given coordinates using the block's rendering type
     */
    public boolean renderBlockByRenderType(Block p_147805_1_, int p_147805_2_, int p_147805_3_, int p_147805_4_)
    {
        int l = p_147805_1_.getRenderType();

        if (l == -1)
        {
            return false;
        }
        else
        {
            p_147805_1_.setBlockBoundsBasedOnState(this.blockAccess, p_147805_2_, p_147805_3_, p_147805_4_);
            this.setRenderBoundsFromBlock(p_147805_1_);
            
            switch (l)
            {
            //regex: ' : \(l == ([\d]+) \?' replace: ';\ncase \1: return' ::: IMPORTANT: REMEMBER THIS ON FIRST line!
            case 0 : return this.renderStandardBlock(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 4: return this.renderBlockLiquid(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 31: return this.renderBlockLog(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 1: return this.renderCrossedSquares(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 40: return this.renderBlockDoublePlant((BlockDoublePlant)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 2: return this.renderBlockTorch(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 20: return this.renderBlockVine(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 11: return this.renderBlockFence((BlockFence)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 39: return this.renderBlockQuartz(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 5: return this.renderBlockRedstoneWire(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 13: return this.renderBlockCactus(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 9: return this.renderBlockMinecartTrack((BlockRailBase)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 19: return this.renderBlockStem(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 23: return this.renderBlockLilyPad(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 6: return this.renderBlockCrops(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 3: return this.renderBlockFire((BlockFire)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 8: return this.renderBlockLadder(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 7: return this.renderBlockDoor(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 10: return this.renderBlockStairs((BlockStairs)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 27: return this.renderBlockDragonEgg((BlockDragonEgg)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 32: return this.renderBlockWall((BlockWall)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 12: return this.renderBlockLever(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 29: return this.renderBlockTripWireSource(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 30: return this.renderBlockTripWire(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 14: return this.renderBlockBed(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 15: return this.renderBlockRepeater((BlockRedstoneRepeater)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 36: return this.renderBlockRedstoneDiode((BlockRedstoneDiode)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 37: return this.renderBlockRedstoneComparator((BlockRedstoneComparator)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 16: return this.renderPistonBase(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_, false) ;
            case 17: return this.renderPistonExtension(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_, true) ;
            case 18: return this.renderBlockPane((BlockPane)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 41: return this.renderBlockStainedGlassPane(p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 21: return this.renderBlockFenceGate((BlockFenceGate)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 24: return this.renderBlockCauldron((BlockCauldron)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 33: return this.renderBlockFlowerpot((BlockFlowerPot)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 35: return this.renderBlockAnvil((BlockAnvil)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 25: return this.renderBlockBrewingStand((BlockBrewingStand)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 26: return this.renderBlockEndPortalFrame((BlockEndPortalFrame)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 28: return this.renderBlockCocoa((BlockCocoa)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 34: return this.renderBlockBeacon((BlockBeacon)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_) ;
            case 38: return this.renderBlockHopper((BlockHopper)p_147805_1_, p_147805_2_, p_147805_3_, p_147805_4_);
            // Using the FAKEWORLD for Custom Renderers!
            //default: return FMLRenderAccessLibrary.renderWorldBlock(this, fakeWorld, p_147805_2_, p_147805_3_, p_147805_4_, p_147805_1_, l);
            default: return RenderingRegistry.instance().renderWorldBlock(this, fakeWorld, p_147805_2_, p_147805_3_, p_147805_4_, p_147805_1_, l);
            }
        }
    }
	
}
