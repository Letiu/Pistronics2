package letiu.pistronics.items;

import java.util.List;

import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BMotionblock;
import letiu.pistronics.blocks.BStatue;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.packets.StatueDataPacket;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileStatue;
import letiu.pistronics.util.BlockProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemTool extends PItem {

	public ItemTool() {
		this.name = "The Tool";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.TOOL;
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
		
		list.add(EnumChatFormatting.BLUE + "Use this to "
				+ EnumChatFormatting.GOLD + "rotate"
				+ EnumChatFormatting.BLUE + " any single block.");
		
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side, float xHit, float yHit, float zHit) {
		
		BlockProxy proxy = new BlockProxy(world, x, y, z);
//		proxy.move(1, 0.05F);
		PBlock block = proxy.getPBlock();
		if (block != null && block instanceof BStatue) {
			PTile tile = WorldUtil.getPTile(world, x, y, z);
			if (tile != null && tile instanceof TileStatue) {	
				((TileStatue) tile).rotate(player.isSneaking() ? -2 : 2);
                System.out.println(((TileStatue) tile).camouID);
                if (!world.isRemote) PacketHandler.sendToAllInDimension(new StatueDataPacket(((TileStatue) tile), player.dimension), player.dimension);
			}
			return true;
		}
		if (block != null && block instanceof BMotionblock) return true;
//		if (world.isRemote) return false;
		proxy.rotate(proxy.getCoords(), player.isSneaking() ? (side ^ 1) : side, (float) ConfigData.toolSpeed);
		
//		if (!player.isSneaking()) {
//			PistonSystem system = new PistonSystem(proxy, side, 0.05F, PistonSystem.MOVE);
//			System.out.println("System Size: " + system.getSystemSize());
//			system.tryMove();
//		}
//		else {
//			PistonSystem system = new PistonSystem(proxy, side, 2F, PistonSystem.ROTATE);
//			System.out.println("System Size: " + system.getSystemSize());
//			system.tryRotate();
//		}
//		
		return true;
	}
}
