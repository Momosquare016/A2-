package game.actors.npcs;

import edu.monash.fit2099.demo.mars.behaviours.FollowBehaviour;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

import game.LocationUtils;
import game.actions.CureAction;
import game.actors.NPC;
import game.behaviours.RandomBehaviourSelector;
import game.behaviours.SequentialBehaviourSelector;
import game.effects.RotEffect;
import game.behaviours.ReproduceBehaviour;
import game.behaviours.WanderBehaviour;
import game.enums.Ability;
import game.enums.Status;
import game.interfaces.BehaviourSelectionStrategy;
import game.interfaces.Curable;
import game.interfaces.Reproducible;

/**
 * A concrete NPC representing the Spirit Goat.
 * The Spirit Goat can be attacked and will wander randomly using {@link WanderBehaviour}.
 * It disappears after 10 turns using a {@link RotEffect} countdown timer.
 * If cured using a valid curing item, the countdown timer resets.
 * Spirit Goats can reproduce when near entities blessed by grace.
 * Created by:
 * @author Chan Chee Wei

 */
public class SpiritGoat extends NPC implements Curable, Reproducible {
    /**
     * The countdown of turns before Spirit Goat disappears or becomes unconscious.
     */
    private final RotEffect rotEffect;

    /**
     * Constructor to create a Spirit Goat NPC.
     * Constructs a Spirit Goat with 50 HP and a display character of 'y'.
     * It is given the {@code ATTACKABLE} and {@code CURABLE} status capabilities,
     * and it wanders randomly each turn via a {@link WanderBehaviour}.
     */
    public SpiritGoat(BehaviourSelectionStrategy strategy) {
        super("Spirit Goat", 'y', 50,strategy);
        this.rotEffect = new RotEffect(10);

        this.addCapability(Status.ATTACKABLE);
        this.addCapability(Status.CURABLE);

        this.addBehaviour(999, new WanderBehaviour());
        this.addBehaviour(2, new ReproduceBehaviour(this, 3));
    }


    /**
     * Determines the Spirit Goat's action each turn.
     * Removes reproduction cooldown with 20% chance.
     * If the rot countdown reaches zero, it becomes unconscious.
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
     * Returns a list of actions that can be performed on this Spirit Goat by another actor.
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
     * Applies the cure effect on the Spirit Goat.
     * Upon curing, it resets the rot countdown to the initial value.
     *
     * @param actor the actor performing the cure
     * @param map the game map where the interaction takes place
     * @return a string message indicating the outcome
     */
    @Override
    public String cure(Actor actor, GameMap map) {
        rotEffect.reset();
        return "The countdown timer of " + this + " has reset";
    }

    @Override
    public boolean canReproduce(GameMap map) {
        return (new LocationUtils(map.locationOf(this)).hasSurroundingWith(Status.BLESSED_BY_GRACE));
    }

    @Override
    public String reproduce(GameMap map) {
        new LocationUtils(map.locationOf(this)).spawnActor(new SpiritGoat(this.selectionStrategy));
        return "a Spirit Goat";
    }

}