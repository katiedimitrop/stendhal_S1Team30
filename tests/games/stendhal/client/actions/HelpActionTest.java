package games.stendhal.client.actions;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.UserInterface;
import games.stendhal.client.gui.chatlog.EventLine;
import games.stendhal.client.sound.facade.SoundSystemFacade;
import games.stendhal.common.NotificationType;

public class HelpActionTest {
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

		// Test the Help action execute method with null param.
		final HelpAction action = new HelpAction();

		// Create a user interface so the HelpAction can be tested.
		// It is an interface so the methods have to be overriden.
		ClientSingletonRepository.setUserInterface(new UserInterface() {
	

			@Override
			public void addEventLine(EventLine line) {
			}

		

			@Override
			public SoundSystemFacade getSoundSystemFacade() {
				// TODO Auto-generated method stub
				return null;
			}



			@Override
			public void addGameScreenText(double x, double y, String text, NotificationType type, boolean isTalking) {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void addAchievementBox(String title, String description, String category) {
				// TODO Auto-generated method stub
				
			}

		});
		// Test the execution of the method.
		assertTrue(action.execute(params, "1"));
	}


}
