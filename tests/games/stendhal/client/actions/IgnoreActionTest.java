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

public class IgnoreActionTest {

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
				assertEquals("ignore", action.get("type"));
			}
		};
		
		String[] params = new String[] {"shnick", "5"};

		// Test the ignore action execute method.
		final IgnoreAction action = new IgnoreAction();
		assertTrue(action.execute(params, "1"));

		// Test again with first param being null.
		params = new String[] {null, "5"};
		assertTrue(action.execute(params, "1"));
		
		// Test again with the duration (remainder being null).
		params = new String[] {"schnick", null};
		assertTrue(action.execute(params, "1"));
		
		// Test again with duration (remainder being "*".
		params = new String[] {"schnick", "*"};
		assertTrue(!action.execute(params, "1"));
		
		// Test again with remainder (duration) = 0.
		params = new String[] {"schnick", "0"};
		assertTrue(action.execute(params, "1"));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final IgnoreAction action = new IgnoreAction();
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final IgnoreAction action = new IgnoreAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
