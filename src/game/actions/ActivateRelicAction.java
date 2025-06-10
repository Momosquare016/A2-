package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.CursedRelic;
import game.items.RelicManager;

/**
 * An action that allows an {@link Actor} to activate a {@link CursedRelic}.
 * <p>
 * This action checks if a relic is already active. If not, it activates the new relic using the {@link RelicManager}.
 * </p>
 *
 * Created by:
 * @author Yathis
 */
public class ActivateRelicAction extends Action {
    /** The cursed relic to be activated. */
    private final CursedRelic relic;

    /**
     * Constructs a new ActivateRelicAction with the specified cursed relic.
     *
     * @param relic the {@link CursedRelic} to activate
     */
    public ActivateRelicAction(CursedRelic relic) {
        this.relic = relic;
    }

    /**
     * Executes the activation of the relic by the given actor.
     * <p>
     * If a relic is already active, the action fails and returns a message.
     * Otherwise, the relic is activated and its effects are applied.
     * </p>
     *
     * @param actor the {@link Actor} performing the action
     * @param map   the current game map
     * @return a string describing the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        RelicManager mgr = RelicManager.getInstance();
        if (mgr.getActiveRelic() != null) {
            return actor + " already has " + mgr.getActiveRelic().getName();
        }
        mgr.activateRelic(relic, actor);
        return actor + " activates " + relic.getName();
    }

    /**
     * Returns a description of the action to appear in the in-game menu.
     *
     * @param actor the {@link Actor} performing the action
     * @return a string description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " activates " + relic.getName();
    }
}












