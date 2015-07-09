package letiu.pistronics.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public abstract class PSimpleRenderer implements ISimpleBlockRenderingHandler {

	public int renderID;
	
	@Override
	public int getRenderId() {
		return renderID;
	}
}
