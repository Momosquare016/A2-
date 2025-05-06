package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An empty or no-operation action that does nothing when executed.
 * This is useful for returning from methods without triggering any visible output or game effects.
 * For example, it can suppress unwanted messages or prevent action repetition.
 * Created by:
 * @author Chan Chee Wei
 */
public class DummyAction extends Action {
    /**
     * Executes the action. Does nothing and returns an empty string.
     *
     * @param actor The actor performing the action
     * @param map The game map
     * @return An empty string (no output)
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return "";
    }

    /**
     * Provides an empty string for menu display.
     *
     * @param actor The actor performing the action
     * @return An empty string (no menu description)
     */
    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
