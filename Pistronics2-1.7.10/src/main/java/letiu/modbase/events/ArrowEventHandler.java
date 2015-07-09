package letiu.modbase.events;

import java.util.ArrayList;

import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ArrowEventHandler {
	
	private ArrayList<IArrowEventListener> listeners = new ArrayList<IArrowEventListener>();
	
	public void addListener(IArrowEventListener listener) {
		listeners.add(listener);		
	}
	
	@SubscribeEvent
	public void handleEvent(ArrowNockEvent event) {
		for (IArrowEventListener listener : listeners) {
			listener.handleNockEvent(event);
		}
	}
	
	@SubscribeEvent
	public void handleEvent(ArrowLooseEvent event) {
		for (IArrowEventListener listener : listeners) {
			listener.handleLooseEvent(event);
		}
	}
}
