package games.stendhal.server.entity.mapstuff.block;

import games.stendhal.client.entity.Chest;

public class Wheelbarrow extends Block {

	private Chest wheelbarrowChest;
	
	public Wheelbarrow(boolean multiPush) {
		super(multiPush);
		wheelbarrowChest = new Chest();
		wheelbarrowChest.initialize(this);
	}

}
