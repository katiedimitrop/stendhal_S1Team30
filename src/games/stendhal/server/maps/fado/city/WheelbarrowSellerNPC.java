package games.stendhal.server.maps.fado.city;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import games.stendhal.common.grammar.ItemParserResult;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.mapstuff.block.Wheelbarrow;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;
import games.stendhal.server.entity.player.Player;

public class WheelbarrowSellerNPC implements ZoneConfigurator {

	public static final int BUYING_PRICE = 40;
	
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildFadoCityArea(zone);
	}

	private void buildFadoCityArea(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Hadvar") {
			@Override
			protected void createPath() {
				setPath(null);
			}
			
			@Override
			protected void createDialog() 
			{
				class WheelbarrowSellerBehaviour extends SellerBehaviour 
				{
					WheelbarrowSellerBehaviour(final Map<String, Integer> items) 
					{
						super(items);
					}

					@Override
					public boolean transactAgreedDeal(ItemParserResult res, final EventRaiser seller, final Player player) {
						if (res.getAmount() > 1) {
							seller.say("Sorry! I can only sell one wheelbarrow.");
							return false;
						} else if (!(seller.getEntity().nextTo(player))) {
							if (!player.drop("money", getCharge(res, player))) {
								seller.say("You don't seem to have enough money.");
								return false;
							}
							seller.say("Here you go, your own wheelbarrow! Use it well.");

							final Wheelbarrow wheelbarrow = new Wheelbarrow(true, player);
							StendhalRPAction.placeat(seller.getZone(), wheelbarrow, seller.getX(), seller.getY() + 1);
							StendhalRPAction.placeat(seller.getZone(), wheelbarrow.wheelbarrowChest, seller.getX() + 1, seller.getY() + 1);
							//seller.getZone().add(wheelbarrow.wheelbarrowChest);
							player.notifyWorldAboutChanges();

							return true;
						} else {
							say("I need space to give you your wheelbarrow, please give me some room.");
							return false;
						}
					}
				}

				final Map<String, Integer> items = new HashMap<String, Integer>();
				items.put("wheelbarrows", BUYING_PRICE);

				addGreeting();
				addJob("I work as a wheelbarrow seller.");
				addHelp("I sell wheelbarrows. To buy one, just tell me you want to #buy #wheelbarrow.");
				addGoodbye();
				new SellerAdder().addSeller(this, new WheelbarrowSellerBehaviour(items));
			}
		};

		npc.setEntityClass("sellernpc");
		npc.setDescription("Hadvar stands awaiting buyers to purchase his wheelbarrows. "
				+ "His wheelbarrows don't last forever and are only used in fado city.");
		npc.setPosition(36, 44);
		npc.initHP(100);
		npc.setSounds(Arrays.asList("cough-11", "cough-2", "cough-3"));
		zone.add(npc);
	}

}
