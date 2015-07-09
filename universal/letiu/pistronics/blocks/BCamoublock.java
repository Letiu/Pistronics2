package letiu.pistronics.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.itemblocks.BITexted;
import letiu.pistronics.piston.ISpecialRotator;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileCamoublock;
import letiu.pistronics.util.RotateUtil;
import letiu.pistronics.util.Vector3;

public class BCamoublock extends PBlock implements ISpecialRotator {
	
	public BCamoublock() {
		this.name = "Block of Camou";
		this.material = "rock";
		this.hardness = 3F;
		this.resistance = 10F;
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.CAMOU_BLOCK;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public PItem getItemBlock() {
		return new BITexted(EnumChatFormatting.ITALIC + "Hello I'm a Camoublock! :O",
							EnumChatFormatting.GOLD + "Rightclick " + EnumChatFormatting.BLUE + 
							"a side with any normal block",
							EnumChatFormatting.BLUE + "to change the texture.");
	}
	
	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		if (tile == null || !(tile instanceof TileCamoublock)) return textures[0]; 
		TileCamoublock tileCB = (TileCamoublock) tile;
		if (tileCB != null && tileCB.getCamouID(side) != -1) {
			return BaseBlock.BLOCK_PREFIX + "x" + tileCB.getCamouID(side) + "x" + tileCB.getCamouMeta(side);
		}
		return textures[0];
	}

	@Override
	public String getTexture(int meta, int side) {
		return this.textures[0];
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public PTile createPTile() {
		return new TileCamoublock();
	}

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		WorldUtil.setBlockFacing(world, x, y, z, player);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		
		TileCamoublock tile = WorldUtil.getTile(world, x, y, z, TileCamoublock.class);
		if (tile == null) return false;
		
		ItemStack stack = player.getCurrentEquippedItem();
		
		if (CompareUtil.compareIDs(stack, ItemData.spade.item)) {
			if (tile.getCamouID(side) != -1) {
				tile.setCamouID(side, -1);
				tile.setCamouMeta(side, 0);
			}
			WorldUtil.updateBlock(world, x, y, z);
			return true;
		}
		
		Block block = BlockItemUtil.getBlockFromStack(stack);
		if (block != null && block.getRenderType() == 0 && 
			BlockItemUtil.getBlockID(this.block) != tile.getCamouID(side)) {
			
			tile.setCamouID(side, BlockItemUtil.getBlockID(block));
			tile.setCamouMeta(side, stack.getItemDamage());
			WorldUtil.updateBlock(world, x, y, z);
			return true;
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
		
		if (!world.isRemote) {
			
			TileCamoublock tile = WorldUtil.getTile(world, x, y, z, TileCamoublock.class);
			
			if (tile != null) {
				int[] ids = new int[6];
				int[] metas = new int[6];
				for (int i = 0; i < 6; i++) {
					
					int newDir = RotateUtil.rotateDir(i, rotateDir);
					ids[newDir] = tile.getCamouID(i);
					metas[newDir] = tile.getCamouMeta(i);
				}
				for (int i = 0; i < 6; i++) {
					tile.setCamouID(i, ids[i]);
					tile.setCamouMeta(i, metas[i]);
				}
			}
		}
	}
}
