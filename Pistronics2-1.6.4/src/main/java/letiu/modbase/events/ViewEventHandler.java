package letiu.modbase.events;

import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class ViewEventHandler {

	/** Used for part previews, will be set back to null by the render function */
	public static DrawBlockHighlightEvent highLightEvent = null;
//	
//	/** Used for the saw to determine the sub block */
//	public static DrawBlockHighlightEvent sawEvent = null;
	
	/** Used for the general subHit checks */
	public static DrawBlockHighlightEvent subHitEvent = null;
	
	@ForgeSubscribe
	public void handleEvent(DrawBlockHighlightEvent event) {
		
		highLightEvent = subHitEvent = event;
//		
//		RenderBlocks renderer = new RenderBlocks(event.player.worldObj);
//		
//		ForgeHooksClient.setWorldRendererRB(renderer);
//		
//		renderer.renderStandardBlock(Blocks.stone, 0, 10, 0);
		
		
		//event.context.drawSelectionBox(event.player, event.target, 0, event.partialTicks);
		//Minecraft.getMinecraft().re
		
//		ItemStack stack = event.player.getCurrentEquippedItem();
//		
//		if (stack != null && stack.itemID == Items.saw.itemID) {
//			sawEvent = event;
//		}
//		
//		if (stack != null && stack.itemID < Block.blocksList.length && !event.player.isSneaking()) {
//			Block block = Block.blocksList[stack.itemID];
//			if (block != null && block instanceof IPartCompound) {
//				
//				highLightEvent = event;
//				event.player.worldObj.markBlockForRenderUpdate(event.target.blockX, event.target.blockY, event.target.blockZ);
//				
//			}
//			else if (highLightEvent != null){
//				
//				highLightEvent = null;
//				event.player.worldObj.markBlockForRenderUpdate(event.target.blockX, event.target.blockY, event.target.blockZ);
//			}
//		}
//		else if (highLightEvent != null){
//			highLightEvent = null;
//			event.player.worldObj.markBlockForRenderUpdate(event.target.blockX, event.target.blockY, event.target.blockZ);
//		}
	}

}
