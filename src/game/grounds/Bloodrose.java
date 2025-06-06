package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A crop that harms both the planter and nearby actors.
 * Upon planting, it injures the actor who planted it. Each turn, it damages any actor in adjacent tiles.
 * Created by:
 * @author Chan Chee Wei
 * @author Muhammad Ali Raza
 */
public class Bloodrose extends Crop{
    /**
     * Constructs a Bloodrose crop with display character 'w' and name "Bloodrose".
     */
    public Bloodrose() {
        super('w', "Bloodrose");
    }

    /**
     * Called when the crop is planted. Hurts the planter for 5 hit points.
     *
     * @param planter the actor who planted the crop
     * @param location the location where the crop is planted
     */
    @Override
    public void onPlant(Actor planter, Location location) {
        planter.hurt(5);
    }

    /**
     * Returns the stamina cost required to plant this crop.
     *
     * @return 75 stamina
     */
    @Override
    public int getStaminaCost() {
        return 75;
    }

    /**
     * Called every game tick. Damages all actors in adjacent tiles by 10 hit points.
     *
     * @param location the location of this crop
     */
    @Override
    public void tick(Location location) {
        for (Exit exit : location.getExits()) {
            Actor actor = exit.getDestination().getActor();
            if (actor != null) {
                actor.hurt(10);
            }
        }
    }
}