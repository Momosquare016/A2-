package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.Status;

/**
 * A behaviour that allows an actor to follow a nearby target that has the {@code FOLLOWABLE} status.
 * <p>
 * The actor will attempt to move toward the target using Manhattan distance,
 * and will continue following the same target unless it disappears from the map.
 * </p>
 *
 * Created by:
 * @author
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
        Location here = map.locationOf(actor);
        if (target == null) {
            findFollowableTarget(here);
            return null;
        }

        if (!map.contains(target) || !map.contains(actor)) {
            return null;
        }

        Location there = map.locationOf(target);
        int currentDistance = distance(here, there);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, there);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }

        return null;
    }

    /**
     * Searches for a followable target in adjacent locations and assigns it to the {@code target} field.
     *
     * @param here the current location of the actor
     */
    private void findFollowableTarget(Location here) {
        for (Exit exit : here.getExits()) {
            Actor candidate = exit.getDestination().getActor();
            if (candidate != null && candidate.hasCapability(Status.FOLLOWABLE)) {
                target = candidate;
            }
        }
    }

    /**
     * Computes the Manhattan distance between two locations.
     * This is the number of steps required to move from one location to the other
     * using only the four cardinal directions.
     *
     * @param a the first location
     * @param b the second location
     * @return the Manhattan distance between {@code a} and {@code b}
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
