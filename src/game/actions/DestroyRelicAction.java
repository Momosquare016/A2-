package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.RelicManager;

/**
 * An action that allows the actor to destroy the currently active relic.
 * <p>
 * This removes the relic's buff and curse effects from the actor and deactivates it in the {@link RelicManager}.
 * </p>
 *
 * Created by:
 * @author Yathis
 */
public class DestroyRelicAction extends Action {

    /**
     * Executes the action of destroying the currently active relic for the given actor.
     * This removes the relic's effects and clears it from the {@link RelicManager}.
     *
     * @param actor the {@link Actor} performing the action
     * @param map   the game map the actor is on
     * @return a description of the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        RelicManager.getInstance().destroyRelic(actor);
        return actor + " destroys the active relic.";
    }

    /**
     * Returns a string describing the action for the in-game menu.
     *
     * @param actor the {@link Actor} performing the action
     * @return a description of the action for display in the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " destroys the active relic";
    }
}


