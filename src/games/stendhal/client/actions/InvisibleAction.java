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
import marauroa.common.game.RPAction;

/**
 * Toggle between invisibility.
 */
class InvisibleAction extends Action {
	
	//Name of xml file that contains the min and max no of parameters
    public static final String COMMAND_NAME = "invisible";
    
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
		final RPAction invisible = new RPAction();

		invisible.put("type", "invisible");

		ClientSingletonRepository.getClientFramework().send(invisible);

		return true;
	}
}
