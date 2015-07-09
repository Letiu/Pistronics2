package letiu.modbase.util;

import java.util.Set;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTUtil {

	public static final int TAG_COMPOUND_ID = NBTBase.NBTTypes.length - 2;
	
	public static NBTTagCompound getCompoundAt(NBTTagList list, int i) {
		return list.getCompoundTagAt(i);
	}
	
	public static NBTTagList getCompoundList(NBTTagCompound compound, String key) {
		return compound.getTagList(key, TAG_COMPOUND_ID);
	}
	
	public static boolean compare(NBTTagCompound nbt1, NBTTagCompound nbt2) {
		
		if (nbt1 == null && nbt2 == null) return true;
		if (nbt1 == null && nbt2 != null) return false;
		if (nbt1 != null && nbt2 == null) return false;
		
		Set keySet = nbt1.func_150296_c();
		
		for (Object obj : keySet) {
			String key = (String) obj;
			if (!nbt2.hasKey(key) || !(nbt1.getTag(key).equals(nbt2.getTag(key)))) return false;
		}
		
		return true;
	}
	
	public static NBTTagCompound getNewCompound() {
		return new NBTTagCompound();
	}
}
