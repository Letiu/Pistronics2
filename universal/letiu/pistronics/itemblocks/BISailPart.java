package letiu.pistronics.itemblocks;

import java.util.List;

import org.lwjgl.input.Keyboard;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BRodPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.IPart;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.ColorReference;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.render.PItemRenderer;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.tiles.TileSail;
import letiu.pistronics.util.BlockProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import letiu.pistronics.data.PItemBlock;

public class BISailPart extends PItemBlock implements IPart {

	private static TileSail tile = new TileSail();
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt != null) {
			
			
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				list.add(EnumChatFormatting.ITALIC + "Press SHIFT for more.");
				list.add("Color: " +  ColorReference.colorNames[nbt.getInteger("color")]);
				
				if (nbt.getBoolean("camou")) {
					String tex = "None";
					int camouID = nbt.getInteger("camouID");
					if (camouID != -1) {
						Block block = BlockItemUtil.getBlockByID(camouID);
						tex = block.getLocalizedName();
					}
					
					list.add(EnumChatFormatting.AQUA + "Camou: " + tex);
				}
			}
			else {
				list.add("Sails are crafted by rightclicking");
				list.add("wool with a saw.");
			}
		}
	}
	
	@Override
	public int getSpriteNumber() {
		return 0;
	}
	
	@Override
	public String getIcon(ItemStack stack, int pass) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt == null) return Textures.EXTENSION_NORMAL;
		
		tile.readFromNBT(nbt);
		return BlockData.sailPart.getTextureIndex(tile, 3, pass);
	}
	
	@Override
	public PItemRenderer getSpecialRenderer() {
		return PRenderManager.sailItemRenderer;
	}

	@Override
	public boolean onPartActivated(World world, int x, int y, int z,
			EntityPlayer player, int part, int side) {
		return false;
	}
	
	@Override
	public boolean canPartBeAdded(IBlockAccess world, int x, int y, int z, int side) {
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		if (tile != null) {
			if (tile instanceof TileRod) return true;
			if (tile instanceof TilePartblock) {
				TilePartblock tilePartblock = (TilePartblock) tile;
				for (int i = 0; i < 6; i += 2) {
					if (i != side && i != (side ^ 1) 
						&& tilePartblock.getPart(i) instanceof BRodPart 
						&& tilePartblock.getPart(i ^ 1) instanceof BRodPart) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy,
			TilePartblock tile, int part) {
	}
	
	@Override
	public boolean connectsToSide(BlockProxy proxy, TilePartblock tile, int part,
			int side, SystemType type) {
		return false;
	}
	
	@Override
	public PBlock getPartBlock() {
		return BlockData.sailPart;
	}

	@Override
	public void onPartPlaced(TilePartblock tilePartblock, PTile tile, int part,
			int partClicked, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRotate(TilePartblock tilePartblock, PTile tile, int part,
			int rotateDir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canPartStay(IBlockAccess world, int x, int y, int z, int side) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
