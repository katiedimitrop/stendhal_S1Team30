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

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.entity.User;
import games.stendhal.client.gui.chatlog.StandardEventLine;
import games.stendhal.common.Constants;
import games.stendhal.common.EquipActionConsts;
import games.stendhal.common.grammar.Grammar;
import marauroa.common.game.RPAction;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Drop a player item.
 */
class DropAction implements SlashAction {

	/**
	 * Execute a chat command.
	 *
	 * @param params
	 *            The formal parameters.
	 * @param remainder
	 *            Line content after parameters.
	 *
	 * @return <code>true</code> if command was handled.
	 */
	@Override
	public boolean execute(final String[] params, final String remainder) {
		int quantity;
		String itemName;

		// Is there a numeric expression as first parameter?
		if (params[0].matches("[0-9].*")) {
			try {
				quantity = Integer.parseInt(params[0]);
			} catch (final NumberFormatException ex) {
				ClientSingletonRepository.getUserInterface().addEventLine(new StandardEventLine("Invalid quantity: " + params[0]));
				return true;
			}

			itemName = remainder;
		} else {
			quantity = 1;
			itemName = (params[0] + " " + remainder).trim();
		}

		final String singularItemName = Grammar.singular(itemName);

		for (final String slotName : Constants.CARRYING_SLOTS) {
			int itemID = User.get().findItem(slotName, itemName);

			// search again using the singular, in case it was a plural item name
			if ((itemID == -1) && !itemName.equals(singularItemName)) {
				itemID = User.get().findItem(slotName, singularItemName);
			}

			if (itemID != -1) {
				final RPAction drop = new RPAction();

				drop.put(EquipActionConsts.TYPE, "drop");
				drop.put(EquipActionConsts.BASE_OBJECT, User.get().getObjectID());
				drop.put(EquipActionConsts.BASE_SLOT, slotName);
				drop.put(EquipActionConsts.GROUND_X, (int) User.get().getX());
				drop.put(EquipActionConsts.GROUND_Y, (int) User.get().getY());
				drop.put(EquipActionConsts.QUANTITY, quantity);
				drop.put(EquipActionConsts.BASE_ITEM, itemID);

				ClientSingletonRepository.getClientFramework().send(drop);
				return true;
			}
		}
		ClientSingletonRepository.getUserInterface().addEventLine(new StandardEventLine("You don't have any " + singularItemName));
		return true;
	}

	/**
	 * Get the maximum number of formal parameters.
	 *
	 * @return The parameter count.
	 */
	@Override
	public int getMaximumParameters()
	{
		int maxNoOfParameters;
	
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     Document document = null;
		try {
			document = builder.parse(new File("data/conf/actions/drop.xml"));
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         //Get actions element
	     Element actions = document.getDocumentElement();
         //Make list of actions under element
	     NodeList actionList = actions.getElementsByTagName("action");
	     //Make element out of first action
	     Element action = (Element) actionList.item(0);
	     
	     //Make a list of attributes
	     NodeList attributeList = action.getElementsByTagName("attributes");
	     
	     //Make element out of first attribute
	     Element attribute = (Element) attributeList.item(0);
	     //Make list out of parameters in attribute
	     NodeList parameterList = attribute.getElementsByTagName("max-parameters");
	     //Make element out of first parameter
	     Element maxParameterTag = (Element) parameterList.item(0);
	     //get its value
	     
        
         maxNoOfParameters = Integer.parseInt(maxParameterTag.getAttribute("value"));
       
         
		return maxNoOfParameters;
	}

	/**
	 * Get the minimum number of formal parameters.
	 *
	 * @return The parameter count.
	 */
	@Override
	public int getMinimumParameters() {
		
		
		return 1;
	}
}
