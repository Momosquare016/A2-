package game.ground;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Enum.SeedType;
import game.Main.FancyMessage;
import game.Plants.Plant;
import game.actors.Player;

import java.util.List;

/**
 * A class representing the Bloodrose plant.
 */
public class Bloodrose extends Plant {
    /**
     * Constructor.
     */
    public Bloodrose() {
        super('w', "Bloodrose", SeedType.BLOODROSE);
    }

    /**
     * Apply the Bloodrose's planting effect: sap the Farmer's health.
     *
     * @param location The location where the plant is planted
     */
    @Override
    public void plantingEffect(Location location) {
        // Instead of trying to iterate through all actors, we can look for the Player
        // by using publicly available methods
        GameMap map = location.map();

        // Check if the player is on this map
        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Location currentLocation = map.at(x, y);
                if (currentLocation.containsAnActor()) {
                    Actor actor = currentLocation.getActor();
                    if (actor instanceof Player) {
                        actor.hurt(5);
                        return;  // Exit once we've found the player
                    }
                }
            }
        }
    }

    /**
     * Apply the Bloodrose's tick effect: hurt surrounding actors.
     *
     * @param location The location of the plant
     */
    @Override
    public void tickEffect(Location location) {
        // Get surrounding locations
        List<Location> surroundings = getSurroundingLocations(location);

        // Apply damage to actors in surrounding locations
        for (Location surroundingLocation : surroundings) {
            if (surroundingLocation.containsAnActor()) {
                Actor actor = surroundingLocation.getActor();
                actor.hurt(10);

                // Check if player died
                if (!actor.isConscious() && actor instanceof Player) {
                    Display display = new Display();
                    display.println(FancyMessage.YOU_DIED);
                }
            }
        }
    }
}