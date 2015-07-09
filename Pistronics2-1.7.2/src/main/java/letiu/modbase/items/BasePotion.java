package letiu.modbase.items;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

public class BasePotion extends Potion {

	public static void expandPotionArray() {
		
		if (Potion.potionTypes.length >= 127) return;
		
		Potion[] potionTypes = null;

		for (Field f : Potion.class.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			
					potionTypes = (Potion[])f.get(null);
					final Potion[] newPotionTypes = new Potion[127];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
				}
			}
			catch (Exception e) {
				System.err.println("PISTRONICS: Wasn't able to expand potion array.");
				System.err.println(e);
			}
		}
	}
	
	protected BasePotion(int ID, boolean eff, int color) {
		super(ID, eff, color);
	}
	
}
