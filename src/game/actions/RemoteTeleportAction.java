package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;


public class RemoteTeleportAction extends Action {
    private String destinationName;
    private Location destination;
    private char portalId;
    private Location portalLocation;

    public RemoteTeleportAction(String destinationName, Location destination, char portalId, Location portalLocation) {
        this.destinationName = destinationName;
        this.destination = destination;
        this.portalId = portalId;
        this.portalLocation = portalLocation;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        destination.map().addActor(actor, destination);

        return actor + " use the teleport " + portalId + " to " + destinationName;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " use the teleport " + portalId + " to " + destinationName;
    }
}
