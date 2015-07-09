package letiu.modbase.events;

import java.util.ArrayList;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ArrowEventHandler {
	
	private ArrayList<IArrowEventListener> listeners = new ArrayList<IArrowEventListener>();
	
	public void addListener(IArrowEventListener listener) {
		listeners.add(listener);		
	}
	
	@ForgeSubscribe
	public void handleEvent(ArrowNockEvent event) {
		for (IArrowEventListener listener : listeners) {
			listener.handleNockEvent(event);
		}
	}
	
	@ForgeSubscribe
	public void handleEvent(ArrowLooseEvent event) {
		for (IArrowEventListener listener : listeners) {
			listener.handleLooseEvent(event);
		}
	}
}
