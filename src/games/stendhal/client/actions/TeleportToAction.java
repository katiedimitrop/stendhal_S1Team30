/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.actions;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.common.StringHelper;
import marauroa.common.game.RPAction;

/**
 * Teleport player to another player's location.
 */
class TeleportToAction extends Action {
	
	//Name of xml file that contains the min and max no of parameters
    public static final String COMMAND_NAME = "teleportto";
    
    @Override
	 protected String getCommandName() 
    {
		return COMMAND_NAME;
	 }

	/**
	 * Execute a chat command.
	 *
	 * @param params
	 *            The formal parameters.
	 * @param remainder
	 *            Line content after parameters.
	 *
	 * @return <code>true</code> if was handled.
	 */
	@Override
	public boolean execute(final String[] params, final String remainder) {
		final RPAction teleport = new RPAction();

		teleport.put("type", "teleportto");
		teleport.put("target", StringHelper.unquote(remainder));

		ClientSingletonRepository.getClientFramework().send(teleport);

		return true;
	}
}
