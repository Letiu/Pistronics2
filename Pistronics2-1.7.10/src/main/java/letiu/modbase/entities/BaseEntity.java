package letiu.modbase.entities;

import letiu.modbase.render.UniversalEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BaseEntity extends Entity {

	public BaseEntity(World world) {
		super(world);

//		this.motionX = 0.0D;
//        this.motionY = 0.0D;
//        this.motionZ = 0.0D;
//        this.preventEntitySpawning = true;
//        this.setSize(1F, 1F);
//        this.noClip = true;
	}
	
	public BaseEntity(World world, double x, double y, double z) {
		super(world);
//		this.setPosition(x, y, z);
//		this.prevPosX = x;
//		this.prevPosY = y;
//		this.prevPosZ = z;
//		this.motionX = 0.0D;
//        this.motionY = 0.0D;
//        this.motionZ = 0.0D;
//        this.preventEntitySpawning = true;
//        this.setSize(1F, 1F);
//        this.noClip = true;
	}
	
	@Override
	protected void entityInit() {}

	public UniversalEntityRenderer getRenderer() {
		return null;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public void onUpdate() {
		
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {}

}
