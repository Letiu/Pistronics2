package letiu.pistronics.data;

import letiu.pistronics.tiles.TileCamoublock;
import letiu.pistronics.tiles.TileCreativeMachine;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TileGear;
import letiu.pistronics.tiles.TileMechPiston;
import letiu.pistronics.tiles.TileMechRotator;
import letiu.pistronics.tiles.TileMotion;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.tiles.TileRodFolder;
import letiu.pistronics.tiles.TileSail;
import letiu.pistronics.tiles.TileStatue;


public class TileData {
	
	public static final String key_extension = "tlExtension";
	public static final String key_camoublock = "tlCamoublock";
	public static final String key_partblock = "tlPartblock";
	public static final String key_rod = "tlRod";
	public static final String key_mechPiston = "tlMechPiston";
	public static final String key_mechRotator = "tlMechRotator";
	public static final String key_creativeMachine = "tlCreativeMachine";
	public static final String key_rodfolder = "tlRodFolder";
	public static final String key_motion = "tlMotion";
	public static final String key_gear = "tlGear";
	public static final String key_sail = "tlSail";
	public static final String key_statue = "tlStatue";
	
	public static PTile getTile(String key) {

		if (key.equals(key_extension)) return new TileExtension();
		if (key.equals(key_camoublock)) return new TileCamoublock();
		if (key.equals(key_partblock)) return new TilePartblock();
		if (key.equals(key_rod)) return new TileRod();
		if (key.equals(key_mechPiston)) return new TileMechPiston();
		if (key.equals(key_mechRotator)) return new TileMechRotator();
		if (key.equals(key_creativeMachine)) return new TileCreativeMachine();
		if (key.equals(key_rodfolder)) return new TileRodFolder();
		if (key.equals(key_motion)) return new TileMotion();
		if (key.equals(key_gear)) return new TileGear();
		if (key.equals(key_sail)) return new TileSail();
		if (key.equals(key_statue)) return new TileStatue();
		
		return null;
	}
}
