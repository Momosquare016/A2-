package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Curable;

/**
 * Class representing an action to cure a {@link Curable} target using a curing item such as a Talisman.
 * This action will consume stamina if the target is beneath the actor (e.g. a Blight).
 * Created by:
 * @author Chan Chee Wei
 */
public class CureAction extends Action implements StaminaCosting {
    /**
     * The Item used to cure
     */
    private final Item item;

    /**
     * The target to be cured
     */
    private final Curable target;

    /**
     * The direction of the target relative to the actor
     */
    private String direction;

    /**
     * Constructs a CureAction with a direction.
     *
     * @param item the curing item used
     * @param target the target to be cured
     * @param direction the direction of the target relative to the actor
     */
    public CureAction(Item item, Curable target, String direction) {
        this.item = item;
        this.target = target;
        this.direction = direction;
    }

    /**
     * Constructs a CureAction with no direction (e.g., target is under actor).
     *
     * @param item the curing item used
     * @param target the target to be cured
     */
    public CureAction(Item item, Curable target) {
        this.item = item;
        this.target = target;
    }

    /**
     * Returns the stamina cost of curing when the target is under the actor.
     *
     * @return 50 units of stamina
     */
    @Override
    public int getStaminaCost() {
        return 50;
    }

    /**
     * Executes the curing action. If the target is beneath the actor, stamina is reduced.
     *
     * @param actor the actor performing the cure
     * @param map the game map
     * @return a message describing the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (direction == null) {
            if (actor.hasAttribute(BaseActorAttributes.STAMINA)) {
                if (actor.getAttribute(BaseActorAttributes.STAMINA) < getStaminaCost()) {
                    return actor + " doesn't have enough stamina";
                }
                actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, getStaminaCost());
            }
        }

        String result = target.cure(actor, map);
        return menuDescription(actor) + "\n" + result;
    }

    /**
     * Describes the action in the game menu.
     *
     * @param actor the actor performing the action
     * @return a string description of the cure action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses " + item + " to cure the " + target + (direction == null? " under them" : " at " + direction);
    }
}