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

public class ListProducersActionTest {

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
				assertEquals("listproducers", action.get("type"));
				
			}
		};
		final ListProducersAction action = new ListProducersAction();
		assertTrue(action.execute(null, null));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final ListProducersAction action = new ListProducersAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final ListProducersAction action = new ListProducersAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
