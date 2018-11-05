package games.stendhal.server.entity.item;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class InvisibilityRingTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
		MockStendlRPWorld.get().addRPZone(new StendhalRPZone("int_semos_guard_house", 100, 100));

	}
	
	/**
	 * Tests for describe.
	 */
	@Test
	public void testDescribe() {
		final InvisibilityRing ring = (InvisibilityRing) SingletonRepository.getEntityManager().getItem("invisibility ring");
		assertThat(ring.describe(), is("You see an §'invisibility ring'. Use it to hide from your enemies."));
	}
	/**
	 * Tests for onUsed.
	 */
	@Test
	public void testOnUsed() {
		final Player player1 = PlayerTestHelper.createPlayer("George");
		PlayerTestHelper.registerPlayer(player1, "int_semos_guard_house");
		final InvisibilityRing ring = new InvisibilityRing();
		PlayerTestHelper.equipWithItem(player1,ring);
		assertThat(player1.isInvisibleToCreatures(), is(true));
		// how do we unequip the items?
		assertThat(player1.isInvisibleToCreatures(), not(is(true)));
		
	}

}
