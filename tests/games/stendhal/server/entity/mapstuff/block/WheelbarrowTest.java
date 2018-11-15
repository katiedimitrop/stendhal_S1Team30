package games.stendhal.server.entity.mapstuff.block;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
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
		wheelbarrow.setPosition(5, 5);;
		StendhalRPZone zone = new StendhalRPZone("test", 10, 10);
		zone.add(wheelbarrow);
		wheelbarrow.push(player, Direction.RIGHT);
		assertTrue(wheelbarrow.wheelbarrowChest.nextTo(wheelbarrow));
        fail();
		
	} //testReset
	

} //class