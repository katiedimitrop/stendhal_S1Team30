package games.stendhal.server.maps.fado.city;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

/**
 * Test buying wheelbarrows.
 *
 * @author Mohammad Jahanzaib Alam
 */
public class WheelbarrowSellerNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "0_fado_city";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME);
	}

	public WheelbarrowSellerNPCTest() {
		setNpcNames("Hadvar");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new WheelbarrowSellerNPC(), ZONE_NAME);
	}

	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Hadvar");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hello"));
		assertEquals("Greetings! How may I help you?", getReply(npc));

		assertTrue(en.step(player, "bye"));
		assertEquals("Bye.", getReply(npc));
	}

	/**
	 * Tests for buyWheelbarrow.
	 */
	@Test
	public void testBuyWheelbarrow() {
		final SpeakerNPC npc = getNPC("Hadvar");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi"));
		assertEquals("Greetings! How may I help you?", getReply(npc));

		assertTrue(en.step(player, "job"));
		assertEquals("I work as a wheelbarrow seller.", getReply(npc));

		assertTrue(en.step(player, "offer"));
		assertEquals("I sell wheelbarrows.", getReply(npc));

		assertTrue(en.step(player, "buy"));
		assertEquals("A wheelbarrow will cost 20. Do you want to buy it?", getReply(npc));
		assertTrue(en.step(player, "no"));
		assertEquals("Ok, how else may I help you?", getReply(npc));

		assertTrue(en.step(player, "buy dog"));
		assertEquals("Sorry, I don't sell dogs.", getReply(npc));

		assertTrue(en.step(player, "buy house"));
		assertEquals("Sorry, I don't sell houses.", getReply(npc));

		assertTrue(en.step(player, "buy someunknownthing"));
		assertEquals("Sorry, I don't sell someunknownthings.", getReply(npc));

		assertTrue(en.step(player, "buy wheelbarrow"));
		assertEquals("A wheelbarrow will cost 20. Do you want to buy it?", getReply(npc));

		assertTrue(en.step(player, "no"));
		assertEquals("Ok, how else may I help you?", getReply(npc));

		assertTrue(en.step(player, "buy wheelbarrow"));
		assertEquals("A wheelbarrow will cost 20. Do you want to buy it?", getReply(npc));

		assertTrue(en.step(player, "yes"));
		assertEquals("You don't seem to have enough money.", getReply(npc));

		// equip with enough money to buy one wheelbarrow
		assertTrue(equipWithMoney(player, 20));

		assertTrue(en.step(player, "buy 2 wheelbarrows"));
		assertEquals("2 wheelbarrows will cost 200. Do you want to buy them?", getReply(npc));

		assertTrue(en.step(player, "yes"));
		assertEquals("Sorry! I can only sell one wheelbarrow.", getReply(npc));

		assertTrue(en.step(player, "buy wheelbarrow"));
		assertEquals("A wheelbarrow will cost 20. Do you want to buy it?", getReply(npc));
		// Make sure there is no block currently at the position where the wheelbarrow will be spawned.
		assertFalse(WheelbarrowSellerNPC.spawnIsBlocked());

		assertTrue(en.step(player, "yes"));
		
		// Make sure the wheelbarrow has spawned and therefore the spawn location is now blocked.
		assertTrue(WheelbarrowSellerNPC.spawnIsBlocked());
		assertEquals("Here you go, your own wheelbarrow! Use it well.", getReply(npc));

		// Test for when the spawn location is blocked causing the NPC to say 
		// something is blocking it and needs to be moved.
		assertTrue(en.step(player, "buy wheelbarrow"));
		assertTrue(en.step(player, "yes"));
		
		assertEquals("I need space to give you your wheelbarrow, please move whatever is blocking my space.", getReply(npc));

		
	}

}