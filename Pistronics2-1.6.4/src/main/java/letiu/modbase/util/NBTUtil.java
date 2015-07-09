package letiu.modbase.util;

import java.util.List;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTUtil {

	public static NBTTagCompound getCompoundAt(NBTTagList list, int i) {
		return (NBTTagCompound) list.tagAt(i);
	}
	
	public static NBTTagList getCompoundList(NBTTagCompound compound, String key) {
		return compound.getTagList(key);
	}
	
	public static NBTTagCompound getNewCompound() {
		return new NBTTagCompound("tag");
	}
}
