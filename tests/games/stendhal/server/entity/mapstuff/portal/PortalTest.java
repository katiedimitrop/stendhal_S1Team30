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
package games.stendhal.server.entity.mapstuff.portal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
//import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
//import games.stendhal.server.core.config.XMLUtil;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.EntityTestHelper;
import utilities.RPClass.PortalTestHelper;
import org.w3c.dom.*;
//import javax.xml.parsers.*;
//import java.io.*;

public class PortalTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		Log4J.init();
		MockStendlRPWorld.get();
		EntityTestHelper.generateRPClasses();
		PortalTestHelper.generateRPClasses();
	}

	/**
	 * Tests for toString.
	 */
	@Test
	public final void testToString() {

		final Portal port = new Portal();
		assertEquals("Portal[0,0]", port.toString());
	}

	/**
	 * Tests for isHidden.
	 */
	@Test
	public final void testIsHidden() {
		final Portal port = new Portal();
		assertFalse(port.isHidden());
		port.put("hidden", "You don't see this object");
		assertTrue(port.isHidden());
	}

	/**
	 * Tests for setGetIdentifier.
	 */
	@Test
	public final void testSetGetIdentifier() {

		final Portal port = new Portal();
		final Object o = new Object();
		port.setIdentifier(o);
		assertSame(o, port.getIdentifier());
	}

	/**
	 * Tests for destination.
	 */
	@Test
	public final void testDestination() {

		final Portal port = new Portal();
		final Object ref = new Object();
		port.setDestination("zonename", ref);
		assertTrue(port.loaded());
		assertSame(ref, port.getDestinationReference());
		assertEquals("zonename", port.getDestinationZone());
	}

	/**
	 * Tests for usePortalWithNoDestination.
	 */
	@Test
	public final void testUsePortalWithNoDestination() {

		final Portal port = new Portal();
		final Player player = PlayerTestHelper.createPlayer("player");
		assertFalse("port has no destination", port.usePortal(player));
	}

	/**
	 * Tests for usePortalNotNextToPlayer.
	 */
	@Test
	public final void testUsePortalNotNextToPlayer() {

		// there is a bit of pathfinding now in the portal code (if you are a distance from it)
		// so we need a 'zone' defined

		final Portal port = new Portal();
		port.setPosition(1, 1);
		final StendhalRPZone testzone = new StendhalRPZone("admin_test");
		testzone.collisionMap.init(10, 10);

		final Player player = PlayerTestHelper.createPlayer("player");

		final Object ref = new Object();
		port.setDestination("zonename", ref);
		final Portal destPort = new Portal();
		destPort.setIdentifier(ref);
		final StendhalRPZone zone = new StendhalRPZone("zonename");
		zone.add(destPort);
		testzone.add(port);
		testzone.add(player);

		MockStendlRPWorld.get().addRPZone(testzone);
		MockStendlRPWorld.get().addRPZone(zone);

		player.setPosition(5, 5);
		assertTrue("player is in original zone now", player.getZone().equals(testzone));
		assertTrue("portal is in original zone now", port.getZone().equals(testzone));
		assertFalse("player is not next to portal", port.nextTo(player));
		assertFalse("portal is not next to player, won't walk through but will set a path", port.usePortal(player));
		assertTrue("player was set on a path", player.hasPath());
		// would be nice to test but we would have to iterate the turns manually
		// and this is rather more part of the pathfinding which makes the player change zone, not the portal
		// assertTrue("player is in destination zone now", player.getZone().equals(zone));
	}

	/**
	 * Tests for usePortalHasInvalidDestination.
	 */
	@Test
	public final void testUsePortalHasInvalidDestination() {

		final Portal port = new Portal();
		final Player player = PlayerTestHelper.createPlayer("player");
		final Object ref = new Object();
		port.setDestination("zonename", ref);
		assertFalse("port has invalid destination", port.usePortal(player));
	}

	/**
	 * Tests for usePortalHasInvalidDestinationReference.
	 */
	@Test
	public final void testUsePortalHasInvalidDestinationReference() {

		final Portal port = new Portal();
		final Player player = PlayerTestHelper.createPlayer("player");
		final Object ref = new Object();
		port.setDestination("zonename", ref);
		final StendhalRPZone zone = new StendhalRPZone("zonename");
		MockStendlRPWorld.get().addRPZone(zone);
		assertFalse("port has invalid destination", port.usePortal(player));
	}

	/**
	 * Tests for usePortal.
	 */
	@Test
	public final void testUsePortal() {

		final Portal port = new Portal();
		final Player player = PlayerTestHelper.createPlayer("player");
		final Object ref = new Object();
		port.setDestination("zonename", ref);
		final Portal destPort = new Portal();
		destPort.setIdentifier(ref);
		final StendhalRPZone zone = new StendhalRPZone("zonename");
		zone.add(destPort);
		MockStendlRPWorld.get().addRPZone(zone);
		assertTrue("all things are nice", port.usePortal(player));
	}

	/**
	 * Tests for onUsed.
	 */
	@Test
	public final void testOnUsed() {
		final Portal port = new Portal() {
			@Override
			protected boolean usePortal(final Player player) {
				player.setName("renamed-" + player.getName());
				return false;
			}
		};
		final Player bob = PlayerTestHelper.createPlayer("bob");
		port.usePortal(bob);
		assertEquals("renamed-bob", bob.getName());
	}

	/**
	 * Tests for onUsedBackwards.
	 */
	@Test
	public final void testOnUsedBackwards() {
		final Portal port = new Portal();
		final Player player = PlayerTestHelper.createPlayer("player");
		port.onUsedBackwards(player);
	}

	/**
	 *  Test nalwor portal destinations
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	@Test
	public final void testNarwalPortals() throws ParserConfigurationException, SAXException, IOException
	{
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder = factory.newDocumentBuilder();
	     Document document = builder.parse(new File("data/conf/zones/nalwor.xml"));
         Element zones = document.getDocumentElement();
         //list of all <zone> elements below <zones> parent 
         NodeList list1 = zones.getElementsByTagName("zone");
         Element firstZone = (Element) list1.item(4);
         //create list of portals in 0_nalwor_forest_w
         NodeList list2 = firstZone.getElementsByTagName("portal");
         
         Element expectedPortal1 = (Element) list2.item(0);
         Element actualPortal1 = (Element) list2.item(1);
         Element expectedPortal2 = (Element) list2.item(2); 
         Element actualPortal2 = (Element) list2.item(3);
         
         int expectedPortal1X = Integer.parseInt(expectedPortal1.getAttribute("x"));
         int expectedPortal1Y = Integer.parseInt(expectedPortal1.getAttribute("y"));
         
         int actualPortal1X = Integer.parseInt(actualPortal1.getAttribute("x"));
         int actualPortal1Y = Integer.parseInt(actualPortal1.getAttribute("y"));
         
         int expectedPortal2X = Integer.parseInt(expectedPortal2.getAttribute("x"));
         int expectedPortal2Y = Integer.parseInt(expectedPortal2.getAttribute("y"));
               
         int actualPortal2X = Integer.parseInt(actualPortal2.getAttribute("x"));
         int actualPortal2Y = Integer.parseInt(actualPortal2.getAttribute("y"));
         
         
         assertTrue((expectedPortal1X == actualPortal1X)&&(expectedPortal2X == actualPortal2X)&&(expectedPortal1Y == actualPortal1Y - 2)&&(expectedPortal2Y == actualPortal2Y - 2));
	}
	
}

