package games.stendhal.server.maps.ados.city;

import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class UoMStudentNPC implements ZoneConfigurator {

	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("UoM Student") {

			@Override
			protected void createPath() {
				setPath(null);
			}
					
		};
		npc.setDescription("She learns Java from Lon Jatham. Go talk to him if you want to know anything about computer science.");
		npc.setEntityClass("littlegirl2npc");
		npc.setPosition(3, 5);

		zone.add(npc);
	}
}

