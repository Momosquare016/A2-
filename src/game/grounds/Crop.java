package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Abstract base class for all crops.
 * Crops are plantable ground elements with stamina costs and specific effects when planted and over time.
 * Created by:
 * @author Chan Chee Wei
 */
public abstract class Crop extends Ground {

    /**
     * Constructs a new Crop.
     *
     * @param displayChar the character to display on the map
     * @param name the name of the crop
     */
    public Crop(char displayChar, String name) {
        super(displayChar, name);
    }

    /**
     * Called when the crop is planted.
     *
     * @param planter the actor performing the planting
     * @param location the location where the crop is planted
     */
    public abstract void onPlant(Actor planter, Location location);

    /**
     * Returns the stamina cost to plant this crop.
     *
     * @return the stamina cost
     */
    public abstract int getStaminaCost();
}