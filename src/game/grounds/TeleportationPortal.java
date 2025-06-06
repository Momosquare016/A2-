package game.grounds;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.RemoteTeleportAction;
import game.actions.TeleportAction;

import java.util.HashMap;
import java.util.Map;

public class TeleportationPortal extends Ground {
    private final char portalId;
    private final Map<String, Location> destinations = new HashMap<>();
    private static final int RANGE = 1;



    public TeleportationPortal() {
        super('A',"portalID");
        this.portalId = 'A';
    }
    public TeleportationPortal(char portalId) {
        super('A',"portalId");
        this.portalId = portalId;
    }

    public void addDestination(String destinationName, Location destination) {
        destinations.put(destinationName, destination);
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        boolean isNearby = isActorInRange(actor, location);

        for (Map.Entry<String, Location> entry : destinations.entrySet()) {
            if (isNearby) {
                actions.add(new RemoteTeleportAction(entry.getKey(), entry.getValue(), portalId, location));
            } else if (location.getGround() == this) {

                actions.add(new TeleportAction(entry.getKey(), entry.getValue(), portalId));
            }
        }

        return actions;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }
    private boolean isActorInRange(Actor actor, Location currentLocation) {
        Location portalLocation = null;
        for (int x : currentLocation.map().getXRange()) {
            for (int y : currentLocation.map().getYRange()) {
                if (currentLocation.map().at(x, y).getGround() == this) {
                    portalLocation = currentLocation.map().at(x, y);
                    break;
                }
            }
            if (portalLocation != null) break;
        }

        if (portalLocation == null) return false;

        int dx = Math.abs(currentLocation.x() - portalLocation.x());
        int dy = Math.abs(currentLocation.y() - portalLocation.y());

        return dx <= RANGE && dy <= RANGE && !(dx == 0 && dy == 0);
    }

}

