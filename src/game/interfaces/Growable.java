package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for actors that have growth capabilities.
 * Eliminates the need for instanceof checks when determining if an actor can grow.
 */
public interface Growable {
    /**
     * Performs the growth action for this entity.
     * @param map the current game map
     * @return a description of what grew
     */
    String performGrowth(GameMap map);

    /**
     * Determines if this entity can currently grow.
     * @param map the current game map
     * @return true if growth is possible
     */
    boolean canPerformGrowth(GameMap map);
}