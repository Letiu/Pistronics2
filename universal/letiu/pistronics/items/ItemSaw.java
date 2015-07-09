package letiu.pistronics.items;

import java.util.List;

import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BGear;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PItem;
import letiu.pistronics.packets.SmokePacket;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileSail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemSaw extends PItem {

	public ItemSaw() {
		this.name = "Saw";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.SAW;
	}

	@Override
	public String getIcon(ItemStack stack, int pass) {
		return this.textures[0];
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		list.add(EnumChatFormatting.BLUE + "Useable on "
				+ EnumChatFormatting.GOLD + "rods"
				+ EnumChatFormatting.BLUE + ", "
				+ EnumChatFormatting.GOLD + "extension");
		list.add(EnumChatFormatting.BLUE + "and "
				+ EnumChatFormatting.GOLD + "partblocks"
				+ EnumChatFormatting.BLUE + ".");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side, float xHit, float yHit, float zHit) {
		 
		if (CompareUtil.compareIDs(WorldUtil.getBlock(world, x, y, z), ItemReference.WOOL)) {
			if (!world.isRemote) {
				int meta = WorldUtil.getBlockMeta(world, x, y, z);
				WorldUtil.setBlockToAir(world, x, y, z);
				ItemStack sailStack = BlockItemUtil.getStack(BlockData.sailPart, 8, 0);
				
				TileSail sailTile = new TileSail();
				sailTile.color = meta;
				sailStack.stackTagCompound = sailTile.getNBTForItem();
				
				WorldUtil.spawnItemStack(world, x, y, z, sailStack);
				
				PacketHandler.sendToAllInDimension(new SmokePacket(x, y, z, player.dimension, true), player.dimension);
			}
			return true;
		}
		
		if (side == 0 || side == 1) {
			int gearSize = getPossibleGearSizeUD(world, x, y, z);
			if (gearSize != 0) {
				BGear.createGearBlock(world, x, y, z, side, gearSize, WorldUtil.getBlockMeta(world, x, y, z), true);
				PacketHandler.sendToAllInDimension(new SmokePacket(x, y, z, player.dimension, true), player.dimension);
				return true;
			}
		}
		else if (side == 2 || side == 3) {
			int gearSize = getPossibleGearSizeEW(world, x, y, z);
			if (gearSize != 0) {
				BGear.createGearBlock(world, x, y, z, side, gearSize, WorldUtil.getBlockMeta(world, x, y, z), true);
				PacketHandler.sendToAllInDimension(new SmokePacket(x, y, z, player.dimension, true), player.dimension);
				return true;
			}
		}
		else if (side == 4 || side == 5) {
			int gearSize = getPossibleGearSizeNS(world, x, y, z);
			if (gearSize != 0) {
				BGear.createGearBlock(world, x, y, z, side, gearSize, WorldUtil.getBlockMeta(world, x, y, z), true);
				PacketHandler.sendToAllInDimension(new SmokePacket(x, y, z, player.dimension, true), player.dimension);
				return true;
			}
		}
		
		return false;
	}
	
	private int getPossibleGearSizeUD(World world, int x, int y, int z) {
		int size = 0;
		
		if (CompareUtil.compareIDs(WorldUtil.getBlock(world, x, y, z), ItemReference.PLANKS)) {
			int meta = WorldUtil.getBlockMeta(world, x, y, z);
			size = 1;
			for (int a = -1; a < 2; a++) {
				for (int b = -1; b < 2; b++) {
					if (b == 0) continue;
					if (WorldUtil.getBlockMeta(world, x + a, y, z + b) != meta
							|| !CompareUtil.compareIDs(WorldUtil.getBlock(world, x + a, y, z + b), ItemReference.PLANKS)) {
						return size;
					}
				}
			}
			size = 3;
			for (int a = -2; a < 3; a++) {
				for (int b = -2; b < 3; b++) {
					if (b > -2 && b < 2) continue;
					if (WorldUtil.getBlockMeta(world, x + a, y, z + b) != meta
							|| !CompareUtil.compareIDs(WorldUtil.getBlock(world, x + a, y, z + b), ItemReference.PLANKS)) {
						return size;
					}
				}
			}
			size = 5;
		}
		
		return size;
	}
	
	private int getPossibleGearSizeEW(World world, int x, int y, int z) {
		int size = 0;
		
		if (CompareUtil.compareIDs(WorldUtil.getBlock(world, x, y, z), ItemReference.PLANKS)) {
			int meta = WorldUtil.getBlockMeta(world, x, y, z);
			size = 1;
			for (int a = -1; a < 2; a++) {
				for (int b = -1; b < 2; b++) {
					if (b == 0) continue;
					if (WorldUtil.getBlockMeta(world, x + a, y + b, z) != meta
							|| !CompareUtil.compareIDs(WorldUtil.getBlock(world, x + a, y + b, z), ItemReference.PLANKS)) {
						return size;
					}
				}
			}
			size = 3;
			for (int a = -2; a < 3; a++) {
				for (int b = -2; b < 3; b++) {
					if (b > -2 && b < 2) continue;
					if (WorldUtil.getBlockMeta(world, x + a, y + b, z) != meta
							|| !CompareUtil.compareIDs(WorldUtil.getBlock(world, x + a, y + b, z), ItemReference.PLANKS)) {
						return size;
					}
				}
			}
			size = 5;
		}
		
		return size;
	}
	
	private int getPossibleGearSizeNS(World world, int x, int y, int z) {
		int size = 0;
		
		if (CompareUtil.compareIDs(WorldUtil.getBlock(world, x, y, z), ItemReference.PLANKS)) {
			int meta = WorldUtil.getBlockMeta(world, x, y, z);
			size = 1;
			for (int a = -1; a < 2; a++) {
				for (int b = -1; b < 2; b++) {
					if (b == 0) continue;
					if (WorldUtil.getBlockMeta(world, x, y + a, z + b) != meta
							|| !CompareUtil.compareIDs(WorldUtil.getBlock(world, x, y + a, z + b), ItemReference.PLANKS)) {
						return size;
					}
				}
			}
			size = 3;
			for (int a = -2; a < 3; a++) {
				for (int b = -2; b < 3; b++) {
					if (b > -2 && b < 2) continue;
					if (WorldUtil.getBlockMeta(world, x, y + a, z + b) != meta
							|| !CompareUtil.compareIDs(WorldUtil.getBlock(world, x, y + a, z + b), ItemReference.PLANKS)) {
						return size;
					}
				}
			}
			size = 5;
		}
		
		return size;
	}
}
