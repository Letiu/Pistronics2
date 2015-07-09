package letiu.pistronics.blocks;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.machines.BElementMachine;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
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

import java.util.ArrayList;
import java.util.List;

public class BStopper extends PBlock {

	public BStopper() {
		this.name = "Stopper";
		this.material = "rock";
		this.hardness = 5F;
		this.resistance = 15F;
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.STOPPER;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public PItem getItemBlock() {
		return ItemData.stopper;
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		return getTexture(side, meta);
	}

	@Override
	public String getTexture(int side, int meta) {
		return textures[0];
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
}
