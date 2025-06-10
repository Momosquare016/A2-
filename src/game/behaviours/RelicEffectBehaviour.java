package game.behaviours;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.CursedRelic;
import game.items.RelicManager;

/**
 * Behaviour that passively applies the effects of the currently active cursed relic.
 * This behaviour should be attached to the Player and triggers each turn.
 * Created by:
 * @author yathis
 */
public class RelicEffectBehaviour implements Behaviour {

    /**
     * Applies the curse effect of the currently active relic (if any) to the actor.
     *
     * @param actor the Actor this behaviour is attached to (e.g. the Player)
     * @param map   the GameMap the actor is currently on
     * @return null since this behaviour does not result in a direct Action
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        CursedRelic activeRelic = RelicManager.getInstance().getActiveRelic();
        if (activeRelic != null) {
            activeRelic.applyCurse(actor);  // Apply passive curse effect like HP drain
        }
        return null;  // Passive effect only, no action to return
    }
}




