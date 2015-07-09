package letiu.pistronics.blocks;

import java.util.ArrayList;
import java.util.List;

import letiu.modbase.network.PacketHandler;
import letiu.pistronics.packets.StatueDataPacket;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.ISpecialRotator;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileStatue;
import letiu.pistronics.util.Vector3;

public class BStatue extends PBlock implements ISpecialRotator {
	
	public BStatue() {
		this.name = "Statue";
		this.material = "rock";
		this.hardness = 5F;
		this.resistance = 10F;
		this.creativeTab = true;
		this.textures = new String[2];
		this.textures[0] = Textures.BOX;
		this.textures[1] = Textures.STATUE;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public PItem getItemBlock() {
		return ItemData.statue;
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		return textures[0];
	}

	@Override
	public String getTexture(int meta, int side) {
		return this.textures[1];
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public PTile createPTile() {
		return new TileStatue();
	}
	
	@Override
	public int getRenderID() {
		return ConfigData.renderStatuesAsBoxes ? 0 : -1;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		PTile pTile = WorldUtil.getPTile(world, x, y, z);
		if (pTile != null && pTile instanceof TileStatue) {
			TileStatue tile = (TileStatue) pTile;
			
			if (stack.stackTagCompound != null)
			tile.readFromNBT(stack.stackTagCompound);
			
			tile.setAngle((int) (-player.rotationYawHead + 180));
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		
		PTile pTile = WorldUtil.getPTile(world, x, y, z);
		
		if (pTile != null && pTile instanceof TileStatue) {
			TileStatue tile = (TileStatue) pTile;
			ItemStack stack = player.getCurrentEquippedItem();
			
			if (stack != null) {

				if (CompareUtil.compareIDs(stack, ItemData.camoupaste.item)) {
					tile.camou = true;
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
                    if (!world.isRemote) PacketHandler.sendToAllInDimension(new StatueDataPacket(tile, player.dimension), player.dimension);
					return true;
				}		
				
				if (CompareUtil.compareIDs(stack, ItemReference.ROTTEN_FLESH) 
					|| CompareUtil.compareIDs(stack, ItemReference.POISONOUS_POTATO)
					|| CompareUtil.compareIDs(stack, ItemReference.SPIDER_EYE)) {
					
					if (tile.decScale() && !player.capabilities.isCreativeMode) stack.stackSize--;
                    if (!world.isRemote) PacketHandler.sendToAllInDimension(new StatueDataPacket(tile, player.dimension), player.dimension);
					return true;
				}
				
				if (ItemReference.isFood(stack)) {
					
					if (tile.incScale() && !player.capabilities.isCreativeMode) stack.stackSize--;
                    if (!world.isRemote) PacketHandler.sendToAllInDimension(new StatueDataPacket(tile, player.dimension), player.dimension);
					return true;
				}
				
				if (tile.camou) {
					
					if (CompareUtil.compareIDs(stack, ItemReference.MILK)) {
						tile.camouID = -2;
						if (!player.capabilities.isCreativeMode) {
							player.inventory.setInventorySlotContents(player.inventory.currentItem, BlockItemUtil.getStack(ItemReference.BUCKET));
						}
                        if (!world.isRemote) PacketHandler.sendToAllInDimension(new StatueDataPacket(tile, player.dimension), player.dimension);
						return true;
					}
				
					if (CompareUtil.compareIDs(stack, ItemData.spade.item)) {
						if (tile.camouID == -1) {
							tile.camou = false;
							if (!world.isRemote) WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(ItemData.camoupaste));
						}
						
						tile.camouID = -1;
						tile.camouMeta = 0;
                        //tile.setStatueResolution(TileStatue.DEFAULT_RESOLUTION);
                        if (!world.isRemote) PacketHandler.sendToAllInDimension(new StatueDataPacket(tile, player.dimension), player.dimension);
						return true;
					}
					
					Block block = BlockItemUtil.getBlockFromStack(stack);
					if (block != null && block.getRenderType() == 0) {
						tile.tryCamou(block, stack.getItemDamage());
						WorldUtil.updateBlock(world, x, y, z);
                        if (!world.isRemote) PacketHandler.sendToAllInDimension(new StatueDataPacket(tile, player.dimension), player.dimension);
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean canRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
		return true;
	}

	@Override
	public void preRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
		
		if (rotateDir == 0) {
			PTile tile = WorldUtil.getPTile(world, x, y, z);
			if (tile != null && tile instanceof TileStatue) {
				((TileStatue) tile).rotate(90);
			}
		}
		else if (rotateDir == 1) {
			PTile tile = WorldUtil.getPTile(world, x, y, z);
			if (tile != null && tile instanceof TileStatue) {
				((TileStatue) tile).rotate(-90);
			}
		}
		
	}
	
	@Override
	public ItemStack getDroppedStack(PTile tile, int meta) {
		ItemStack stack = BlockItemUtil.getStack(BlockData.statue.block);
		if (tile != null && tile instanceof TileStatue) {
			stack.stackTagCompound = ((TileStatue) tile).getNBTForItem();
		}
		return stack;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		return drops;
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int fortune, EntityPlayer player) {

		ItemStack stack = player.getCurrentEquippedItem();
		
		if (!player.capabilities.isCreativeMode || ItemReference.isHarvestTool(stack)) {
			WorldUtil.spawnItemStack(world, x, y, z, getDroppedStack(WorldUtil.getPTile(world, x, y, z), 0));
		}
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return getDroppedStack(WorldUtil.getPTile(world, x, y, z), 0);
	}
	
	@Override
	public boolean shouldUseDefaultPick() {
		return false;
	}
	
	@Override
	public boolean getSubBlocks(int itemID, CreativeTabs tab, List list) {
		
		ItemStack stack;
		
		stack = BlockItemUtil.getStack(this);
		stack.stackTagCompound = getDefaultNBT();
		list.add(stack);
		
		return true;
	}
	
	public static NBTTagCompound getDefaultNBT() {
		NBTTagCompound tagCompound = NBTUtil.getNewCompound();
		
		tagCompound.setBoolean("oriTex", false);
		tagCompound.setBoolean("camou", false);
		tagCompound.setInteger("camouID", -1);
		tagCompound.setInteger("camouMeta", 0);
        tagCompound.setInteger("resolution", TileStatue.DEFAULT_RESOLUTION);
		
		tagCompound.setInteger("scale", 100);
		
		return tagCompound;
	}
}
