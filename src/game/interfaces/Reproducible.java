package game.interfaces;

import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for entities that can reproduce.
 * This allows for different reproduction mechanisms for different entities.
 * @author Muhammad Ali Raza
 */
public interface Reproducible {
    /**
     * Attempt to reproduce based on surrounding conditions.
     *
     * @param map the current game map
     * @return true if reproduction occurred, false otherwise
     */
    boolean canReproduce(GameMap map);

    String reproduce(GameMap map);
}