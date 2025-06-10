package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An action that teleports an {@link Actor} to a specified {@link Location}.
 * <p>
 * This action is typically used for transitioning between maps, fast travel, or scripted events.
 * </p>
 *
 * Created by:
 * @author leejl
 */
public class TeleportAction extends Action {
    private final String destinationName;
    private final Location destination;

    /**
     * Constructs a TeleportAction to move an actor to a new location.
     *
     * @param destinationName the name of the target location (e.g., "Stormveil Castle")
     * @param destination     the actual {@link Location} the actor will be moved to
     */
    public TeleportAction(String destinationName, Location destination) {
        this.destinationName = destinationName;
        this.destination = destination;
    }

    /**
     * Executes the teleport action: removes the actor from the current map
     * and adds them to the destination map and location.
     *
     * @param actor the {@link Actor} performing the teleport
     * @param map   the current {@link GameMap} the actor is on
     * @return a message indicating the result of the teleportation
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);

        destination.map().addActor(actor, destination);

        return menuDescription(actor);
    }

    /**
     * Returns a description of this action to appear in the in-game menu.
     *
     * @param actor the {@link Actor} performing the action
     * @return a string describing the teleport action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " teleports to " + destinationName;
    }
}

