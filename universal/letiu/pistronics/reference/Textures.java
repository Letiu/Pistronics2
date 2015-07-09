package letiu.pistronics.reference;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.ItemReference;
import net.minecraft.util.ResourceLocation;

public class Textures {
	
	// FULL PATHS //
    public static final ResourceLocation camouOverlay = new ResourceLocation("pistronics", "textures/blocks/statue/camouOverlay.png");
    public static final ResourceLocation stoneTexture = BlockItemUtil.getResourceLocation(ItemReference.STONE, 0);

	public static final String OAK_WOOD_PLANKS = "textures/blocks/gears/wood_1.png";
	public static final String SPRUCE_WOOD_PLANKS = "textures/blocks/gears/wood_2.png";
	public static final String BIRCH_WOOD_PLANKS = "textures/blocks/gears/wood_3.png";
	public static final String JUNGLE_WOOD_PLANKS = "textures/blocks/gears/wood_4.png";
	
	public static final ResourceLocation OAK_WOOD_PLANKS_RL = new ResourceLocation("pistronics", Textures.OAK_WOOD_PLANKS);
	public static final ResourceLocation SPRUCE_WOOD_PLANKS_RL = new ResourceLocation("pistronics", Textures.SPRUCE_WOOD_PLANKS);
	public static final ResourceLocation BIRCH_WOOD_PLANKS_RL = new ResourceLocation("pistronics", Textures.BIRCH_WOOD_PLANKS);
	public static final ResourceLocation JUNGLE_WOOD_PLANKS_RL = new ResourceLocation("pistronics", Textures.JUNGLE_WOOD_PLANKS);
	
	public static ResourceLocation getPlankTextureFromMeta(int meta) {
		switch (meta) {
		case 0: return OAK_WOOD_PLANKS_RL;
		case 1: return SPRUCE_WOOD_PLANKS_RL;
		case 2: return BIRCH_WOOD_PLANKS_RL;
		case 3: return JUNGLE_WOOD_PLANKS_RL;
		default: return OAK_WOOD_PLANKS_RL;
		}
	}
	
	// BLOCKS //
	
	public static final String MECH_PISTON_SUPER_STICKY = "mech_piston/super_sticky";
	
	public static final String MECH_PISTON_INNER = "mech_piston/inner";
	public static final String MECH_PISTON_SIDE = "mech_piston/side";
	public static final String MECH_PISTON_BOTTOM = "mech_piston/bottom";
	
	public static final String MECH_ROTATOR_INNER = "mech_rotator/inner";
	public static final String MECH_ROTATOR_SIDE = "mech_rotator/side";
	public static final String MECH_ROTATOR_BOTTOM = "mech_rotator/bottom";
	
	public static final String CAMOU_PISTON_INNER = "camou_machines/camou_piston_inner";
	public static final String CAMOU_PISTON_SIDE = "camou_machines/camou_piston_side";
	public static final String CAMOU_PISTON_BOTTOM = "camou_machines/camou_piston_bottom";
	
	public static final String CAMOU_ROTATOR_INNER = "camou_machines/camou_rotator_inner";
	public static final String CAMOU_ROTATOR_SIDE = "camou_machines/camou_rotator_side";
	public static final String CAMOU_ROTATOR_BOTTOM = "camou_machines/camou_rotator_bottom";
	
	public static final String CAMOU_ADV_PISTON_INNER = "camou_machines/camou_adv_piston_inner";
	public static final String CAMOU_ADV_PISTON_SIDE = "camou_machines/camou_adv_piston_side";
	public static final String CAMOU_ADV_PISTON_BOTTOM = "camou_machines/camou_adv_piston_bottom";
	
	public static final String CAMOU_RODFOLDER_INNER = "camou_machines/camou_rodfolder_inner";
	public static final String CAMOU_RODFOLDER_SIDE = "camou_machines/camou_rodfolder_side";
	public static final String CAMOU_RODFOLDER_BOTTOM = "camou_machines/camou_box";
	
	public static final String ROD_FOLDER_INNER = "rod_folder/inner";
	public static final String ROD_FOLDER_SIDE = "rod_folder/side";
	public static final String ROD_FOLDER_BOTTOM = "rod_folder/box";
	
	public static final String ADV_PISTON_SIDE = "adv_piston/side";
	public static final String ADV_PISTON_INNER = "adv_piston/inner";
	public static final String ADV_PISTON_BOTTOM = "adv_piston/bottom";
	
	public static final String ADV_ROTATOR_SIDE = "adv_rotator/side";
	public static final String ADV_ROTATOR_INNER = "adv_rotator/inner";
	public static final String ADV_ROTATOR_BOTTOM = "adv_rotator/bottom";
	
	public static final String ROD_SIDE = "mech_piston/ext_rod";
	public static final String ROD_ENDS = "wood/planks_oak";
	public static final String ROD_REDSTONE = "mech_piston/redstone_rod";
	public static final String ROD_REDSTONE_ON = "mech_piston/redstone_rod_active";
	
	
	public static final String EXTENSION_NORMAL = "mech_piston/normal";
	public static final String EXTENSION_STICKY = "mech_piston/sticky";
	public static final String EXTENSION_SUPER_STICKY = "mech_piston/super_sticky";
	
