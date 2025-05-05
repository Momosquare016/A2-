package game.Plants;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Enum.SeedType;
import game.Enum.Status;
import game.actions.PlantAction;

/**
 * Class representing a seed that can be planted.
 */
public class Seed extends Item {
    private SeedType type;

    /**
     * Constructor.
     *
     * @param type The type of seed
     */
    public Seed(SeedType type) {
        super("Seed of " + type.toString(), '*', true);
        this.type = type;
    }

    /**
     * Get the type of seed.
     *
     * @return The seed type
     */
    public SeedType getType() {
        return type;
    }

    /**
     * Create and return an action to pick up this Item.
     * This method should return the parent class's implementation.
     *
     * @param actor the Actor acting
     * @return a PickUpAction if this Item is portable, null otherwise.
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return super.getPickUpAction(actor);
    }

    /**
     * Add planting action for the player when item is in inventory
     *
     * @param actor The actor carrying this item
     * @param map The game map
     * @return A collection of actions
     */
    @Override
    public ActionList allowableActions(Actor actor, GameMap map) {
        ActionList actions = super.allowableActions(actor, map);

        if (actor.hasCapability(Status.CAN_PLANT)) {
            actions.add(new PlantAction(this));
        }

        return actions;
    }
}