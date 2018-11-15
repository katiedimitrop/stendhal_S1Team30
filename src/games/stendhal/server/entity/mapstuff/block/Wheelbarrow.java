package games.stendhal.server.entity.mapstuff.block;

public class Wheelbarrow extends Block{

	public   int chestposition = 0;
	public  int position = 0;

	public Wheelbarrow(boolean multipush) {
		super(multipush);
	}


}

/*
import static games.stendhal.common.constants.Actions.MOVE_CONTINUOUS;
import java.awt.geom.Rectangle2D;
import org.apache.log4j.Logger;
import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.mapstuff.block.BlockTarget;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.mapstuff.puzzle.PuzzleBuildingBlock;
import marauroa.common.game.Definition;
import marauroa.common.game.Definition.Type;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import games.stendhal.common.MathHelper;
import games.stendhal.common.Rand;
import games.stendhal.common.constants.Nature;
import games.stendhal.common.grammar.Grammar;
//import games.stendhal.server.core.engine.ItemLogger;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.events.EquipListener;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.UseListener;
//import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.behavior.UseBehavior;
import games.stendhal.server.entity.mapstuff.spawner.PassiveEntityRespawnPoint;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.StatusType;
import marauroa.common.game.RPSlot;

import games.stendhal.server.entity.slot.ContainerItemSlot;
import java.util.Collections;
import games.stendhal.common.constants.SoundLayer;
import games.stendhal.server.core.events.MovementListener;
import games.stendhal.server.core.events.ZoneEnterExitListener;
import games.stendhal.server.entity.ActiveEntity;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.events.SoundEvent;




public class Wheelbarrow extends Entity implements TurnListener, EquipListener,UseListener,ZoneEnterExitListener, MovementListener
{
  //Activeentity
  private static final Logger logger = Logger.getLogger(Entity.class);
	private Direction direction;
	private double speed;
	private double movementOffset;
	private int stepsTaken;
	private boolean ignoreCollision;


	protected void move(final int x, final int y, final int nx, final int ny) {
		setPosition(nx, ny);
		notifyWorldAboutChanges();
	}

	protected boolean handlePortal(final Portal portal) {
		return false;
	}

	protected void handleLeaveZone(final int nx, final int ny) {
		logger.debug("Leaving zone from (" + getX() + "," + getY() + ") to ("
				+ nx + "," + ny + ")");
		StendhalRPAction.decideChangeZone(this, nx, ny);
		if (!has(MOVE_CONTINUOUS)) {
			stop();
		}

		notifyWorldAboutChanges();
	}

	private static RPClass createRPClass() {
		final RPClass rpclass = new RPClass("active_entity");

		rpclass.isA("entity");
		rpclass.addAttribute("dir", Type.BYTE, Definition.VOLATILE);
		rpclass.addAttribute("speed", Type.FLOAT, Definition.VOLATILE);

		return rpclass;
	}


	

	protected boolean isZoneChangeAllowed() {
		return false;
	}
//#########################################################

//###########################################################

//#####################################################
	@Override
	public void update() {
		super.update();

		if (has("dir")) {
			direction = Direction.build(getInt("dir"));
		}

		if (has("speed")) {
			speed = getDouble("speed");
		}
	}

	public boolean isGhost() {
		return false;
	}
//#########################################################
	@Override
	public int getResistance() {
		if (isGhost()) {
			return 0;
		}
		return super.getResistance();
	}

	public final void faceto(final int x, final int y) {
		final int rndx = x - getX();
		final int rndy = y - getY();

		if (Math.abs(rndx) > Math.abs(rndy)) {
			if (rndx < 0.0) {
				setDirection(Direction.LEFT);
			} else {
				setDirection(Direction.RIGHT);
			}
		} else {
			if (rndy < 0.0) {
				setDirection(Direction.UP);
			} else {
				setDirection(Direction.DOWN);
			}
		}
	}

	public final void faceToward(final Entity entity) {
		setDirection(getDirectionToward(entity));
	}

	public Direction getDirection() {
		return direction;
	}

	public final Direction getDirectionToward(final Entity entity) {
		return getDirectionToward(entity.getArea());
	}

	final Direction getDirectionToward(final Rectangle2D area) {
		return Direction.getAreaDirectionTowardsArea(getArea(), area);
	}

	public boolean isFacingToward(final Entity entity) {
		return direction.equals(getDirectionToward(entity));
	}

	public void setDirection(final Direction dir) {
		if (dir == this.direction) {
			return;
		}

		this.direction = dir;
		put("dir", direction.get());
	}

	public void applyMovement() {
		// even if we could we would not move;
		if (speed == 0) {
			stepsTaken = 0;
			return;
		}

		if (direction == Direction.STOP) {
			stepsTaken = 0;
			return;
		}

		final int x = getX();
		final int y = getY();
		final int nx = x + direction.getdx();
		final int ny = y + direction.getdy();

		final StendhalRPZone zone = getZone();


		if (!ignoresCollision()) {
			if (zone.simpleCollides(this, nx, ny, this.getWidth(), this.getHeight())) {
				handleSimpleCollision(nx, ny);
				return;
			}
		}
		final Portal p = zone.getPortal(nx, ny);
		if (p != null) {
			if (handlePortal(p)) {
				return;
			}
		}

		if (isGhost()) {
			if (isMoveCompleted()) {
				move(x, y, nx, ny);
				return;
			}
		}

		final boolean collision = zone.collidesObjects(this, this.getArea(nx, ny));

		if (collision) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("Collision at (" + nx + "," + ny + ")");
			}
			handleObjectCollision();
		} else {
			if (!isMoveCompleted()) {
				if (logger.isDebugEnabled()) {
					logger.debug(get("type") + ") move not completed");
				}
				return;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Moving from (" + x + "," + y + ") to (" + nx
						+ "," + ny + ")");
			}

			move(x, y, nx, ny);
			stepsTaken += 1;
		}
	}

	public double getSpeed() {
		return speed;
	}

	public int getStepsTaken() {
	    return stepsTaken;
	}

	protected boolean isMoveCompleted() {
		movementOffset += getSpeed();

		if (movementOffset >= 1.0) {
			movementOffset -= 1.0;
			return true;
		} else {
			return false;
		}
	}
//###################################################
	@Override
	protected void onMoved(final int oldX, final int oldY, final int newX, final int newY) {
		//getZone().notifyMovement(this, oldX, oldY, newX, newY);
	}

	public void setSpeed(final double speed) {
		if (speed == this.speed) {
			return;
		}

		this.speed = speed;
		put("speed", speed);
		notifyWorldAboutChanges();
	}

	public void stop() {
		setSpeed(0.0);
		movementOffset = 0.0;
	}
//###########################################################
	@Override
	public boolean stopped() {
		return (speed == 0.0);
	}

	protected void handleObjectCollision() {
		// implemented by sub classes
	}

	protected void handleSimpleCollision(final int nx, final int ny) {
		if (isZoneChangeAllowed()) {
			if (getZone().leavesZone(this, nx, ny)) {
				handleLeaveZone(nx, ny);
				return;
			}
		}
		if (isGhost()) {
			move(getX(), getY(), nx, ny);
		}
	}

	public boolean ignoresCollision() {
		return ignoreCollision;
	}

	public void setIgnoresCollision(boolean ignore) {
		ignoreCollision = ignore;
		if (ignore) {
			put("ignore_collision", "");
		} else {
			remove("ignore_collision");
		}
	}


//PassiveEntity##################################################



   //Item#################################################################

      private static final int DEFAULT_ATTACK_RATE = 5;

    	private static final int MAX_DETERIORATION = 100;

    	private static final int DEFAULT_DETERIORATION = 0;

    	private static final long MEAN_LIFETIME = 12 * MathHelper.MILLISECONDS_IN_ONE_HOUR;

    	public static final int DEGRADATION_TIMEOUT = 10 * MathHelper.SECONDS_IN_ONE_MINUTE;

    	private List<String> possibleSlots;

    	private PassiveEntityRespawnPoint plantGrower;

    	private Nature damageType = Nature.CUT;

    	private Map<Nature, Double> susceptibilities;

    	private boolean fromCorpse = false;

    	private UseBehavior useBehavior;



    	
    	public void setEquipableSlots(final List<String> slots) {
    		// save slots
    		possibleSlots = slots;
    	}

    	public void setPlantGrower(final PassiveEntityRespawnPoint plantGrower) {
    		this.plantGrower = plantGrower;
    	}

    	public PassiveEntityRespawnPoint getPlantGrower() {
    		return plantGrower;
    	}

    	public int getAttack() {
    		if (has("atk") && getDeterioration() <= MAX_DETERIORATION) {
    			return getInt("atk");
    		}

    		return 0;
    	}

    	public int getDefense() {
    		if (has("def") && getDeterioration() <= MAX_DETERIORATION) {
    			return getInt("def");
    		}

    		return 0;
    	}

    	public int getRangedAttack() {
    		if (has("ratk") && getDeterioration() <= MAX_DETERIORATION) {
    			return getInt("ratk");
    		}

    		return 0;
    	}

    	public int getAttackRate() {
    		if (has("rate")) {
    			return getInt("rate");
    		}

    		return DEFAULT_ATTACK_RATE;
    	}

    	public int getDeterioration() {
    		if(has("deterioration")) {
    			return getInt("deterioration");
    		}
    		return DEFAULT_DETERIORATION;
    	}

    	public void deteriorate() {
    		double propabilityForMeanExp = Rand.propabilityForMeanExp(MEAN_LIFETIME / 300 * this.getAttackRate());
    		if(Rand.flipCoin(propabilityForMeanExp) && getDeterioration() <= MAX_DETERIORATION) {
    			Logger.getLogger(getClass()).debug("The item"+ this.getName() +"deteriorated from "+this.getDeterioration()+".");
    //			this.add("deterioration", 1);
    		} else {
    			Logger.getLogger(getClass()).debug("The item"+ this.getName() +"did not deteriorate from "+this.getDeterioration()+".");
    		}
    	}

    	public void repair() {
    		if(has("deterioration")) {
    			put("deterioration", DEFAULT_DETERIORATION);
    		}
    	}

    	public boolean isPersistent() {
    		if (has("persistent")) {
    			return (getInt("persistent") == 1);
    		}

    		return false;
    	}

    	public void setPersistent(final boolean persistent) {
    		if (persistent) {
    			put("persistent", 1);
    		} else if (has("persistent")) {
    			remove("persistent");
    		}
    	}

    	public boolean isOfClass(final String clazz) {
    		return getItemClass().equals(clazz);
    	}

    	public String getItemClass() {
    		if (has("class")) {
    			return get("class");
    		}

    		throw new IllegalStateException("the item does not have a class: "
    				+ this);
    	}

    	public String getItemSubclass() {
    		if (has("subclass")) {
    			return get("subclass");
    		}

    		throw new IllegalStateException("the item does not have a subclass: "
    				+ this);
    	}
      //###########################################
    	@Override
    	public String getName() {
    		return get("name");
    	}

    	public int getQuantity() {
    		return 1;
    	}

    	public List<String> getPossibleSlots() {
    		return possibleSlots;
    	}

    	public String getBoundTo() {
    			return get("bound");
    	}

    	public boolean isBound() {
    		return has("bound");
    	}

    	public void autobind(String player) {
    		if (!isBound() && has("autobind")) {
    			setBoundTo(player);
    		}
    	}

    	public String getInfoString() {
    		if (has("infostring")) {
    			return get("infostring");
    		} else {
    			return null;
    		}
    	}

    	public void setBoundTo(final String name) {
    		if (name != null) {
    			put("bound", name);
    		} else if (has("bound")) {
    			remove("bound");
    		}
    	}

    	public boolean isUndroppableOnDeath() {
    		if (has("undroppableondeath")) {
    			return (getInt("undroppableondeath") == 1);
    		}

    		return false;
    	}

    	public void setUndroppableOnDeath(final boolean unDroppableOnDeath) {
    		if (unDroppableOnDeath) {
    			put("undroppableondeath", 1);
    		} else if (has("undroppableondeath")) {
    			remove("undroppableondeath");
    		}
    	}

    	public void setUseBehavior(UseBehavior behavior) {
    		useBehavior = behavior;
    	}

    	public void setInfoString(final String infostring) {
    		if (infostring != null) { 
    			put("infostring", infostring);
    		} else if (has("infostring")) {
    			remove("infostring");
    		}
    	}

    	public Nature getDamageType() {
    		return damageType;
    	}

    	public void setDamageType(Nature type) {
    		damageType = type;
    	}

    	public double getSusceptibility(Nature type) {
    		double value = 1.0;
    		if (susceptibilities != null) {
    			Double sus = susceptibilities.get(type);
    			if (sus != null) {
    				value = sus.doubleValue();
    			}
    		}

    		return value;
    	}

    	public void setSusceptibilities(Map<Nature, Double> susceptibilities) {
    		this.susceptibilities = susceptibilities;
    	}

    	public void initializeActiveSlotsList(final List<String> slotList) {
    	}

    	public void initializeStatusResistancesList(Map<StatusType,
    			Double> resistanceList) {
    	}

    	public String getWeaponType() {
    		return getItemClass();
    	}
      //################################################################################
    	@Override
    	public String toString() {
    		return "Item, " + super.toString();
    	}

    	public void onPutOnGround(final Player player) {
    		onPutOnGround(true);
    	}

    	public void onPutOnGround(final boolean expire) {
    		if (expire) {
    			SingletonRepository.getTurnNotifier().notifyInSeconds(DEGRADATION_TIMEOUT, this);
    		}
    	}


//###################################################################################
   
 
//##############################################################################
    	@Override
    	public String describe() {
    		String text = "You see " + Grammar.a_noun(getTitle()) + ".";
    		StringBuilder stats = new StringBuilder();
    		String levelwarning = "";
    		if (hasDescription()) {
    			text = getDescription();
    		}
    		text = text.replace(getTitle(), "§'" + getTitle() + "'");

    		final String boundTo = getBoundTo();

    		if (boundTo != null) {
    			text = text + " It is a special reward for " + boundTo
    					+ ", and cannot be used by others.";
    		}

    		if (has("atk")) {
    			stats.append("ATK: ");
    			stats.append(get("atk"));
    			// Show only special types
    			if (getDamageType() != Nature.CUT) {
    				stats.append(" [");
    				stats.append(getDamageType());
    				stats.append("]");
    			}
    		}
    		if (has("def")) {
    			stats.append(" DEF: ");
    			stats.append(get("def"));
    		}
    		if (has("ratk")) {
    			stats.append(" RATK: ");
    			stats.append(get("ratk"));
    		}
    		if (has("rate")) {
    			stats.append(" RATE: ");
    			stats.append(get("rate"));
    		}
    		if (has("amount")) {
    			stats.append(" HP: ");
    			stats.append(get("amount"));
    		}
    		if (has("range")) {
    			stats.append(" RANGE: ");
    			stats.append(get("range"));
    		}
    		if (has("lifesteal")) {
    			stats.append(" LIFESTEAL: ");
    			stats.append(get("lifesteal"));
    		}
    		if ((susceptibilities != null) && !susceptibilities.isEmpty()) {
    			for (Entry<Nature, Double> entry : susceptibilities.entrySet()) {
    				stats.append(" ");
    				stats.append(entry.getKey());
    				stats.append(": ");
    				stats.append(Math.round(100/entry.getValue()));
    				stats.append("%");
    			}
    		}

    		if (has("min_level")) {
    			stats.append(" MIN-LEVEL: ");
    			stats.append(get("min_level"));
    		}
    		if (has("life_support")) {
    			stats.append(" LIFE-SUPPORT: ");
    			stats.append(get("life_support"));
    		}
    		String statString = "";
    		if (stats.length() > 0) {
    			statString =  " Stats are (" + stats.toString().trim() + ").";
    		}
    		return (text + levelwarning + statString);
    	}

    	public void removeOne() {
    		removeFromWorld();
    	}
//##########################################################################
    	@Override
    	public boolean canBeEquippedIn(final String slot) {
    		if (slot == null) {
    			return true;
    		}

    		return possibleSlots.contains(slot);
    	}


    	public void removeFromWorld() {

    		this.onUnequipped();

    		if (isContained()) {
    			RPObject base = getContainer();

    			while (base.isContained()) {
    				base = base.getContainer();
    			}

    			final RPSlot slot = getContainerSlot();
    			slot.remove(getID());

    			SingletonRepository.getRPWorld().modify(base);
    		} else {
    			SingletonRepository.getRPWorld().remove(getID());
    		}
    	}
//#########################################################################
    	@Override
    	public String getDescriptionName(final boolean definite) {
    		final String name = getName();

    		if (name != null) {
    			return name;
    		} else {
    			return super.getDescriptionName(definite);
    		}
    	}

//#########################################################################
    	@Override
    	public String getTitle() {
    		final String name = getName();

    		if (name != null) {
    			return name;
    		} else {
    			return super.getTitle();
    		}
    	}

    	public void setFromCorpse(boolean fromCorpse) {
    		this.fromCorpse = fromCorpse;
    	}

    	public boolean isFromCorpse() {
    		return fromCorpse;
    	}

    	public int getMinLevel() {
    		if (super.has("min_level")) {
    			return super.getInt("min_level");
    		} else {
    			return 0;
    		}
    	}

    	public boolean onEquipped(RPEntity equipper, String slot) {
    		return false;
    	}

    	public boolean onUnequipped() {
    		return false;
    	}
//#############################################



//block######################
//private static final Logger logger = Logger.getLogger(Block.class);

	static final int RESET_TIMEOUT_IN_SECONDS = 5 * MathHelper.SECONDS_IN_ONE_MINUTE;

	static final int RESET_AGAIN_DELAY = 10;

	private static final String Z_ORDER = "z";

	private int startX;
	private int startY;
	private boolean multi;

	private final List<String> sounds;

	private boolean resetBlock = true;
	private boolean wasMoved = false;


	private PuzzleBuildingBlock puzzleBuildingBlock;
	public void untrigger() {
		if (puzzleBuildingBlock != null) {
			puzzleBuildingBlock.put("active", false);
		}
	}

	public void reset() {
		wasMoved = false;
		List<Wheelbarrow> blockTargetsAt = this.getZone().getEntitiesAt(getX(), getY(), Wheelbarrow.class);
		for (Wheelbarrow blockTarget : blockTargetsAt) {
			blockTarget.untrigger();
		}
		this.setPosition(startX, startY);
		SingletonRepository.getTurnNotifier().dontNotify(this);
		this.notifyWorldAboutChanges();
	}

	public void push(Player p, Direction d) {
		if (!this.mayBePushed(d)) {
			return;
		}
		List<BlockTarget> blockTargetsAt = this.getZone().getEntitiesAt(getX(), getY(), BlockTarget.class);
		for (BlockTarget blockTarget : blockTargetsAt) {
			blockTarget.untrigger();
		}

		int x = getXAfterPush(d);
		int y = getYAfterPush(d);
		this.setPosition(x, y);
		blockTargetsAt = this.getZone().getEntitiesAt(x, y, BlockTarget.class);
		for (BlockTarget blockTarget : blockTargetsAt) {
			if (blockTarget.doesTrigger(this, p)) {
				blockTarget.trigger(this, p);
			}
		}
		if (resetBlock) {
			SingletonRepository.getTurnNotifier().dontNotify(this);
			SingletonRepository.getTurnNotifier().notifyInSeconds(RESET_TIMEOUT_IN_SECONDS, this);
		}
		wasMoved = true;
		this.sendSound();
		this.notifyWorldAboutChanges();
		if (logger.isDebugEnabled()) {
			logger.debug("Wheelbarrow [" + this.getID().toString() + "] pushed to (" + this.getX() + "," + this.getY() + ").");
		}
	} 

	public void setResetBlock(boolean resetBlock) {
		this.resetBlock = resetBlock;
	}

	private void sendSound() {
		if (this.sounds != null && !this.sounds.isEmpty()) {
			SoundEvent e = new SoundEvent(Rand.rand(sounds), SoundLayer.AMBIENT_SOUND);
			this.addEvent(e);
			this.notifyWorldAboutChanges();
		}
	}

	public int getYAfterPush(Direction d) {
		return this.getY() + d.getdy();
	}

	public int getXAfterPush(Direction d) {
		return this.getX() + d.getdx();
	}

	private boolean wasPushed() {
		boolean xChanged = this.getInt("x") != this.startX;
		boolean yChanged = this.getInt("y") != this.startY;
		return xChanged || yChanged;
	}

	private boolean mayBePushed(Direction d) {
		boolean pushed = wasPushed();
		int newX = this.getXAfterPush(d);
		int newY = this.getYAfterPush(d);

		if (!multi && pushed) {
			return false;
		}

		boolean collision = this.getZone().collides(this, newX, newY);

		return !collision;
	}

	public String getShape() {
		if (this.has("shape")) {
			return this.get("shape");
		}
		return null;
	}

//#########################################################################
	@Override
	public void onEntered(ActiveEntity entity, StendhalRPZone zone, int newX, int newY) {
	}

	@Override
	public void onExited(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY) {
		if (logger.isDebugEnabled()) {
			logger.debug("WHeelbarrow [" + this.getID().toString() + "] notified about entity [" + entity + "] exiting [" + zone.getName() + "].");
		}
		resetInPlayerlessZone(zone, entity);
	}

	@Override
	public void onMoved(ActiveEntity entity, StendhalRPZone zone, int oldX,
			int oldY, int newX, int newY) {
	}

	@Override
	public void onEntered(RPObject object, StendhalRPZone zone) {
	}

	@Override
	public void onExited(RPObject object, StendhalRPZone zone) {
		if (logger.isDebugEnabled()) {
			logger.debug("Wheelbarrow [" + this.getID().toString() + "] notified about object [" + object + "] exiting [" + zone.getName() + "].");
		}
		resetInPlayerlessZone(zone, object);
	}

	private void resetInPlayerlessZone(StendhalRPZone zone, RPObject object) {
		if (!resetBlock || !wasMoved) {
			return;
		}

		final List<Player> playersInZone = zone.getPlayers();
		int numberOfPlayersInZone = playersInZone.size();
		if (numberOfPlayersInZone == 0 || numberOfPlayersInZone == 1 && playersInZone.contains(object)) {
			resetIfInitialPositionFree();
		}
	}
//######################################################
	@Override
	public boolean isObstacle(Entity entity) {
		if (entity instanceof RPEntity) {
			return true;
		}

		return super.isObstacle(entity);
	}

	@Override
	public void beforeMove(ActiveEntity entity, StendhalRPZone zone, int oldX,
			int oldY, int newX, int newY) {
		if (entity instanceof Player) {
			Rectangle2D oldA = new Rectangle2D.Double(oldX, oldY, entity.getWidth(), entity.getHeight());
			Rectangle2D newA = new Rectangle2D.Double(newX, newY, entity.getWidth(), entity.getHeight());
			Direction d = Direction.getAreaDirectionTowardsArea(oldA, newA);
			this.push((Player) entity, d);
		}
	}
//################

	@Override
	public void onTurnReached(int currentTurn) {
		resetIfInitialPositionFree();
	}

	@Override
	public void onAdded(StendhalRPZone zone) {
		super.onAdded(zone);
		this.startX = getX();
		this.startY = getY();
		zone.addMovementListener(this);
		zone.addZoneEnterExitListener(this);
	}

	@Override
	public void onRemoved(StendhalRPZone zone) {
		super.onRemoved(zone);
		zone.removeMovementListener(this);
		zone.removeZoneEnterExitListener(this);
	}

	private void resetIfInitialPositionFree() {
		if (!this.getZone().collides(this, this.startX, this.startY)) {
			this.reset();
		} else {
			SingletonRepository.getTurnNotifier().notifyInSeconds(RESET_AGAIN_DELAY, this);
		}
	}


//container######################################################
private static final String DEFAULT_SLOT_NAME = "content";

	private static final int DEFAULT_SLOT_SIZE = 8;

	public Container(final String name, final String clazz, final String subclass,
			final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		String slotName = get("slot_name");
		if (slotName == null) {
			slotName = DEFAULT_SLOT_NAME;
		}

		RPSlot slot = new ContainerItemSlot(DEFAULT_SLOT_NAME, slotName);
		addSlot(slot);
		determineSlotCapacity(slot);
	}

	public Container(Container item) {
		super(item);
		RPSlot slot = getSlot(DEFAULT_SLOT_NAME);
		determineSlotCapacity(slot);
	} 

	private void determineSlotCapacity(RPSlot slot) {
		String slotSize = get("slot_size");
		int size = DEFAULT_SLOT_SIZE;
		if (slotSize == null) {
			put("slot_size", DEFAULT_SLOT_SIZE);
		} else {
			size = MathHelper.parseIntDefault(slotSize, DEFAULT_SLOT_SIZE);
		}
		slot.setCapacity(size);
	}


//JOINED METHODS############################

  @Override
  public void onTurnReached(final int currentTurn) {
    if (getZone() != null) {
      if (this.hasSlot("content")) {
        for (RPObject obj : getSlot("content")) {
         // new ItemLogger().timeout((Item) obj);
        }
      }
      getZone().remove(getID());
     // new ItemLogger().timeout(this);
    }
    resetIfInitialPositionFree();
  }


  @Override
  public void onAdded(StendhalRPZone zone) {
    super.onAdded(zone);
    this.startX = getX();
    this.startY = getY();
    zone.addMovementListener(this);
    zone.addZoneEnterExitListener(this);
  }

  
  public static void generateRPClass() {
      RPClass clazz = new RPClass("Wheelbarrow");
      clazz.isA("area");
      // z order to control client side drawing
      clazz.addAttribute(Z_ORDER, Type.INT);
      clazz.addAttribute("class", Type.STRING);
      clazz.addAttribute("shape", Type.STRING);

      
      final RPClass entity = new RPClass("item");
      entity.isA("entity");

      entity.addAttribute("class", Type.STRING);

      entity.addAttribute("subclass", Type.STRING);

      entity.addAttribute("name", Type.STRING);

      entity.addAttribute("atk", Type.SHORT, Definition.HIDDEN);

      entity.addAttribute("ratk", Type.SHORT, Definition.HIDDEN);

      entity.addAttribute("rate", Type.SHORT, Definition.HIDDEN);

      entity.addAttribute("def", Type.SHORT, Definition.HIDDEN);

      entity.addAttribute("deterioration", Type.INT, (byte) (Definition.HIDDEN | Definition.VOLATILE));

      entity.addAttribute("amount", Type.INT);

      entity.addAttribute("range", Type.SHORT, Definition.HIDDEN);

      entity.addAttribute("regen", Type.INT, Definition.HIDDEN);

      entity.addAttribute("frequency", Type.INT, Definition.HIDDEN);

      entity.addAttribute("quantity", Type.INT);

      entity.addAttribute("max_quantity", Type.INT, Definition.HIDDEN);

      entity.addAttribute("min_level", Type.INT, Definition.HIDDEN);

      entity.addAttribute("infostring", Type.STRING, Definition.HIDDEN);

      entity.addAttribute("persistent", Type.SHORT, Definition.HIDDEN);

      entity.addAttribute("lifesteal", Type.FLOAT, Definition.HIDDEN);

      entity.addAttribute("antipoison", Type.FLOAT, (byte) (Definition.HIDDEN | Definition.VOLATILE));

      entity.addAttribute("bound", Type.STRING);

      entity.addAttribute("undroppableondeath", Type.SHORT, Definition.HIDDEN);

      entity.addAttribute("life_support", Type.STRING, Definition.HIDDEN);

      entity.addAttribute("logid", Type.INT, Definition.HIDDEN);

      entity.addAttribute("slot_name", Type.STRING, (byte) (Definition.HIDDEN | Definition.VOLATILE));

      entity.addAttribute("slot_size", Type.STRING, Definition.VOLATILE);

      entity.addRPSlot("content", -1, Definition.PRIVATE);

      entity.addAttribute("autobind", Type.FLAG, (byte) (Definition.HIDDEN | Definition.VOLATILE));
    }

//HOPE THIS TO WORK REMEMBER TO DELETE BOTH COPIES OF CONSTRUCTORS AND ACT/PAS ENTITIES


public Wheelbarrow(boolean multiPush, String style, final String name, final String clazz, final String subclass,
			final Map<String, String> attributes)
{
  this(multiPush, style, null, Collections.<String> emptyList());

  setRPClass("Wheelbarrow");
  put("type", "Wheelbarrow");
  possibleSlots = new LinkedList<String>();
  update();

  setEntityClass(clazz);
  setEntitySubclass(subclass);

  put("name", name);

  if (attributes != null) {
    // store all attributes
    for (final Entry<String, String> entry : attributes.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }
  update();

  String slotName = get("slot_name");
		if (slotName == null) {
			slotName = DEFAULT_SLOT_NAME;
		}
		RPSlot slot = new ContainerItemSlot(DEFAULT_SLOT_NAME, slotName);
		addSlot(slot);
		determineSlotCapacity(slot);
}



public Wheelbarrow(boolean multiPush, String style, String shape, List<String> sounds)
{
    direction = Direction.STOP;
    speed = 0.0;
    movementOffset = 0.0;
    stepsTaken = 0;
    this.put(Z_ORDER, 8000);
    this.multi = Boolean.valueOf(multiPush);
    setRPClass("Wheelbarrow");
    put("type", "Wheelbarrow");
    put("class", "Wheelbarrow");
    this.sounds = sounds;
    // Count as collision for the client and pathfinder
    setResistance(100);
    setDescription("You see a wheelbarrow. Are you strong enough to push it awayyyyyyyyy?");
    if (style != null) {
      put("name", style);
    } else {
      put("name", "Wheelbarrow");
    }
    if (shape != null) {
      put("shape", shape);
    }
  }

@Override
public boolean onUsed(RPEntity user) {
	// TODO Auto-generated method stub
	return false;
}






}//classss
*/
