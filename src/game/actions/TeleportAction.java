package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class TeleportAction extends Action {
    private final String destinationName;
    private final Location destination;
    private final char portalId;

    public TeleportAction(String destinationName, Location destination, char portalId) {
        this.destinationName = destinationName;
        this.destination = destination;
        this.portalId = portalId;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);

        destination.map().addActor(actor, destination);

        return actor + " by using the portal " + portalId + " to " + destinationName;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses the portal " + portalId + " to " + destinationName;
    }
}

