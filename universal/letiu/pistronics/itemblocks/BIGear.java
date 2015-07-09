package letiu.pistronics.itemblocks;

import java.util.List;

import org.lwjgl.input.Keyboard;

import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BGear;
import letiu.pistronics.packets.SmokePacket;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.tiles.TileGear;
import letiu.pistronics.util.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import letiu.pistronics.data.PItemBlock;

public class BIGear extends PItemBlock {

	private static TileGear tile = new TileGear();
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt != null) {
			
			int size = nbt.getInteger("size");
			
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				list.add(EnumChatFormatting.ITALIC + "Press SHIFT for more.");
				list.add("Size: " + size);
			}
			else {
				list.add("Gears are crafted by rightclicking");
				list.add("the center of a plank cuboid with a");
				list.add("saw.");
				list.add("Size 1: 1x1x1");
				list.add("Size 3: 3x1x3");
				list.add("Size 5: 5x1x5");
			}
		}
	}
	
	@Override
	public int getSpriteNumber() {
		return 0;
	}
	
	@Override
	public String getIcon(ItemStack stack, int pass) {
		return Textures.EXTENSION_NORMAL;
	}
	
	@Override
	public PItemRenderer getSpecialRenderer() {
		return PRenderManager.gearItemRenderer;
	}
	
	@Override
	public int getMaxStackSize() {
		return 8;
	}
	
	@Override
	public boolean overwritesPlaceBlockAt() {
		return true;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ, int metadata) {	
		
		if (stack.stackTagCompound == null) return false;
		int size = stack.stackTagCompound.getInteger("size");
		
		Vector3 pos = new Vector3(x, y, z);
		Vector3 offsetA = null, offsetB = null;
		
		if (side == 0 || side == 1) {
			pos.x -= (size / 2);
			pos.z -= (size / 2);
			offsetA = Vector3.X_AXIS;
			offsetB = Vector3.Z_AXIS;
		}
		else if (side == 2 || side == 3) {
			pos.x -= (size / 2);
			pos.y -= (size / 2);
			offsetA = Vector3.X_AXIS;
			offsetB = Vector3.Y_AXIS;
		}
		else if (side == 4 || side == 5) {
			pos.y -= (size / 2);
			pos.z -= (size / 2);
			offsetA = Vector3.Y_AXIS;
			offsetB = Vector3.Z_AXIS;
		}
		
		for (int a = 0; a < size; a++) {
			for (int b = 0; b < size; b++) {
				int xPos = pos.x + offsetA.x * a + offsetB.x * b;
				int yPos = pos.y + offsetA.y * a + offsetB.y * b;
				int zPos = pos.z + offsetA.z * a + offsetB.z * b;
				if (!WorldUtil.isBlockReplaceable(world, xPos, yPos, zPos)) {
					if (!world.isRemote) {
						PacketHandler.sendToAllInDimension(new SmokePacket(xPos, yPos, zPos, player.dimension), player.dimension);
					}
					return false; 
				}
			}
		}
		
		BGear.createGearBlock(world, x, y, z, side, size, stack.stackTagCompound.getInteger("meta"), true);
		return true;
	}
}
