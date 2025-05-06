package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Seed;

/**
 * An action that allows an actor to plant a {@link Seed} on the ground beneath them.
 * Consumes stamina based on the crop associated with the seed.
 * After planting, the seed is removed from the inventory and the crop's effect is applied.
 * This action implements {@link StaminaCosting} to allow stamina-based filtering or handling.
 * Created by:
 * @author Chan Chee Wei
 */
public class PlantAction extends Action implements StaminaCosting {
    /**
     * The seed to be planted
     */
    private final Seed seed;

    /**
     * Constructor for PlantAction.
     *
     * @param seed The seed to be planted
     */
    public PlantAction(Seed seed) {
        this.seed = seed;
    }

    /**
     * Gets the stamina cost of planting, defined by the crop.
     *
     * @return the stamina cost of the associated crop
     */
    @Override
    public int getStaminaCost() {
        return seed.getCrop().getStaminaCost();
    }

    /**
     * Executes the planting action.
     * Decreases actor's stamina, removes the seed from inventory, replaces the current ground with the crop, and calls the crop's on-plant behavior.
     *
     * @param actor the actor performing the planting
     * @param map the game map
     * @return a message describing the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor.hasAttribute(BaseActorAttributes.STAMINA)) {
            if (actor.getAttribute(BaseActorAttributes.STAMINA) < getStaminaCost()) {
                return actor + " doesn't have enough stamina";
            }
            actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, getStaminaCost());
        }

        actor.removeItemFromInventory(seed);

        Location location = map.locationOf(actor);
        location.setGround(seed.getCrop());

        seed.getCrop().onPlant(actor, location);

        return menuDescription(actor);
    }

    /**
     * Describes the action in the menu.
     *
     * @param actor the actor performing the action
     * @return a string description of the planting action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " plants " + seed + " here";
    }
}