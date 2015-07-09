package letiu.pistronics.entities;

import net.minecraft.world.World;
import letiu.modbase.entities.BaseEntity;
import letiu.modbase.render.UniversalEntityRenderer;

public class EntityGear extends BaseEntity {

	public EntityGear(World world) {
		super(world);
		setSize(3, 3);
	}
	
	public EntityGear(World world, double x, double y, double z) {
		super(world, x, y, z);
		setSize(3, 3);
	}

	@Override
	public UniversalEntityRenderer getRenderer() {
		return null; // PRenderManager.entityGearRenderer;
	}
}
