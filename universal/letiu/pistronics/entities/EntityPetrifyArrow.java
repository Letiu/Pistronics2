package letiu.pistronics.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import letiu.modbase.entities.EntityArrowOverwrite;
import letiu.modbase.network.PacketHandler;
import letiu.modbase.render.UniversalEntityRenderer;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.packets.SmokePacket;
import letiu.pistronics.render.PRenderManagerClient;
import letiu.pistronics.tiles.TileStatue;

public class EntityPetrifyArrow extends EntityArrowOverwrite {

	public EntityPetrifyArrow(World world, EntityLivingBase player, float speed) {
		super(world, player, speed);
	}
	
	public EntityPetrifyArrow(World world) {
        super(world);
    }

    public EntityPetrifyArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityPetrifyArrow(World world, EntityLivingBase entity1, EntityLivingBase entity2, float par4, float par5) {
        super(world, entity1, entity2, par4, par5);
    }

    @Override
    public UniversalEntityRenderer getRenderer() {
    	return PRenderManagerClient.entityPetrifyArrowRenderer;
    }
    
	@Override
	protected ItemStack getStackOnPickup() {
		return BlockItemUtil.getStack(ItemData.petrifyArrow);
	}
	
	@Override
	protected void onEntityLivingHit(EntityLivingBase target, NBTTagCompound nbt) {
		// PETRIFICATION //
  
    	if (target.getHealth() <= 0) {
    		World world = target.worldObj;
    		int x = (int)target.posX;
    		int y = (int)target.posY;
    		int z = (int)target.posZ;
    		
    		WorldUtil.setBlock(world, x, y, z, BlockData.statue.block);
    		
    		TileStatue tile = (TileStatue) WorldUtil.getPTile(world, x, y, z);
    		
    		EntityLivingBase statueEntity = null;
    		Class oclass = target.getClass();
    		
            if (oclass != null) {
            	try {
					statueEntity = (EntityLivingBase) oclass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldObj});
				} 
            	catch (Exception exception) {
		            exception.printStackTrace();
		        }
            }
    		
    		
    		//EntityLivingBase statueEntity = (EntityLivingBase) EntityList.createEntityFromNBT(nbt, worldObj);// (EntityLivingBase) EntityList.createEntityByID(EntityList.getEntityID(target), worldObj);
    		if (statueEntity != null) {
    			statueEntity.readEntityFromNBT(nbt);
        		tile.setEntity((EntityLivingBase) statueEntity);
        		
        		tile.setAngle((int) (-target.rotationYaw));
        		if (!world.isRemote && ConfigData.statueSmoke) PacketHandler.sendToAllInDimension(new SmokePacket(x, y, z, target.dimension), target.dimension);
        		
//        		this.worldObj.setBlockTileEntity(x, y, z, tile);
        		
//        		SmokePacket packet = new SmokePacket(x, y, z, target.dimension);
//        		PacketDispatcher.sendPacketToAllInDimension(packet.makePacket(), target.dimension);
    		}
    		else System.out.println("Entity was null!");
    		
    		
    	}
    	// ...  //
	}
	
}
