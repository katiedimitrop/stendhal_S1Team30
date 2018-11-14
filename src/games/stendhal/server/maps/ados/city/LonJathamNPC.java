package games.stendhal.server.maps.ados.city;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class LonJathamNPC implements ZoneConfigurator {

	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Lon Jatham") {

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(7, 6));
				nodes.add(new Node(5, 6));
				nodes.add(new Node(5, 10));
				nodes.add(new Node(7, 10));
				setPath(new FixedPath(nodes, true));
			}
			
			@Override
			protected void createDialog() {
				addGreeting("Hi!!!Interested in #computer #science? You've come to the right place!");
				addJob("I'm a lecturer at University of Manchester. Ask me any questions about studying in School of #Computer #Science.");
				addReply("computer science","You'll be learning Algorithms, Machine Learning ...");
				addReply("yes","You'll be learning Algorithms, Machine Learning ...");
				addGoodbye("Bye bye!");
 			}
		
		};
		npc.setDescription("You see a lecturer of Computer Science. Ask him anything about computer science.");
		npc.setEntityClass("madscientistnpc");
		npc.setPosition(7, 6);

		npc.initHP(100);
		
		zone.add(npc);
	}
}

