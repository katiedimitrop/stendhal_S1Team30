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
			Rectangle2D oldA = new Rectangle2D.Double(oldX, oldY, entity.getWidth(), entity.getHeight());
			Rectangle2D newA = new Rectangle2D.Double(newX, newY, entity.getWidth(), entity.getHeight());
			Direction d = Direction.getAreaDirectionTowardsArea(oldA, newA);
			if (!(zone.collides(entity, newX + d.getdx() * 2, newY + d.getdy() * 2))) 
			{
				wheelbarrowChest.setPosition(newX + d.getdx() * 2, newY + d.getdy() * 2);
				this.push((Player) entity, d);
				wheelbarrowChest.open(wheelbarrowOwner);
			}
		}
	}
	
	@Override
	public String getDescription() {
		return "You see a wheelbarrow wheel.";
	}

}
