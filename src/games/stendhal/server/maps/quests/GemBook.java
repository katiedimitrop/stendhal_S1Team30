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
package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;

public class GemBook extends AbstractQuest {

	private static final String QUEST_SLOT = "seeking_book";
	private static Logger logger = Logger.getLogger(GemBook.class);

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}
	
	@Override
	public List<String> getHistory(final Player player) {
		final String questState = player.getQuest(QUEST_SLOT);
		final List<String> res = new ArrayList<String>();
		
		if (questState.equals("seeking_book")) {
			return res;
		}
		res.add("If you want to learn about gem book, chat to my friend Ceryl in Semos library, which has a good collection of books on mining related subjects.");
		
		
		final List<String> debug = new ArrayList<String>();
		debug.add("Quest state is: " + questState);
		logger.error("History doesn't have a matching quest state for " + questState);
		return debug;
	}

	
	private void requestBlueBook() {
		final SpeakerNPC npc = npcs.get("Wikipedian");
		
		npc.add(ConversationStates.ATTENDING,
				"gem book",
				new AndCondition(new QuestInStateCondition(QUEST_SLOT, "seeking_book"), new QuestCompletedCondition("ceryl_book")),
				ConversationStates.ATTENDING,
				"If you want to learn about gem book, chat to my friend Ceryl in Semos library, which has a good collection of books on mining related subjects.",
				null);
	}
	
	@Override
	public void addToWorld() {
		requestBlueBook();
	}

	@Override
	public String getName() {
		return "GemBook";
	}

	
	@Override
	public String getNPCName() {
		return "Wikipedian";
	}

	@Override
	public String getRegion() {
		return Region.ADOS_CITY;
	}

	

}
