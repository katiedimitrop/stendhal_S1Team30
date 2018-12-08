package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.UserInterface;
import games.stendhal.client.gui.chatlog.EventLine;
import games.stendhal.client.sound.facade.SoundSystemFacade;
import games.stendhal.common.NotificationType;

public class GMHelpActionTest {

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		String[] params = new String[] {null};

		// Test the GMHelp action execute method with null param.
		final GMHelpAction action = new GMHelpAction();

		// Create a user interface so the GMHelpAction can be tested.
		// It is an interface so the methods have to be overriden.
		ClientSingletonRepository.setUserInterface(new UserInterface() {
			@Override
			public void addAchievementBox(String title, String description, String category) {
			}

			@Override
			public void addEventLine(EventLine line) {
			}

			@Override
			public void addGameScreenText(double x, double y, String text, NotificationType type, boolean isTalking) {
			}

			@Override
			public SoundSystemFacade getSoundSystemFacade() {
				return null;
			}
		});
		// Test the execution of the method.
		assertTrue(action.execute(params, "1"));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
