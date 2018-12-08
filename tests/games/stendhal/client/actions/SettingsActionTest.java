package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;


import org.junit.Test;


import games.stendhal.client.StendhalClient;






public class SettingsActionTest {

	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final SettingsAction action = new SettingsAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SettingsAction action = new SettingsAction();
		assertThat(action.getMinimumParameters(), is(0));
	}



}
