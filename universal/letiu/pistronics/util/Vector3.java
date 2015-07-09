package letiu.pistronics.util;

import net.minecraft.nbt.NBTTagCompound;

public class Vector3 {
	
	public static final Vector3 X_AXIS = new Vector3(1, 0, 0);
	public static final Vector3 Y_AXIS = new Vector3(0, 1, 0);
	public static final Vector3 Z_AXIS = new Vector3(0, 0, 1);
	public static final Vector3 X_AXIS_NEG = new Vector3(-1, 0, 0);
	public static final Vector3 Y_AXIS_NEG = new Vector3(0, -1, 0);
	public static final Vector3 Z_AXIS_NEG = new Vector3(0, 0, -1);
	
	/**
	 * Calculates the scalar-product of the given vectors.
	 */
	public static int scalar(Vector3... vecs) {
		int x = 1, y = 1, z = 1;
		for (Vector3 vec : vecs) {
			x *= vec.x; 
			y *= vec.y;
			z *= vec.z;
		}
		return x + y + z;
	}
	
	/**
	 * Calculates the cross-product of the given vectors.
	 */
	public static Vector3 cross(Vector3 vec1, Vector3 vec2) {
		return new Vector3(vec1.y * vec2.z - vec1.z * vec2.y, vec1.z * vec2.x - vec1.x * vec2.z, vec1.x * vec2.y - vec1.y * vec2.x);
	}
	
	public int x;
	public int y;
	public int z;
	
	/**
	 * Creates a new Vector3 with x, y, z.
	 */
	public Vector3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Equivalent to Vector3(0, 0, 0).
	 */
	public Vector3() {
		this(0, 0, 0);
	}
	
	/**
	 * Returns itself not a new Vector.
	 * @param Vector to add 
	 */
	public Vector3 add(Vector3 other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	
	/**
	 * Returns itself not a new Vector.
	 * @param Vector to subtract 
	 */
	public Vector3 sub(Vector3 other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}
	
	/**
	 * Returns itself not a new Vector.
	 * @param Factor to multiply with 
	 */
	public Vector3 mul(int factor) {
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
		return this;
	}
	
	/**
	 * Returns itself not a new Vector.
	 * @param Factor to multiply with 
	 */
	public Vector3 mul(float factor) {
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
		return this;
	}
	
	/**
	 * Returns itself not a new Vector.
	 * @param Factor to multiply with 
	 */
	public Vector3 mul(double factor) {
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
		return this;
	}
	
	/**
	 * Returns itself not a new Vector. Equivalent to mul(-1).
	 */
	public Vector3 reverse() {
		mul(-1);
		return this;
	}
	
	/**
	 * Returns itself not a new Vector. x-Axis = 0, y-Axis = 1, z-Axis = 2.
	 * Params are axis and angle.
	 */
	public Vector3 rotate(int axis, int angle) {
		
		double a = Math.toRadians(angle);
		
		if (axis == 0) {
			this.y = (int) (y * Math.cos(a) + z * -Math.sin(a));
			this.z = (int) (y * Math.sin(a) + z * Math.cos(a));
		}
		else if (axis == 1) {
			//System.out.println("Applying rotation for y-Axis with angle " + angle);
			//System.out.println("radiant angle " + a);
			//System.out.println("Cos a = " + Math.cos(a));
			this.x = (int) (x * Math.cos(a) + z * -Math.sin(a));
			this.z = (int) (x * Math.sin(a) + z * Math.cos(a));
		}
		else if (axis == 2) {
			this.x = (int) (x * Math.cos(a) + y * -Math.sin(a));
			this.y = (int) (x * Math.sin(a) + y * Math.cos(a));
		}
		
		return this;
	}
	
	/**
	 * Returns the length of the Vector.
	 */
	public int amount() {
		return (int) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Returns itself not a new Vector. Normalizes the vector to length 1.
	 * Note that this vector uses int values for coordinates. 
	 */
	public Vector3 normalize() {
		
		int amt = this.amount();
		if (amt == 0) return this;
		this.x /= amt;
		this.y /= amt;
		this.z /= amt;
		
		return this;
	}
	
	public Vector3 copy() {
		return new Vector3(x, y, z);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3 other = (Vector3) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public String toString() {
		return "Vector(" + x + ", " + y + ", " + z + ")";
	}
}
