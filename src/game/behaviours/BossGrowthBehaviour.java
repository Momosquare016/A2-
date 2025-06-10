package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.GrowAction;
import game.enums.Status;
import game.interfaces.Growable;

/**
 * Behavior that makes growable actors grow when no hostile actors are nearby.
 * Uses capability checking instead of instanceof to determine valid targets.
 */
public class BossGrowthBehaviour implements Behaviour {
    private final Growable growable;

    public BossGrowthBehaviour(Growable growable) {
        this.growable = growable;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        // Check if actor can grow and no player is nearby
        if (actor.hasCapability(Status.CAN_GROW) && !isPlayerNearby(actor, map)) {
            return new GrowAction(growable);
        }
        return null;
    }

    /**
     * Checks if any hostile actor (player) is in adjacent tiles.
     * @param actor the actor to check around
     * @param map the current game map
     * @return true if a hostile actor is nearby
     */
    private boolean isPlayerNearby(Actor actor, GameMap map) {
        Location actorLocation = map.locationOf(actor);
        for (Exit exit : actorLocation.getExits()) {
            Actor nearbyActor = exit.getDestination().getActor();
            if (nearbyActor != null && nearbyActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                return true;
            }
        }
        return false;
    }
}
