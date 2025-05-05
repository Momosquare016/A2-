package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.ground.Soil;
import game.items.Talisman;

/**
 * Action to cure blighted ground with the Talisman
 */
public class GroundCureAction extends UseAction {
    private Location location;

    /**
     * Constructor
     *
     * @param location the location to cure
     * @param talisman the talisman being used
     */
    public GroundCureAction(Location location, Talisman talisman) {
        super(talisman);
        this.location = location;
    }

    /**
     * Execute the action
     *
     * @param actor the actor performing the action
     * @param map the game map
     * @return description of what happened
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Using talisman requires stamina
        if (!checkStamina(actor, 50)) {
            return actor + " doesn't have enough stamina to use the Talisman.";
        }

        // Convert blight to soil
        location.setGround(new Soil());

        return actor + " used the Talisman to cure the blighted ground.";
    }

    /**
     * Description for the UI menu
     *
     * @param actor the actor performing the action
     * @return description for the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses Talisman to cure blighted ground (50 stamina)";
    }
}