	public static final String EXTENSION_SIDE = "mech_piston/side";
	public static final String VOID = "extension/void";
	
	public static final String CAMOU_EXT = "camou/camouext";
	public static final String CAMOU_EXT_STICKY = "camou/camouext_sticky";
	public static final String CAMOU_EXT_SUPER_STICKY = "camou/camouext_super_sticky";
	
	public static final String RS_EXT_OFF = "redstone/rs_ext_off";
	public static final String RS_EXT_ON = "redstone/rs_ext_on";
	public static final String RS_EXT_OFF_S = "redstone/rs_ext_off_s";
	public static final String RS_EXT_ON_S = "redstone/rs_ext_on_s";
	public static final String RS_EXT_OFF_S_S = "redstone/rs_ext_off_s_s";
	public static final String RS_EXT_ON_S_S = "redstone/rs_ext_on_s_s";
	
	public static final String RS_EXT_CAMOU_OFF = "extension/rs_ext_camou";
	public static final String RS_EXT_CAMOU_ON = "extension/rs_ext_camou_on";
	public static final String RS_EXT_CAMOU_OFF_S = "extension/rs_ext_camou_s";
	public static final String RS_EXT_CAMOU_ON_S = "extension/rs_ext_camou_on_s";
	public static final String RS_EXT_CAMOU_OFF_S_S = "extension/rs_ext_camou_s_s";
	public static final String RS_EXT_CAMOU_ON_S_S = "extension/rs_ext_camou_on_s_s";
	
	public static final String RS_EXT_SIDE_OFF = "redstone/rs_ext_side_off";
	public static final String RS_EXT_SIDE_ON = "redstone/rs_ext_side_on";
	
	public static final String EXT_SIDE_MID = "extension/ext_side_mid";
	public static final String EXT_SIDE_RIGHT = "extension/ext_side_right";
	public static final String EXT_SIDE_LEFT = "extension/ext_side_left";
	
	public static final String RS_EXT_ON_SIDE_MID = "extension/rs_ext_side_on_mid";
	public static final String RS_EXT_ON_SIDE_RIGHT = "extension/rs_ext_side_on_right";
	public static final String RS_EXT_ON_SIDE_LEFT = "extension/rs_ext_side_on_left";
	
	public static final String RS_EXT_OFF_SIDE_MID = "extension/rs_ext_side_off_mid";
	public static final String RS_EXT_OFF_SIDE_RIGHT = "extension/rs_ext_side_off_right";
	public static final String RS_EXT_OFF_SIDE_LEFT = "extension/rs_ext_side_off_left";
	
	public static final String RS_COMP_EXT_OFF_SIDE_X = "comp_extensions/comp_ext_";
	public static final String RS_COMP_EXT_ON_SIDE_X = "comp_extensions/comp_ext_on_";
	
	public static final String REDIO_SUPER_EXT_ON = "extension/redio_super_ext_on";
	public static final String REDIO_SUPER_EXT_OFF = "extension/redio_super_ext_off";
	public static final String REDIO_EXT_ON = "extension/redio_ext_on";
	public static final String REDIO_EXT_OFF = "extension/redio_ext_off";
	
	public static final String REDIO_CAMOU_SUPER_EXT_ON = "extension/redio_camou_super_ext_on";
	public static final String REDIO_CAMOU_SUPER_EXT_OFF = "extension/redio_camou_super_ext_off";
	public static final String REDIO_CAMOU_EXT_ON = "extension/redio_camou_ext_on";
	public static final String REDIO_CAMOU_EXT_OFF = "extension/redio_camou_ext_off";
	
	public static final String CAMOU_BLOCK = "camou/camoublock";
	
	public static final String ROD_KNOT = "mech_piston/rod_knot";
	
	public static final String BOX = "rod_folder/box";

	public static final String STATUE = "statue/statue_icon";
	
	public static final String GLUE_BLOCK = "slimeblocks/glueblock";
	public static final String SLIME_BLOCK = "slimeblocks/slimeblock";
	public static final String SUPER_GLUE_BLOCK = "slimeblocks/superglueblock";
	
	public static final String CAMOU_SAIL = "camou/camou_sail";

	public static final String STOPPER = "stopper/stopper";
	
	// ITEMS //
	
	public static final String GEAR = "gear"; 
	public static final String SUPER_GLUE = "super_glue";
	public static final String GLUE = "glueball";
	public static final String TOOL = "tool";
	public static final String CAMOUPASTE = "camoupaste";
	public static final String BOOK_OF_GEARS = "BookItem";
	public static final String SAW = "saw";
	public static final String PETRIFY_ARROW = "petrify_arrow";
	public static final String TAPE = "tape";
	public static final String PILE_OF_REDSTONE = "PileOfRedstone";
	public static final String SPADE = "spade";
	public static final String MAGNET = "magnet";
	public static final String REDIO_GLUE = "redio_glue";
	public static final String REDIO_SUPER_GLUE = "redio_super_glue";
	public static final String PETRIFY_EXTRACT = "petrifyExtract";
    public static final String CHISEL = "chisel";
}
