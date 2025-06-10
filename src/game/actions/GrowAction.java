package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Growable;

/**
 * An action that triggers the growth behavior of an entity that implements {@link Growable}.
 * <p>
 * This action is typically used by entities (e.g., bosses or plants) that have the ability to grow
 * during gameplay. The actual growth logic is handled by the {@link Growable#performGrowth(GameMap)} method.
 * </p>
 *
 * Created by:
 * @author Ali Raza
 */
public class GrowAction extends Action {

    /** The growable entity that will perform a growth action. */
    private final Growable growable;

    /**
     * Constructor for creating a GrowAction.
     *
     * @param growable the {@link Growable} entity associated with this action
     */
    public GrowAction(Growable growable) {
        this.growable = growable;
    }

    /**
     * Executes the growth logic for the associated growable entity.
     * <p>
     * This method assumes that the growable entity has already been validated to have the appropriate capability.
     * </p>
     *
     * @param actor the {@link Actor} performing the action (not used directly)
     * @param map   the game map on which the growth occurs
     * @return the result of the growth action as a string
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return growable.performGrowth(map);
    }

    /**
     * Returns a short description of this action to appear in the in-game menu.
     *
     * @param actor the {@link Actor} performing the action
     * @return a string for the action menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " grows";
    }
}