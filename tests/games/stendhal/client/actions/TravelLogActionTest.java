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
import games.stendhal.common.constants.Actions;
//import marauroa.common.game.RPAction;
import marauroa.common.game.RPAction;

public class TravelLogActionTest {

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
				assertEquals(Actions.PROGRESS_STATUS, action.get("type"));

			}
		};
		final TravelLogAction action = new TravelLogAction();
		assertTrue(action.execute(null,null));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final TravelLogAction action = new TravelLogAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final TravelLogAction action = new TravelLogAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
