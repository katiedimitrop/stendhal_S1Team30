package games.stendhal.server.maps.ados.city;

import static org.junit.Assert.assertEquals;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class LonJathamNPCTest{

	
	private Player player;

	private Engine en = null;

	
	@Before
	public void SetUp() {
		player = PlayerTestHelper.createPlayer("player");
	}
	
	@Test
	public void testDialogue() {
		MockStendlRPWorld.get();
		
		final StendhalRPZone zone = new StendhalRPZone("int_ados_house_60");
		
		new LonJathamNPC().configureZone(zone, null);
		SpeakerNPC npc  = SingletonRepository.getNPCList().get("Lon Jatham");
			
		zone.add(npc);
		
		en = npc.getEngine();
		
		en.step(player,  "hi");
		assertEquals("Hi!!!Interested in #computer #science? You've come to the right place!", getReply(npc));
		
		en.step(player,  "computer science");
		assertEquals("You'll be learning Algorithms, Machine Learning ...", getReply(npc));
		
		en.step(player,  "yes");
		assertEquals("You'll be learning Algorithms, Machine Learning ...", getReply(npc));
		
		en.step(player,  "bye");
		assertEquals("Bye bye!", getReply(npc));
		
	}


}
