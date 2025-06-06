package game.actors.npcs;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import game.actions.CureAction;
import game.actors.NPC;
import game.behaviours.RandomBehaviourSelector;
import game.behaviours.ReproduceBehaviour;
import game.behaviours.SequentialBehaviourSelector;
import game.effects.RotEffect;
import game.behaviours.WanderBehaviour;
import game.enums.Ability;
import game.enums.Status;
import game.grounds.Inheritree;
import game.interfaces.BehaviourSelectionStrategy;
import game.interfaces.Curable;
import game.interfaces.Reproducible;
import game.items.OmenEgg;

/**
 * A concrete NPC representing the Omen Sheep.
 * The Omen Sheep can be attacked and will wander randomly using {@link WanderBehaviour}.
 * It disappears after 15 turns using a {@link RotEffect} countdown timer.
 * If cured using a valid curing item, it transforms all adjacent tiles into {@link Inheritree}.
 * The Omen Sheep produces eggs every 7 turns which can hatch into new sheep.
 * Created by:
 * @author Chan Chee Wei
 */
public class OmenSheep extends NPC implements Curable, Reproducible {
    /**
     * The countdown of turns before Omen Sheep disappears or becomes unconscious.
     */
    private final RotEffect rotEffect;

    /**
     * Constructor to create an Omen Sheep NPC.
     * Constructs an Omen Sheep with 75 HP and a display character of 'm'.
     * It is given the {@code ATTACKABLE} and {@code CURABLE} status capabilities,
     * It also wanders randomly each turn via a {@link WanderBehaviour}.
     */
    public OmenSheep(BehaviourSelectionStrategy strategy) {
        super("Omen Sheep", 'm', 75,strategy);
        this.rotEffect = new RotEffect(15);
        this.addStatusEffect(rotEffect);

        this.addCapability(Status.ATTACKABLE);
        this.addCapability(Status.CURABLE);

        this.addBehaviour(999, new WanderBehaviour());
        this.addBehaviour(2, new ReproduceBehaviour(this, 7));
    }

    /**
     * Determines the Omen Sheep's action each turn.
     * If the rot countdown reaches zero, it becomes unconscious and performs no action.
     * Otherwise, it executes the first valid behavior in priority order.
     * Additionally, it lays eggs every 7 turns.
     *
     * @param actions a list of allowable actions
     * @param lastAction the action performed last turn
     * @param map the current game map
     * @param display the display for console output
     * @return the action to perform this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        display.println(this + String.format(" has %s", rotEffect.getTurnLeft()));
        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Returns a list of actions that can be performed on this Omen Sheep by another actor.
     * Includes attack actions if conditions are met (handled by the superclass),
     * and adds a {@link CureAction} if the interacting actor has a curing item.
     *
     * @param otherActor the actor performing the interaction
     * @param direction the direction of this NPC relative to the actor
     * @param map the map the actor and NPC are on
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        for (Item item : otherActor.getItemInventory()) {
            if (item.hasCapability(Ability.CURING_ITEM)) {
                actions.add(new CureAction(item, this, direction));
            }
        }

        return actions;
    }

    /**
     * Applies the cure effect on the Omen Sheep.
     * Upon curing, it converts all surrounding 8 tiles into {@link Inheritree}.
     *
     * @param actor the actor performing the cure
     * @param map the game map where the interaction takes place
     * @return a string message indicating the outcome
     */
    @Override
    public String cure(Actor actor, GameMap map) {
        for (Exit exit : map.locationOf(this).getExits()) {
            Location location = exit.getDestination();
            location.setGround(new Inheritree());
        }

        return "The surroundings of " + this + " has transformed into Inheritrees";
    }

    @Override
    public boolean canReproduce(GameMap map) {
        return true;
    }

    @Override
    public String reproduce(GameMap map) {
        OmenEgg egg = map.toString().contains("valley")
                ? OmenEgg.createValleyEgg()
                : OmenEgg.createLimveldEgg();

        map.locationOf(this).addItem(egg);
        return "an Egg";
    }
}