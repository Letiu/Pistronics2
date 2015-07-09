package letiu.pistronics.render;

public class RenderTweaker {

	public static boolean[] sideRender = {true, true, true, true, true, true};
	public static boolean renderAllSides = false;
	
	public static void reset() {
		for (int i = 0; i < 6; i++) {
			sideRender[i] = true;;
		}
		renderAllSides = false;
	}
	
}
