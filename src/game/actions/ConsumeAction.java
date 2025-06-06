package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Consumable;

/**
 * An action that allows an {@link Actor} to consume a {@link Consumable} object.
 * <p>
 * This action can be used to consume items such as food or other consumable entities
 * that implement the {@code Consumable} interface. When executed, it applies the
 * consumable's effect to the actor and provides a textual result.
 * </p>
 * * Created by:
 *  * @author LIJINLEI
 */
public class ConsumeAction extends Action {
    /** The target object to be consumed. */
    private final Consumable target;

    /**
     * Constructor for {@code ConsumeAction}.
     *
     * @param target the {@link Consumable} object to be consumed by the actor
     */
    public ConsumeAction(Consumable target) {
        this.target = target;
    }

    /**
     * Executes the consume action, applying the consumable's effect on the actor.
     *
     * @param actor the {@link Actor} performing the action
     * @param map   the {@link GameMap} the actor is on
     * @return a string describing the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = target.consumeEffect(actor, map);
        return actor + " has consumed " + target + ";\n" + result;
    }

    /**
     * Returns a descriptive string for displaying in the menu.
     *
     * @param actor the {@link Actor} performing the action
     * @return a string describing the action for display in the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + target;
    }
}
