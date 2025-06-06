package game.actors.npcs;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

import game.actions.ConsumeAction;
import game.actors.NPC;
import game.behaviours.*;
import game.enums.Status;
import game.interfaces.BehaviourSelectionStrategy;
import game.interfaces.Consumable;
import game.interfaces.Reproducible;
import game.items.GoldenEgg;


/**
 * A concrete NPC representing the Golden Beetle.
 * The Golden Beetle can be attacked and will wander randomly using {@link WanderBehaviour}.
 * It produces a Golden Egg every five turns.
 * It can follow the "Farmer" and be consumed by the "Farmer" to gain health and runes.
 * Created by:
 * @author LIJINLEI
 */
public class GoldenBeetle extends NPC implements Consumable, Reproducible {

    /**
     * Constructor to create a Golden Beetle NPC.
     * Constructs a Golden Beetle with 25 HP and a display character of 'b'.
     * It is given the {@code ATTACKABLE} status capability,
     * and it wanders randomly each turn via a {@link WanderBehaviour}.
     */
    public GoldenBeetle(BehaviourSelectionStrategy strategy) {
        super("Golden Beetle", 'b', 25,strategy);

        this.addCapability(Status.ATTACKABLE);

        this.addBehaviour(999, new WanderBehaviour());
        this.addBehaviour(50, new FollowBehaviour());
        this.addBehaviour(2, new ReproduceBehaviour(this, 5));


    }

    /**
     * Returns a list of actions that can be performed on this Golden Beetle by another actor.
     * Includes attack actions if conditions are met (handled by the superclass),
     * and adds an {@link ConsumeAction} if the interacting actor is the "Farmer".
     *
     * @param otherActor the actor performing the interaction
     * @param direction the direction of this NPC relative to the actor
     * @param map the map the actor and NPC are on
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        actions.add(new ConsumeAction(this));

        return actions;
    }

    /**
     * Determines whether this Golden Beetle can reproduce on the current map.
     * Always returns true.
     *
     * @param map The game map where reproduction may occur.
     * @return True, indicating this NPC can reproduce.
     */
    @Override
    public boolean canReproduce(GameMap map) {
        return true;
    }

    /**
     * Triggers the reproduction effect of the Golden Beetle.
     * A {@link GoldenEgg} is added to the NPC's current location.
     *
     * @param map The game map where the effect takes place.
     * @return A description of the item produced.
     */
    @Override
    public String reproduce(GameMap map) {
        GoldenEgg egg = map.toString().contains("valley")
                ? GoldenEgg.createValleyEgg()
                : GoldenEgg.createLimveldEgg();

        map.locationOf(this).addItem(egg);
        return "a Golden Egg";
    }

    /**
     * Defines the effect when the Golden Beetle is consumed by an actor.
     * <p>
     * The consuming actor:
     * - Heals 15 HP
     * - Gains 1000 runes
     * The beetle becomes unconscious as a result.
     *
     * @param consumer The actor consuming the Golden Beetle.
     * @param map      The current game map.
     * @return A string describing the outcome of consumption.
     */
    @Override
    public String consumeEffect(Actor consumer, GameMap map) {
        consumer.heal(15);
        consumer.addBalance(1000);
        this.unconscious(map);
        return consumer + " healed for 15 points and gained 1000 runes";
    }

}