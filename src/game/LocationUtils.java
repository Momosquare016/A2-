package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class for performing operations related to a specific {@link Location} on the map.
 * Provides methods for checking nearby capabilities, determining valid spawn positions,
 * and spawning actors in valid adjacent locations.
 * Created by:
 * @author Chan Chee Wei
 */
public class LocationUtils {
    private final Location location;

    /**
     * Constructs a utility wrapper for a specific location.
     *
     * @param location the location to be used in utility operations
     */
    public LocationUtils(Location location) {
        this.location = location;
    }

    /**
     * Checks all surrounding tiles (including the current tile) for the presence of a given {@link Status} capability.
     * This includes actors, items, and ground at each surrounding location.
     *
     * @param capability the status capability to search for
     * @return {@code true} if any adjacent tile contains the given capability on an actor, item, or ground; {@code false} otherwise
     */
    public boolean hasSurroundingWith(Enum<?> capability) {
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getActor() != null && destination.getActor().hasCapability(capability)) {
                return true;
            }

            for (Item item : destination.getItems()) {
                if (item.hasCapability(capability)) {
                    return true;
                }
            }

            if (destination.getGround().hasCapability(capability)) {
                return true;
            }
        }
        return false;
    }

    public Actor getAdjacentActorWith(Enum<?> capability) {
        for (Actor candidate : getAdjacentActors()) {
            if (candidate.hasCapability(capability)) {
                return candidate;
            }
        }
        return null;
    }

    public List<Actor> getAdjacentActors() {
        List<Actor> actors = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            Location adjacent = exit.getDestination();
            Actor actor = adjacent.getActor();
            if (actor != null) {
                actors.add(actor);
            }
        }

        return actors;
    }

    public boolean isAdjacentTo(Location other) {
        for (Exit exit : location.getExits()) {
            if (exit.getDestination() == other) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds all valid locations where a given actor can be spawned.
     * A location is considered valid if it is unoccupied and can be entered by the actor.
     * The current location is also included if it's available.
     *
     * @param actor the actor to evaluate for spawnable locations
     * @return a list of valid spawn locations including the current and adjacent tiles
     */
    public List<Location> findValidSpawnLocations(Actor actor) {
        List<Location> validLocations = new ArrayList<>();

        if (!location.containsAnActor()) {
            validLocations.add(location);
        }

        for (Exit exit : location.getExits()) {
            Location location = exit.getDestination();
            if (!location.containsAnActor() && location.canActorEnter(actor)) {
                validLocations.add(location);
            }
        }

        return validLocations;
    }

    /**
     * Spawns the given actor in a randomly selected valid location from the surrounding tiles (or the current location).
     * If no valid spawn locations are found, the actor is not spawned.
     *
     * @param actor the actor to spawn in the vicinity of the base location
     */
    public void spawnActor(Actor actor) {
        List<Location> validLocations = findValidSpawnLocations(actor);
        if (!validLocations.isEmpty()) {
            validLocations.get(new Random().nextInt(validLocations.size())).addActor(actor);
        }
    }

    public String getDirectionToActor(Actor actor) {
        for (Exit exit : location.getExits()) {
            if (exit.getDestination().getActor() == actor) {
                return exit.getName();
            }
        }
        return null;
    }

    public Exit getBestExitTowards(Location destination, Actor actor) {
        int shortestDistance = distance(location, destination);
        Exit bestExit = null;

        for (Exit exit : location.getExits()) {
            Location next = exit.getDestination();
            if (next.canActorEnter(actor)) {
                int newDistance = distance(next, destination);
                if (newDistance < shortestDistance) {
                    shortestDistance = newDistance;
                    bestExit = exit;
                }
            }
        }
        return bestExit;
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
    public int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}