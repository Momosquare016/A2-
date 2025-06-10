package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;

import java.util.HashMap;
import java.util.Map;

/**
 * A magical portal that allows an {@link Actor} to teleport to one or more predefined destinations.
 * <p>
 * Each portal can have multiple named destinations. When an actor interacts with the portal,
 * teleport actions to all registered destinations become available.
 * </p>
 *
 * Created by:
 * @author Ling Jin Lei
 */
public class TeleportationPortal extends Ground {
    /** A map of destination names to their corresponding locations. */
    private final Map<String, Location> destinations = new HashMap<>();

    /**
     * Constructs a new TeleportationPortal with a default display character and name.
     */
    public TeleportationPortal() {
        super('A', "Portal");
    }

    /**
     * Registers a new destination that this portal can teleport to.
     *
     * @param name        the display name of the destination (e.g., "Stormveil Castle")
     * @param destination the {@link Location} to teleport to
     */
    public void addDestination(String name, Location destination) {
        destinations.put(name, destination);
    }

    /**
     * Returns all available teleportation destinations from this portal.
     *
     * @return a map of destination names to {@link Location} objects
     */
    public Map<String, Location> getDestinations() {
        return destinations;
    }

    /**
     * Returns a list of teleportation actions available to the actor when on this portal.
     *
     * @param actor     the actor at the portal
     * @param location  the location of the portal
     * @param direction the direction from the actor to the portal
     * @return an {@link ActionList} containing all teleport options
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        for (String destName : destinations.keySet()) {
            actions.add(new TeleportAction(destName, destinations.get(destName)));
        }

        return actions;
    }
}