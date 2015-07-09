package letiu.pistronics.items;

import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PItem;
import letiu.pistronics.packets.StatueDataPacket;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileStatue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemChisel extends PItem {

	public ItemChisel() {
		this.name = "Chisel";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.CHISEL;
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
		
		list.add(EnumChatFormatting.BLUE + "Use this to edit the details on "
				+ EnumChatFormatting.GOLD + "statues"
				+ EnumChatFormatting.BLUE + ".");
	}

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xHit, float yHit, float zHit) {

        TileStatue tile = WorldUtil.getTile(world, x, y, z, TileStatue.class);

        if (tile != null && CompareUtil.compareIDs(stack, ItemData.chisel.item) && (tile.camouID != -1 || !tile.camou)) {
            if (player.isSneaking()) tile.decrementResolution();
            else tile.incrementResolution();
            if (!world.isRemote) PacketHandler.sendToAllInDimension(new StatueDataPacket(tile, player.dimension), player.dimension);
            return true;
        }

        return false;
    }
}
