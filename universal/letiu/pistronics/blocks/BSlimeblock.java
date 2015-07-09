package letiu.pistronics.blocks;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.machines.BElementMachine;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.IPistonElement;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.util.BlockProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import letiu.pistronics.data.PItem;

public class BSlimeblock extends PBlock implements IPistonElement {
	
	public BSlimeblock() {
		this.name = "Slimeblock";
		this.material = "rock";
		this.hardness = 3F;
		this.resistance = 10F;
		this.creativeTab = true;
		this.textures = new String[3];
		this.textures[0] = Textures.SLIME_BLOCK;
		this.textures[1] = Textures.GLUE_BLOCK;
		this.textures[2] = Textures.SUPER_GLUE_BLOCK;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public PItem getItemBlock() {
		return ItemData.slimeblock;
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		return getTexture(side, meta);
	}

	@Override
	public String getTexture(int side, int meta) {
		if (meta < 0 || meta >= textures.length) return textures[0];
		return textures[meta];
	}
	
	@Override
	public int getRenderID() {
		return ConfigData.opaqueSlimeblocks ? 0 : PRenderManager.getRenderID(PRenderManager.slimeblockRenderer);
	}
	
	@Override
	public boolean isOpaque() {
		return ConfigData.opaqueSlimeblocks;
	}
	
	public boolean renderAsNormalBlock() {
		return ConfigData.opaqueSlimeblocks;
	}
	
	@Override
	public int getRenderPass() {
		return ConfigData.opaqueSlimeblocks ? 0 : 1;
	}
	
	@Override
	public boolean getSubBlocks(int itemID, CreativeTabs tab, List list) {

		ItemStack stack;
		
		stack = BlockItemUtil.getStack(this, 1, 0);
		list.add(stack);
		
		stack = BlockItemUtil.getStack(this, 1, 1);
		list.add(stack);
		
		stack = BlockItemUtil.getStack(this, 1, 2);
		list.add(stack);
		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
	
		WorldUtil.setBlockMeta(world, x, y, z, stack.getItemDamage(), 3);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		
		return false;
	}

	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, boolean strongConnection) {
		system.addElement(proxy, strongConnection);
		
		for (int i = 0; i < 6; i++) {
			BlockProxy neighbor = proxy.getNeighbor(i);
			if (neighbor.isSolid()) {
				if (neighbor.isPistonElement()) {
					//
					// Special Machine behavior! 
					//
					if (neighbor.getPBlock() instanceof BElementMachine && neighbor.getFacing() == (i ^ 1)) {
						neighbor.getConnectedForSystem(system, false);
					}
					else {
						neighbor.getConnectedForSystem(system, true);
					}
				}
				else {
					system.addElement(neighbor, true);
				}
			}
		}
	}

	@Override
	public boolean connectsToSide(BlockProxy proxy, int side, SystemType type) {
		return true;
	}
	
	@Override
	public ItemStack getDroppedStack(PTile tile, int meta) {
		return BlockItemUtil.getStack(this, 1, meta);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
	
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		result.add(BlockItemUtil.getStack(this, 1, metadata));
		
		return result;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return BlockItemUtil.getStack(this, 1, WorldUtil.getBlockMeta(world, x, y, z));
	}

	@Override
	public boolean isTransmitter(BlockProxy proxy) {
		return false;
	}
}
