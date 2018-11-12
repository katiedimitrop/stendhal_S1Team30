package games.stendhal.server.entity.item;

import static org.hamcrest.CoreMatchers.is;
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
		assertThat(ring.describe(), is("You see an §'invisibility ring', a very useful asset!"));
	}
	/**
	 * Tests for onEquipped.
	 */
	@Test
	public void testOnEquipped() {
		final Player player1 = PlayerTestHelper.createPlayer("George");
		PlayerTestHelper.registerPlayer(player1, "int_semos_guard_house");
		final InvisibilityRing ring = (InvisibilityRing) SingletonRepository.getEntityManager().getItem("invisibility ring");
		player1.equip("finger",ring);
		ring.onEquipped(player1, "finger");
		assertThat(player1.isInvisibleToCreatures(), is(true));
		assertThat(player1.isGhost(), is(true));
	}

}
