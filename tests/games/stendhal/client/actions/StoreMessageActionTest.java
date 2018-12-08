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

public class StoreMessageActionTest {

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
				assertEquals("storemessage", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("schneck", action.get("text"));
			}
		};
		final StoreMessageAction action = new StoreMessageAction();
		final String[] params = new String[] {"schnick"};
		assertTrue(action.execute(params, "schneck"));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final StoreMessageAction action = new StoreMessageAction();
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final StoreMessageAction action = new StoreMessageAction();
		assertThat(action.getMinimumParameters(), is(1));
	}

}
