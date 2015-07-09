package letiu.pistronics.piston;

import net.minecraft.world.World;
import letiu.pistronics.util.Vector3;

public interface ISpecialRotator {

	public boolean canRotate(World world, int x, int y, int z, int rotateDir, float speed, Vector3 rotatePoint);
	
	public void preRotate(World world, int x, int y, int z, int rotateDir, float speed, Vector3 rotatePoint);
	
	public void postRotate(World world, int x, int y, int z, int rotateDir, float speed, Vector3 rotatePoint);
}
