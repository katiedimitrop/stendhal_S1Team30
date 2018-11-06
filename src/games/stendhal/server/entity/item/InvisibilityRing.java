package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

/** 
 * A ring that, when worn, sets a player to be invisible.
 * 
 * Also implements a 'pick-pocket' feature that allows
 * the player to steal items from other players
 * 
 * @author 'George Clarke'
 */

public class InvisibilityRing extends Item 
{
	/**
	 * Creates a new invisibility ring
	 * 
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	
	public InvisibilityRing(final String name, final String clazz, final String subclass,
			final Map<String, String> attributes)
	{
		super(name, clazz, subclass, attributes);
	} //1st constructor
	
	/**
	 * Copy constructor.
	 *
	 * @param item
	 *            item to copy
	 */
	
	public InvisibilityRing(final InvisibilityRing item)
	{
		super(item);
	} //2nd constructor
	
	/**
	 * Method that sets the player using the ring
	 * to be invisible
	 */

	public void makeInvisible(final RPEntity user) 
	{
		if (user instanceof Player) {
			((Player) user).setInvisible(true);
		} //if
	} //onUsed
} //class