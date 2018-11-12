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
	 * Creates a new invisibility ring and immediately sets
	 * the player to be invisible to creatures
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
		setPersistent(true);
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

	@Override
	public boolean onEquipped(RPEntity equipper, String slot) {
		if ((equipper instanceof Player)) {
			return makeInvisible((Player) equipper);
		}
		return false;
	} //onUsed
	
	public boolean makeInvisible(Player player)
	{
		boolean invisible = player.isInvisibleToCreatures();
		if (invisible)
		{
		  player.setInvisible(false);
		  player.setGhost(false);
		  player.sendPrivateText("Invisibility and Ghost mode is now off!");
		  return true;
		} //if
		else
		{
			player.setInvisible(true);
			player.setGhost(true);
			player.sendPrivateText("Invisibility and Ghost mode is now on! Use this wisely!");
			return true;
		}
	}
} //class