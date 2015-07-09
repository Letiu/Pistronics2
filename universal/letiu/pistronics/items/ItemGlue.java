package letiu.pistronics.items;

import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.PItem;
import letiu.pistronics.piston.SystemController;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileStatue;
import letiu.pistronics.util.RotateUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemGlue extends PItem {

	public ItemGlue() {
		this.name = "Glue";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.GLUE;
	}

	@Override
	public String getIcon(ItemStack stack, int pass) {
		return this.textures[0];
	}
	
	public static SystemController controller = null;
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side, float xHit, float yHit, float zHit) {

          System.out.println(WorldUtil.getBlockMeta(world, x, y, z));
		  //RotateUtil.rotateVanillaBlocks(world, x, y, z, 1);

//        ItemStack invStack = player.inventory.getStackInSlot(0);
//        System.out.println(invStack);
//        System.out.println(invStack.getItemDamage());
//        System.out.println(invStack.stackTagCompound);
//        ItemStack piston = BlockItemUtil.getStack(ItemReference.PISTON);
//        System.out.println(piston);
//        System.out.println(piston.getItemDamage());
//        System.out.println(piston.stackTagCompound);
//        System.out.println(ItemStack.areItemStacksEqual(piston, invStack));



//		Map<String,Integer> map = GameData.buildItemDataList();
//		
//		for (String s : map.keySet()) {
//			System.out.println(s + "|" + map.get(s));
//		}
//		
//		System.out.println(GameRegistry.findBlock("Pistronics2", "Rod Block"));
//		System.out.println(GameRegistry.findBlock("minecraft", "furnace"));
		
//		System.out.println(player.inventory.getStackInSlot(0).getItem());
//		System.out.println(GameRegistry.findBlock("minecraft", "stone"));

		
//		System.out.println(side);
//		
//		Block block = WorldUtil.getBlock(world, x, y, z);
//		System.out.println("RenderType: " + block.getRenderType());
//		System.out.println(block.getBlockBoundsMinX());
//		System.out.println(block.getBlockBoundsMinY());
//		System.out.println(block.getBlockBoundsMinZ());
//		System.out.println(block.getBlockBoundsMaxX());
//		System.out.println(block.getBlockBoundsMaxY());
//		System.out.println(block.getBlockBoundsMaxZ());
		
		//System.out.println(FMLCommonHandler.instance().getSide());
//		
//		if (!world.isRemote) {
////			System.out.println(WorldUtil.getBlockMeta(world, x, y, z));
//			System.out.println(x + "/" + y + "/" + z);
//		}
		
//		if (!world.isRemote) {
//			BaseEntity entity = new EntityGear(world, x, y + 1, z);
//			world.spawnEntityInWorld(entity);
//		}
		
		//		
//		System.out.println("-----");
//		System.out.println("isRemote: " + world.isRemote);
//		System.out.println("isEntityPlayerMP: " + (player instanceof EntityPlayerMP));
//		System.out.println("FMLSide: " + (FMLCommonHandler.instance().getEffectiveSide()));
//		System.out.println("-----");
		
//		WorldUtil.updateBlock(world, x, y, z);
		
//		BlockProxy proxy = new BlockProxy(world, x, y, z);
//		
//		if (!world.isRemote) {
//			System.out.println(proxy.getBlockName());
//			System.out.println(proxy.getBlock().getLocalizedName());
//		}
//		
		
//		System.out.println(proxy.getMetadata());
		
//		proxy.move(2, 0.05F);
//		
//		if (proxy.isPistonElement()) {
//			PistonSystem system = new PistonSystem(proxy, proxy.getFacing(), 0, SystemType.MOVE);
//			System.out.println(system.getSystemSize());
//		}
		
//		PTile tile = proxy.getPTile();
//		
//		if (!world.isRemote && player.isSneaking()) {
//			System.out.println(proxy);
//		}
//		
//		if (tile instanceof ITransmitter && !world.isRemote) {
//			System.out.println("Is input: " + ((ITransmitter) tile).isInput());
//		}
		
		//if (!world.isRemote) System.out.println(RedstoneUtil.getNeighborStates(proxy));
		
//		System.out.println("CanStay? " + WorldUtil.canBlockStayWithout(world, x, y, z, 2));
		
		
//		BlockTorch
		
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		
		list.add(EnumChatFormatting.ITALIC + "Somehow this turned out to be sticky.");
	}
}
