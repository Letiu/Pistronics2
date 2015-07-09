package letiu.pistronics.data;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import letiu.pistronics.entities.EntityPetrifyArrow;

public class EntityData {
	
	private static ArrayList<Class<? extends Entity>> entities;
	
	public static void init() {
		entities = new ArrayList<Class<? extends Entity>>();
		
		entities.add(EntityPetrifyArrow.class);
	}
	
	public static ArrayList<Class<? extends Entity>> getEntityData() {
		return entities;
	}
}
