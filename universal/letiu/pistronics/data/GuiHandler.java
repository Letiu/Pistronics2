package letiu.pistronics.data;

import letiu.modbase.util.WorldUtil;
import letiu.pistronics.gui.ContainerCreativeMachine;
import letiu.pistronics.gui.ContainerRodfolder;
import letiu.pistronics.gui.GuiBookOfGears;
import letiu.pistronics.gui.GuiCreativeMachine;
import letiu.pistronics.gui.GuiRodfolder;
import letiu.pistronics.tiles.TileCreativeMachine;
import letiu.pistronics.tiles.TileRodFolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int BOOK_OF_GEARS_ID = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		
		if (tile instanceof TileRodFolder) {
			return new ContainerRodfolder(player.inventory, (TileRodFolder) tile);			
		}
		else if (tile instanceof TileCreativeMachine) {
			return new ContainerCreativeMachine(player.inventory, (TileCreativeMachine) tile);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if (ID == BOOK_OF_GEARS_ID) {
			if (x == -1) return new GuiBookOfGears(player);
			else return new GuiBookOfGears(world, x, y, z, player);
		}
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		
		if (tile instanceof TileRodFolder) {
			return new GuiRodfolder(player.inventory, (TileRodFolder) tile);
		}
		else if (tile instanceof TileCreativeMachine) {
			return new GuiCreativeMachine(player.inventory, (TileCreativeMachine) tile);
		}
		
		return null;
	}

}
