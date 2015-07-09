package letiu.pistronics.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.itemblocks.BITexted;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.tiles.TileMechPiston;
import letiu.pistronics.util.BlockProxy;

public class BPiston extends BElementMachine {
	
	public BPiston() {
		this.name = "Mechanic Piston";
		this.material = "wood";
		this.hardness = 3F;
		this.resistance = 10F;
		this.creativeTab = true;
		this.textures = new String[6];
		this.textures[0] = Textures.MECH_PISTON_INNER;
		this.textures[1] = Textures.MECH_PISTON_BOTTOM;
		this.textures[2] = Textures.MECH_PISTON_SIDE;
		this.textures[3] = Textures.CAMOU_PISTON_INNER;
		this.textures[4] = Textures.CAMOU_PISTON_BOTTOM;
		this.textures[5] = Textures.CAMOU_PISTON_SIDE;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public PTile createPTile() {
		return new TileMechPiston();
	}
	
	@Override
	public PItem getItemBlock() {
		return new BITexted(EnumChatFormatting.ITALIC + "Hello I'm a Piston! :D",
							EnumChatFormatting.GOLD + "Rightclick " + EnumChatFormatting.BLUE + 
							"me with an " + EnumChatFormatting.GOLD + "extension " + EnumChatFormatting.BLUE 
							+ "or " + EnumChatFormatting.GOLD + "rod" + EnumChatFormatting.BLUE + ".",
							EnumChatFormatting.BLUE + "Don't forget to place something behind me.");
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		int offset = 0;
		if (tile != null && tile instanceof TileMech) {
			TileMech tileMech = (TileMech) tile;
			if (tileMech.camou) {
				if (tileMech.camouID != -1) {
					return BaseBlock.BLOCK_PREFIX + "x" + tileMech.camouID + "x" + tileMech.camouMeta;
				}
				else offset += 3;
			}
		}
		meta = meta & 7;
		if (side == meta) return textures[offset + 0];
		else if (side == (meta ^ 1)) return textures[offset + 1];
		else return textures[offset + 2]; 
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		if (!world.isRemote) {
			BlockProxy proxy = new BlockProxy(world, x, y, z);
			int facing = proxy.getFacing();
			TileMech tile = (TileMech) proxy.getPTile();
			
			if (tile.active) {
				if (!proxy.isPowered()) tile.active = false;
			}
			else if (!tile.isMoving() && proxy.isPowered()) {
				tile.active = true;
				PistonSystem system = new PistonSystem(proxy, facing, 0.05F, SystemType.MOVE);
				system.tryMove();
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		boolean superResult = super.onBlockActivated(world, x, y, z, player, side, xHit, yHit, zHit);
		
		ItemStack stack = player.getCurrentEquippedItem();
		if (CompareUtil.compareIDs(stack, ItemReference.REDSTONE_TORCH)) {
			
			if (!world.isRemote) {
				BlockProxy proxy = new BlockProxy(world, x, y, z);
				int facing = proxy.getFacing();
				TileMech tile = (TileMech) proxy.getPTile();
				
				if (!tile.isMoving() && !tile.active) {
					PistonSystem system = new PistonSystem(proxy, facing, 0.05F, SystemType.MOVE);
					system.tryMove();
				}
			}
			
			return true;
		}
		
		return superResult;
	}

}
