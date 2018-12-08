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

public class JailActionTest {

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
				assertEquals("jail", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("schneck", action.get("minutes"));
				assertEquals("schnack", action.get("reason"));
				
			}
		};
		final JailAction action = new JailAction();
		final String[] params = new String[] {"schnick", "schneck"};
		assertTrue(action.execute(params, "schnack"));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final JailAction action = new JailAction();
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final JailAction action = new JailAction();
		assertThat(action.getMinimumParameters(), is(2));
	}

}
