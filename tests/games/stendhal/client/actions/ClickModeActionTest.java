package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import games.stendhal.client.StendhalClient;


public class ClickModeActionTest {
	
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		String[] params = new String[] {null};

		// Test the ClickMode action execute method with null param.
		final ClickModeAction action = new ClickModeAction();

	assertTrue(action.execute(params, null));
}
	
	
	/**
	 * Tests for getMinimumParameters.
	 */
	
	public void testGetMaximumParameters() {
		final ClickModeAction action = new ClickModeAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final ClickModeAction action = new ClickModeAction();
		assertThat(action.getMinimumParameters(), is(0));
	}
}
