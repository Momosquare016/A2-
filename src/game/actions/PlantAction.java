package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.ground.Bloodrose;
import game.Plants.Plant;
import game.Plants.Seed;
import game.Enum.SeedType;
import game.actors.Player;
import game.ground.Inheritree;
import game.ground.Soil;

/**
 * Action to plant a seed.
 */
public class PlantAction extends Action {

    private Seed seed;

    /**
     * Constructor.
     *
     * @param seed The seed to plant
     */
    public PlantAction(Seed seed) {
        this.seed = seed;
    }

    /**
     * Plant the seed at the actor's location if it's valid ground.
     *
     * @param actor The actor planting the seed
     * @param map The map the actor is on
     * @return A description of the planting action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        SeedType seedType = seed.getType();

        // Check if the ground is soil (can't plant on blight)
        if (!(location.getGround() instanceof Soil)) {
            return actor + " cannot plant on " + location.getGround() + ".";
        }

        // Check stamina requirements
        if (actor instanceof Player) {
            Player player = (Player) actor;
            int staminaCost = getStaminaCost(seedType);

            if (!player.useStamina(staminaCost)) {
                return actor + " doesn't have enough stamina to plant.";
            }
        }

        // Remove the seed from inventory
        actor.removeItemFromInventory(seed);

        // Create plant and apply effects
        Plant plant = createPlant(seedType);
        location.setGround(plant);
        plant.plantingEffect(location);

        return actor + " plants a " + plant + ".";
    }

    /**
     * Create a plant from a seed type
     *
     * @param seedType The type of seed
     * @return The created plant
     */
    private Plant createPlant(SeedType seedType) {
        if (seedType == SeedType.INHERITREE) {
            return new Inheritree();
        } else {
            return new Bloodrose();
        }
    }

    /**
     * Get stamina cost for planting a seed type
     *
     * @param seedType The type of seed
     * @return The stamina cost
     */
    private int getStaminaCost(SeedType seedType) {
        return seedType == SeedType.INHERITREE ? 25 : 75;
    }

    /**
     * A description of the action suitable for display in the UI menu.
     *
     * @param actor The actor performing the action
     * @return A string describing the action
     */
    @Override
    public String menuDescription(Actor actor) {
        int staminaCost = getStaminaCost(seed.getType());
        return actor + " plants " + seed + " (" + staminaCost + " stamina)";
    }
}