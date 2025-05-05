package game.ground;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.Plants.Plant;
import game.actors.Player;
import game.Enum.SeedType;

import java.util.List;

/**
 * A class representing the Inheritree plant.
 */
public class Inheritree extends Plant {

    /**
     * Constructor.
     */
    public Inheritree() {
        super('t', "Inheritree", SeedType.INHERITREE);
    }

    /**
     * Apply the Inheritree's planting effect: convert surrounding blight to soil.
     *
     * @param location The location where the plant is planted
     */
    @Override
    public void plantingEffect(Location location) {
        // Convert surrounding blight to soil
        List<Location> surroundings = getSurroundingLocations(location);

        for (Location surroundingLocation : surroundings) {
            if (surroundingLocation.getGround() instanceof Blight) {
                surroundingLocation.setGround(new Soil());
            }
        }
    }

    /**
     * Apply the Inheritree's tick effect: heal nearby actors.
     *
     * @param location The location of the plant
     */
    @Override
    public void tickEffect(Location location) {
        // Get surrounding locations
        List<Location> surroundings = getSurroundingLocations(location);

        // Heal and restore stamina of surrounding actors
        for (Location surroundingLocation : surroundings) {
            if (surroundingLocation.containsAnActor()) {
                Actor actor = surroundingLocation.getActor();

                // Heal the actor
                actor.heal(5);

                // Restore stamina if it's a player
                if (actor instanceof Player) {
                    ((Player) actor).restoreStamina(5);
                }
            }
        }
    }
}