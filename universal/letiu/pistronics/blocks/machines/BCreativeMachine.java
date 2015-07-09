package letiu.pistronics.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.core.ModClass;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.itemblocks.BITexted;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileCreativeMachine;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.CMRedstoneCommand;
import letiu.pistronics.util.FacingUtil;

public class BCreativeMachine extends BElementMachine {

	public BCreativeMachine() {
		this.name = "Creative Machine";
		this.material = "iron";
		this.hardness = 5F;
		this.resistance = 10F;
		this.creativeTab = true;
		this.textures = new String[6];
		this.textures[0] = Textures.ADV_PISTON_INNER;
		this.textures[1] = Textures.ADV_PISTON_BOTTOM;
		this.textures[2] = Textures.ADV_PISTON_SIDE;
		this.textures[3] = Textures.CAMOU_ADV_PISTON_INNER;
		this.textures[4] = Textures.CAMOU_ADV_PISTON_BOTTOM;
		this.textures[5] = Textures.CAMOU_ADV_PISTON_SIDE;
		this.blockIcon = this.textures[0];
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
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public PTile createPTile() {
		return new TileCreativeMachine();
	}
	
	@Override
	public PItem getItemBlock() {
		return new BITexted(EnumChatFormatting.LIGHT_PURPLE + "Creative only unless you enable the config option.");
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		if (!world.isRemote) {
			BlockProxy proxy = new BlockProxy(world, x, y, z);
			int facing = proxy.getFacing();
			TileCreativeMachine tile = (TileCreativeMachine) proxy.getPTile();
			
			if (tile.active) {
				if (!proxy.isPowered()) tile.active = false;
			}
			else if (!tile.isInMotion() && proxy.isPowered()) {
				tile.active = true;
				
				int strength = proxy.getPowerInput();
				
				CMRedstoneCommand command = tile.getCommand(strength);
				
				if (command != null) {
					if (!command.direction) facing = facing ^ 1;
					
					if (command.mode == CMRedstoneCommand.MOVE_MODE) {
						PistonSystem system = new PistonSystem(proxy, facing, command.getMoveSpeed(), SystemType.MOVE);
						system.tryMove();
					}
					else if (command.mode == CMRedstoneCommand.ROTATE_MODE) {
						PistonSystem system = new PistonSystem(proxy, facing, command.getRotateSpeed(), SystemType.ROTATE);
						system.tryRotate();
					}
				}
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
				TileCreativeMachine tile = (TileCreativeMachine) proxy.getPTile();
				
				if (!tile.isInMotion() && !tile.active) {
					
					CMRedstoneCommand command = tile.getCommand(0);
					
					if (command != null) {
						if (!command.direction) facing = facing ^ 1;
						
						if (command.mode == CMRedstoneCommand.MOVE_MODE) {
							PistonSystem system = new PistonSystem(proxy, facing, command.getMoveSpeed(), SystemType.MOVE);
							system.tryMove();
						}
						else if (command.mode == CMRedstoneCommand.ROTATE_MODE) {
							PistonSystem system = new PistonSystem(proxy, facing, command.getRotateSpeed(), SystemType.ROTATE);
							system.tryRotate();
						}
					}
				}
			}
			
			return true;
		}

		if (superResult) {
			return true;
		}
		else if (FacingUtil.getRelevantAxis(WorldUtil.getBlockFacing(world, x, y, z), xHit, yHit, zHit) <= 0.75F) {
			player.openGui(ModClass.instance, 0, world, x, y, z);
			return true;
		}
		
		return false;
	}
}
