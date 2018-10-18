package games.stendhal.server.maps.quests;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.ados.library.LibrarianNPC;
import games.stendhal.server.maps.wofol.blacksmith.BlacksmithNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class GemBookTest {

	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		MockStendlRPWorld.get();

		final StendhalRPZone zone = new StendhalRPZone("admin_test");

		new BlacksmithNPC().configureZone(zone, null);
		new LibrarianNPC().configureZone(zone, null);

		final AbstractQuest quest = new GemBook();
		quest.addToWorld();

	}
	@Before
	public void setUp() {
		player = PlayerTestHelper.createPlayer("player");
	}
	
	@Test
	public void wikipediantestQuest() {
		
		npc = SingletonRepository.getNPCList().get("Wikipedian");
		en = npc.getEngine();
		
		en.step(player, "hi");
		assertEquals("Greetings! How may I help you?", getReply(npc));
		en.step(player, "explain");
		assertEquals("What do you want to be explained?", getReply(npc));
		en.step(player, "gem book");
		assertEquals("If you want to learn about gem book, chat to my friend Ceryl in Semos library, which has a good collection of books on mining related subjects.", getReply(npc));
		
	}

}

