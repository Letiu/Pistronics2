package letiu.modbase.events;

import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public interface IArrowEventListener {

	public void handleNockEvent(ArrowNockEvent event);
	public void handleLooseEvent(ArrowLooseEvent event);
}
