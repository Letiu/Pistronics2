package letiu.modbase.entities;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;
import letiu.modbase.core.ModClass;
import letiu.pistronics.data.EntityData;

public class EntityCollector {

	public static void registerEntities() {
		
		ArrayList<Class<? extends Entity>> entities = EntityData.getEntityData();
		
		int id = 0;
		
		for (Class clazz : entities) {
			EntityRegistry.registerModEntity(clazz, clazz.getSimpleName(), id++, ModClass.instance, 80, 1, false);
		}
		
	}
	
}
