package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.Status;

/**
 * A crop that purifies land and heals actors.
 * On planting, it transforms all adjacent cursed tiles into Soil.
 * Each turn, it heals nearby actors and restores their stamina.
 * Created by:
 * @author Chan Chee Wei
 */
public class Inheritree extends Crop{
    /**
     * Constructs an Inheritree crop with display character 't' and name "Inheritree".
     */
    public Inheritree() {
        super('t', "Inheritree");
    }

    /**
     * Called when the crop is planted. Converts adjacent cursed ground into {@link Soil}.
     *
     * @param planter the actor who planted the crop
     * @param location the location where the crop is planted
     */
    @Override
    public void onPlant(Actor planter, Location location) {
        for (Exit exit : location.getExits()) {
            Location surrounding = exit.getDestination();
            if (surrounding.getGround().hasCapability(Status.CURSED)) {
                surrounding.setGround(new Soil());
            }
        }
    }

    /**
     * Returns the stamina cost to plant this crop.
     *
     * @return 25 stamina
     */
    @Override
    public int getStaminaCost() {
        return 25;
    }

    /**
     * Called every game tick. Heals nearby actors and increases their stamina by 5.
     *
     * @param location the location of this crop
     */
    @Override
    public void tick(Location location) {
        for (Exit exit : location.getExits()) {
            Actor actor = exit.getDestination().getActor();
            if (actor != null) {
                actor.heal(5);
                if (actor.hasAttribute(BaseActorAttributes.STAMINA)) {
                    actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 5);
                }
            }
        }
    }
}