package games.stendhal.server.entity.mapstuff.block;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.RPClass.BlockTestHelper;


/**
 * 
 * Test to check if wheelbarrows keep both parts
 * together when on map.
 *  
 */

public class WheelbarrowTest {
	
	@BeforeClass
	public static void beforeClass() {
		BlockTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
        MockStendlRPWorld.get();
	} //beforeClass 
	
	@Test
	public final void testTogether() {
		Player player = PlayerTestHelper.createPlayer("testPlayer");
		Wheelbarrow wheelbarrow = new Wheelbarrow(true, player);
		StendhalRPZone zone = new StendhalRPZone("test", 16, 16);
		zone.add(wheelbarrow);
		zone.add(wheelbarrow.wheelbarrowChest);
		StendhalRPAction.placeat(zone, wheelbarrow, 5, 5);
		StendhalRPAction.placeat(zone, wheelbarrow.wheelbarrowChest, 6, 5);
		wheelbarrow.setPosition(5, 5);
		wheelbarrow.wheelbarrowChest.setPosition(6, 5);
		
		wheelbarrow.push(player, Direction.RIGHT);
		assertTrue(wheelbarrow.wheelbarrowChest.getX() == (wheelbarrow.getX() + 1));
	} //testReset
	

} //class