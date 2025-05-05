package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.animals.Animal;
import game.Enum.EntityType;
import game.ground.RotEffect;
import game.ground.Inheritree;
import game.items.Talisman;
import java.util.List;
import java.util.ArrayList;

/**
 * Action to cure an animal with the Talisman
 */
public class CureAction extends UseAction {
    private RotEffect target;

    /**
     * Constructor
     *
     * @param target the animal to cure
     * @param talisman the talisman being used
     */
    public CureAction(RotEffect target, Talisman talisman) {
        super(talisman);
        this.target = target;
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

        // Different effects based on target type
        if (target instanceof Animal) {
            Animal animal = (Animal) target;

            if (animal.getType() == EntityType.SPIRIT_GOAT) {
                // Reset countdown for spirit goat
                target.resetCountdown();
                return actor + " used the Talisman to cure the " + animal + ". Its countdown was reset to " +
                        target.getMaxCountdown() + " turns.";
            }
            else if (animal.getType() == EntityType.OMEN_SHEEP) {
                // Create Inheritrees around sheep
                Location sheepLocation = map.locationOf((Actor)animal);
                for (Location surroundingLocation : getSurroundingLocations(sheepLocation, map)) {
                    if (!surroundingLocation.containsAnActor()) {
                        surroundingLocation.setGround(new Inheritree());
                    }
                }
                return actor + " used the Talisman on the " + animal + ". Inheritrees grew around it!";
            }
        }

        return actor + " used the Talisman, but nothing happened.";
    }

    /**
     * Get surrounding locations (8 adjacent squares)
     *
     * @param location The center location
     * @param map The game map
     * @return List of adjacent locations
     */
    private List<Location> getSurroundingLocations(Location location, GameMap map) {
        List<Location> surroundings = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue; // Skip center

                int newX = location.x() + x;
                int newY = location.y() + y;

                if (map.getXRange().contains(newX) &&
                        map.getYRange().contains(newY)) {
                    surroundings.add(map.at(newX, newY));
                }
            }
        }

        return surroundings;
    }

    /**
     * Description for the UI menu
     *
     * @param actor the actor performing the action
     * @return description for the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses Talisman on " + ((Actor)target) + " (50 stamina)";
    }
}