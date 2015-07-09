package letiu.modbase.render;

import java.util.TreeMap;

import net.minecraft.util.IIcon;

public class TextureMapper {

	public static TreeMap<String, IIcon> iconMap;
	
	public static void init() {
		iconMap = new TreeMap<String, IIcon>();
	}
	
	
}
