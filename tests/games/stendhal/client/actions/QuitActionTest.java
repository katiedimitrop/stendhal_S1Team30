package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import games.stendhal.client.StendhalClient;

public class QuitActionTest {

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		//final QuitAction action = new QuitAction();
		//assertTrue(action.execute(null,null));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final QuitAction action = new QuitAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final QuitAction action = new QuitAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
