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

public class SupportAnswerActionTest {

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
				assertEquals("supportanswer", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("schneck", action.get("text"));
			}
		};
		final SupportAnswerAction action = new SupportAnswerAction();
		final String[] params = new String[] {"schnick"};
		assertTrue(action.execute(params, "schneck"));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final SupportAnswerAction action = new SupportAnswerAction();
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SupportAnswerAction action = new SupportAnswerAction();
		assertThat(action.getMinimumParameters(), is(1));
	}

}
