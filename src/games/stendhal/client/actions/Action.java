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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Alter an entity's attributes.
 */
abstract class Action implements SlashAction 
{
    //Command name of the action 
    
     //All subclasses must provide this
     protected abstract String getCommandName();
     
	 //Create path of xml file for parsing
     String xmlPath = "data/conf/actions/" + getCommandName()+".xml";
     
     /**
	 * Executes a chat command.
	 *
	 * @param params
	 *            The formal parameters.
	 * @param remainder
	 *            Line content after parameters.
	 *
	 * @return <code>true</code> if was handled.
	 */
	@Override
	public boolean execute(final String[] params, final String remainder) 
	{
		return true;
	}

	
	/**
	 * Gets the maximum number of formal parameters.
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
			document = builder.parse(new File(xmlPath));
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
	 * Gets the minimum number of formal parameters.
	 *
	 * @return The parameter count.
	 */
	@Override
	public int getMinimumParameters() 
	{
		int minNoOfParameters;
		
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
			document = builder.parse(new File(xmlPath));
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
	     NodeList parameterList = attribute.getElementsByTagName("min-parameters");
	     //Make element out of first parameter
	     Element minParameterTag = (Element) parameterList.item(0);
	     //get its value
	     
      
        minNoOfParameters = Integer.parseInt(minParameterTag.getAttribute("value"));
     
       
		return minNoOfParameters;
	}
}
