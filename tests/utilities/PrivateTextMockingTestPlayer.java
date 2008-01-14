package utilities;

import games.stendhal.common.NotificationType;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPObject;

/**
 * A player test class used to catch private message texts.
 */
public class PrivateTextMockingTestPlayer extends Player {
	private StringBuilder privateTextBuffer = null;


	/**
	 * Creates a new mock player without name.
	 */
	public PrivateTextMockingTestPlayer(RPObject obj) {
		super(obj);
	}

	/**
	 * Creates a new mock player with name.
	 */
	public PrivateTextMockingTestPlayer(RPObject obj, String name) {
		this(obj);
		setName(name);
	}

	/**
	 * Catch private message texts.
	 */
	@Override
	public void sendPrivateText(NotificationType type, String text) {
		appendMessage(text);

		// Don't forget to call the super class.
		super.sendPrivateText(type, text);
	}

	/**
	 * Append the given string to the private message buffer.
	 *  
	 * @param text
	 */
	private void appendMessage(final String text) {
		if (privateTextBuffer != null) {
			privateTextBuffer.append("\r\n");
			privateTextBuffer.append(text);
		} else {
			privateTextBuffer = new StringBuilder(text);
		}
	}

	/**
	 * Gets the concatenated private message texts
	 * since the last resetPrivateTextString() call.
	 *
	 * @return private message string
	 */
	public String getPrivateTextString() {
		return privateTextBuffer != null ? privateTextBuffer.toString() : "";
	}

	/**
	 * Return boolean flag, if we received any private text
	 * since the last call to resetPrivateTextString().
	 *
	 * @return
	 */
	public boolean hasPrivateText() {
		return privateTextBuffer != null;
	}

	/**
	 * Reset private message text.
	 */
	public void resetPrivateTextString() {
		privateTextBuffer = null;
	}
}
