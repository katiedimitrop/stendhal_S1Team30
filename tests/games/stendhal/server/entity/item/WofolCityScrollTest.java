package games.stendhal.server.entity.item;

import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

//import java.util.Arrays;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.common.constants.Actions;
//import games.stendhal.server.actions.UseAction;
//import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.entity.item.scroll.MarkedScroll;
import games.stendhal.server.entity.item.scroll.WofolCityScroll;
//import games.stendhal.server.entity.item.scroll.WofolCityScroll;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
//import marauroa.common.game.RPAction;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class WofolCityScrollTest
{
	//private static final String ZONE_NAME = "ITEMTESTZONE";
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();

	}
   @Test
   public void shouldAllowPlayerToUseWofolScrollToGetToWofolCity()
   {
	   MockStendlRPWorld.get();
	   final StendhalRPZone zone = new StendhalRPZone("TestZone");
	  
	   int otherSideOfScrollX = 90;
 	   int otherSideOfScrollY = 90;
	   final String destZone = "-1_semos_mine_nw";
	   final Player player  =  PlayerTestHelper.createPlayer("wcbob");
	   //PlayerTestHelper.registerPlayer(player,zone.getName());
	   zone.add(player);
	   
	   //Give the player a scroll to Wofol city(in their bag). name,class,subclass,map
	   final WofolCityScroll wofolScroll = new WofolCityScroll("wofol city scroll","","", new HashMap<String,String>());
	   player.equip("bag", wofolScroll);
	   
	   assertTrue(player.isEquipped("wofol city scroll"));
	   ///Execute the test
	   wofolScroll.callUseScroll(player);
	   
	   //How to get right zone name returned??
	   assertThat((player.getZone().getName()),is(destZone));
	   
	   //Check that player is at specific coordinates and in wofol city
	   assertThat(player.getX(),is(otherSideOfScrollX));
	   assertThat(player.getY(),is(otherSideOfScrollY));
	   
	   
	   ///Tidy up
	   
	 
   }
   
   @Test
   public void testDescribe()
   {   
	   final WofolCityScroll wofolScroll = new WofolCityScroll("wofol city scroll","","", new HashMap<String,String>());
	   assertThat(wofolScroll.describe(), is("You see a §'wofol city scroll'. Use it to teleport to Wofol City."));

   }
}
