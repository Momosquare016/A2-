package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.enums.Status;

/**
 * Behavior that makes the boss attack nearby hostile actors (players).
 * Uses the existing AttackAction from the codebase.
 */
public class BossAttackBehaviour implements Behaviour {

    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);

        // Check all adjacent locations for hostile actors
        for (Exit exit : location.getExits()) {
            Actor target = exit.getDestination().getActor();
            if (target != null && target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                return new AttackAction(target, exit.getName());
            }
        }
        return null;
    }
}