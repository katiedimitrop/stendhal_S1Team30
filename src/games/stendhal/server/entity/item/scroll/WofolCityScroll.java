package games.stendhal.server.entity.item.scroll;

import java.util.Map;

import games.stendhal.server.entity.player.Player;



public class WofolCityScroll extends MarkedScroll
{

	public WofolCityScroll(String name, String clazz, String subclass, Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Copy constructor.
	 *
	 * @param item
	 *            item to copy
	 */
	public WofolCityScroll(final WofolCityScroll item) {
		super(item);
	}
   
	public void callUseScroll(final Player player)
	{
		this.useScroll(player);
	}
}
