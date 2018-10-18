package games.stendhal.server.entity.mapstuff.block;

import static org.hamcrest.Matchers.is;
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
 * Test to check if carts dont't refresh when player 
 * leaves zone, and move away from edge of zone if 
 * player enters zone at the positionthe cart is in
 *  
 */

public class CartTest {
	
	@BeforeClass
	public static void beforeClass() {
		BlockTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
        MockStendlRPWorld.get();
	} //beforeClass 
	
	@Test
	public final void testReset() {
		Block b = new Block(true);
		b.setPosition(5, 5);
		b.setResetBlock(false);
		StendhalRPZone z = new StendhalRPZone("test", 10, 10);
		z.add(b);
		Player player = PlayerTestHelper.createPlayer("testPlayer");
		b.push(player, Direction.RIGHT);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(6)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(5)));
		b.onExited(player, z);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(6)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(5)));
	} //testReset
	
	@Test
	public final void testCartEdge() {
		Block cart = new Block(true);
		cart.setPosition(0, 5);
		StendhalRPZone testzone = new StendhalRPZone("test", 10, 10);
		testzone.add(cart);
		Player p = PlayerTestHelper.createPlayer("pusher");
		assertEquals(0, cart.getX());
		assertEquals(5, cart.getY());
		testzone.setEntryPoint(0, 5);;
		p.setDirection(Direction.RIGHT);
		testzone.placeObjectAtEntryPoint(p);
		assertEquals(0, p.getX());
		assertEquals(5, p.getY());
		assertEquals(1, cart.getX());
		assertEquals(5, cart.getY());
		
		/*p.setPosition(-1, 5);
		testzone.add(p);
		assertEquals(-1, p.getX());
		assertEquals(5, p.getY());
		assertEquals(0, cart.getX());
		assertEquals(5, cart.getY());
		p.faceToward(cart);
		cart.onEntered(p, testzone);
		assertEquals(0, p.getX());
		assertEquals(5, p.getY());
		assertEquals(1, cart.getX());
		assertEquals(5, cart.getY());*/
	} //testCart
} //class