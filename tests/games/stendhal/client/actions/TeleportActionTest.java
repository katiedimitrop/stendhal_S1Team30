package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class TeleportActionTest {

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("teleport", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("schneck", action.get("zone"));
				assertEquals("schnack", action.get("x"));
				assertEquals("schnock", action.get("y"));
				
			}
		};
		final TeleportAction action = new TeleportAction();
		final String[] params = new String[] {"schnick", "schneck", "schnack", "schnock"};
		assertTrue(action.execute(params, null));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final TeleportAction action = new TeleportAction();
		assertThat(action.getMaximumParameters(), is(4));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final TeleportAction action = new TeleportAction();
		assertThat(action.getMinimumParameters(), is(4));
	}

}
