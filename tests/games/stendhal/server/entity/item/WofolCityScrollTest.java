package games.stendhal.server.entity.item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.scroll.MarkedScroll;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

/*
 * @author Katie Dimitropoulaki
 */
public class WofolCityScrollTest
{
	//private static final String ZONE_NAME = "ITEMTESTZONE";
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
		MockStendlRPWorld.get().addRPZone(new StendhalRPZone("-1_semos_mine_nw", 100, 100));

	}
   @Test
   public void shouldAllowPlayerToUseWofolScrollToGetToWofolCity()
   {
	   MockStendlRPWorld.get();
	   final StendhalRPZone zone = new StendhalRPZone("TestZone");
	   final Player player  =  PlayerTestHelper.createPlayer("wcbob");
	   PlayerTestHelper.registerPlayer(player, "-1_semos_mine_nw");
	   zone.add(player);
       player.setPosition(4,6);
	   final MarkedScroll wofolScroll =  (MarkedScroll) SingletonRepository.getEntityManager().getItem("wofol city scroll");
       wofolScroll.setInfoString(wofolScroll.getInfoString());
       zone.add(wofolScroll, false);
       wofolScroll.setPosition(4,5);
	   
	   wofolScroll.callUseScroll(player);
	   
	   //Compare player position with Infostring of scroll, which contains it's destination zone, and destination coordinates 
	   assertThat(player.getZone().getName()+" "+player.getX() +" "+player.getY(),is(wofolScroll.getInfoString()));
   }
   
   @Test
   public void testDescribe()
   {   
	   final MarkedScroll wofolScroll = new MarkedScroll("wofol city scroll","","", new HashMap<String,String>());
	   wofolScroll.setInfoString(wofolScroll.getInfoString());
	   assertThat(wofolScroll.describe(), is("You see a §'wofol city scroll'."));

   }
}
