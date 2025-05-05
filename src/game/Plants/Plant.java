package game.Plants;

import edu.monash.fit2099.engine.positions.Ground;
import java.util.List;
import edu.monash.fit2099.engine.positions.Location;
import game.Enum.SeedType;

import java.util.ArrayList;

/**
 * Abstract class representing a plant that can be grown from a seed.
 */
public abstract class Plant extends Ground {
    protected SeedType seedType;

    /**
     * Constructor.
     *
     * @param displayChar the character to display for this type of terrain
     * @param name the name of the plant
     * @param seedType the type of seed this plant grows from
     */
    public Plant(char displayChar, String name, SeedType seedType) {
        super(displayChar, name);
        this.seedType = seedType;
    }

    /**
     * Plants experience the passage of time.
     *
     * @param location The location of the Plant
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        tickEffect(location);
    }

    /**
     * Apply effect when initially planted.
     *
     * @param location The location where the plant is planted
     */
    public abstract void plantingEffect(Location location);

    /**
     * Apply effect each turn.
     *
     * @param location The location of the plant
     */
    public abstract void tickEffect(Location location);

    /**
     * Get surrounding locations (8 adjacent squares)
     *
     * @param location The center location
     * @return List of adjacent locations
     */
    protected List<Location> getSurroundingLocations(Location location) {
        List<Location> surroundings = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue; // Skip center

                int newX = location.x() + x;
                int newY = location.y() + y;

                if (location.map().getXRange().contains(newX) &&
                        location.map().getYRange().contains(newY)) {
                    surroundings.add(location.map().at(newX, newY));
                }
            }
        }

        return surroundings;
    }
}