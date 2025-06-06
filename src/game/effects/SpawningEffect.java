package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.LocationUtils;

import java.util.List;
import java.util.Random;

/**
 * An {@link Effect} implementation that spawns a new actor at a valid adjacent location.
 * This effect attempts to find a nearby, unoccupied location that the target actor can enter
 * and places a new instance of the specified actor there. If no valid location is found, the
 * spawning fails gracefully with a descriptive message.
 * Created by:
 * @author Chan Chee Wei
 */
public class SpawningEffect implements Effect{
    private final Actor spawn;

    /** Random generator used to select a spawn location. */
    private final Random random;

    /**
     * Constructs a SpawningEffect with the given actor to be spawned.
     *
     * @param spawn the actor to spawn when this effect is triggered
     */
    public SpawningEffect(Actor spawn) {
        this.spawn = spawn;
        this.random = new Random();
    }

    /**
     * Triggers the spawning effect by placing a new actor at a valid nearby location.
     *
     * @param actor the actor who triggers the effect (used to determine spawn center)
     * @param map the current game map
     * @return a description of the outcome (success or failure)
     */
    @Override
    public String trigger(Actor actor, GameMap map) {
        Location center = map.locationOf(actor);
        List<Location> surroundings = new LocationUtils(center).findValidSpawnLocations(actor);

        if (surroundings.isEmpty()) {
            return String.format("No valid location to spawn %s near %s", spawn, actor);
        }

        Location spawnLocation = surroundings.get(random.nextInt(surroundings.size()));
        spawnLocation.addActor(spawn);
        return String.format("New %s spawned at %s's surrounding", spawn, actor);
    }
}