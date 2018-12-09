package games.stendhal.client.actions;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Test;


import games.stendhal.client.StendhalClient;




public class SoundActionTest {
	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}


	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final SoundAction action = new SoundAction();
		assertThat(action.getMaximumParameters(), is(5));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SoundAction action = new SoundAction();
		assertThat(action.getMinimumParameters(), is(0));
	}
}
 