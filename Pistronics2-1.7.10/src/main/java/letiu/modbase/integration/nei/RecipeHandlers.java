package letiu.modbase.integration.nei;

import codechicken.nei.api.API;

public class RecipeHandlers {

    public static void registerHandlers() {

        ShapedHandler shapedHandler = new ShapedHandler();
        API.registerRecipeHandler(shapedHandler);
        API.registerUsageHandler(shapedHandler);

        ShapelessHandler shapelessHandler = new ShapelessHandler();
        API.registerRecipeHandler(shapelessHandler);
        API.registerUsageHandler(shapelessHandler);
    }
}