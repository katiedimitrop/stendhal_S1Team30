package games.stendhal.server.entity.mapstuff.block;

import games.stendhal.server.entity.player.Player;

import java.awt.geom.Rectangle2D;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.ActiveEntity;
import games.stendhal.server.entity.mapstuff.chest.PersonalChest;;

public class Wheelbarrow extends Block {

	public PersonalChest wheelbarrowChest;
	
	private Player wheelbarrowOwner;
	
	public Wheelbarrow(boolean multiPush, Player player) {
		super(multiPush);
		wheelbarrowOwner = player;
		wheelbarrowChest = new PersonalChest();
		wheelbarrowChest.setPosition(this.getX(), this.getY());
	}
	
	@Override
	public void beforeMove(ActiveEntity entity, StendhalRPZone zone, int oldX,
			int oldY, int newX, int newY) {
		if (entity instanceof Player) {
			wheelbarrowChest.setPosition(newX, newY);
			Rectangle2D oldA = new Rectangle2D.Double(oldX, oldY, entity.getWidth(), entity.getHeight());
			Rectangle2D newA = new Rectangle2D.Double(newX, newY, entity.getWidth(), entity.getHeight());
			Direction d = Direction.getAreaDirectionTowardsArea(oldA, newA);
			this.push((Player) entity, d);
			wheelbarrowChest.update();
			wheelbarrowChest.close();
			wheelbarrowChest.open(wheelbarrowOwner);
			this.notifyAll();
		}
	}
	
	@Override
	public void onMoved(ActiveEntity entity, StendhalRPZone zone, int oldX,
			int oldY, int newX, int newY) {
		wheelbarrowChest.update();
		wheelbarrowChest.open(wheelbarrowOwner);
	}
	
	@Override
	public String getDescription() {
		wheelbarrowChest.open(wheelbarrowOwner);
		return "You see a wheelbarrow wheel.";
	}

}
