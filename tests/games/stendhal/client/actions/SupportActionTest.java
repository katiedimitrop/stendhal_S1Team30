package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
//import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
//import marauroa.common.game.RPAction;
import marauroa.common.game.RPAction;

public class SupportActionTest {

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
				assertEquals("support", action.get("type"));
				assertEquals("schnick", action.get("text"));
			}
		};
		final SupportAction action = new SupportAction();
		assertTrue(action.execute(null,"schnick"));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final SupportAction action = new SupportAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SupportAction action = new SupportAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
