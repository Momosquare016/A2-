package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.GroundCureAction;
import game.ground.Blight;



/**
 * A class representing a Talisman that an actor can pick up and drop
 */

public class Talisman extends Item {
    /**
     * Constructor.
     */
    public Talisman() {
        super("Talisman", 'o', true);
    }

    /**
     * Actions available when talisman is carried
     *
     * @param owner the actor carrying the talisman
     * @param map the map where the actor is
     * @return a collection of actions
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        // Check for blighted ground to cure
        Location location = map.locationOf(owner);
        if (location.getGround() instanceof Blight) {
            actions.add(new GroundCureAction(location, this));
        }

        return actions;
    }
}