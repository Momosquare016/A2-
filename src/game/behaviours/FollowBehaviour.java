package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.LocationUtils;
import game.enums.Status;

/**
 * A behaviour that allows an actor to follow a nearby target that has the {@code FOLLOWABLE} status.
 * <p>
 * The actor will attempt to move toward the target using Manhattan distance,
 * and will continue following the same target unless it disappears from the map.
 * </p>
 *
 * Created by:
 * @author Chan Chee Wei
 */
public class FollowBehaviour implements Behaviour {

    /** The target actor to follow. */
    private Actor target;

    /**
     * Returns an action that will move the actor closer to its followable target.
     * If no target has been found yet, this method will look for one in adjacent tiles.
     * The actor only follows one target at a time and chooses the first valid one found.
     *
     * @param actor the actor with this behaviour
     * @param map the game map
     * @return a {@link MoveActorAction} to move closer to the target, or {@code null} if no movement is needed
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(actor)) {
            return null;
        }

        Location here = map.locationOf(actor);
        LocationUtils utils = new LocationUtils(here);

        if (target == null) {
            target = utils.getAdjacentActorWith(Status.FOLLOWABLE);
            return null;
        }

        if (!map.contains(target)) {
            return null;
        }

        Location there = map.locationOf(target);
        Exit bestExit = utils.getBestExitTowards(there, actor);

        if (bestExit != null) {
            return new MoveActorAction(bestExit.getDestination(), bestExit.getName());
        }

        return null;
    }
}
