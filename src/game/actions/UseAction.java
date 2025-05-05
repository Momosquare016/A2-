package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import game.actors.Player;
import game.items.Talisman;

/**
 * Abstract base class for actions that use the Talisman
 */
public abstract class UseAction extends Action {
    protected Talisman talisman;

    /**
     * Constructor
     *
     * @param talisman the talisman being used
     */
    public UseAction(Talisman talisman) {
        this.talisman = talisman;
    }

    /**
     * Check if actor has enough stamina to use the talisman
     *
     * @param actor the actor using the talisman
     * @param staminaCost the stamina cost of the action
     * @return true if the actor has enough stamina, false otherwise
     */
    protected boolean checkStamina(Actor actor, int staminaCost) {
        if (actor instanceof Player) {
            Player player = (Player) actor;
            return player.useStamina(staminaCost);
        }
        return true;
    }
}