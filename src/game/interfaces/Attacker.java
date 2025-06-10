package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface for actors that have custom logic to determine whether they can attack a target.
 * This allows more control over who an actor can attack based on the game state,
 * such as target attributes, position, or other conditions.
 *
 * @author Chan Chee Wei
 */
public interface Attacker {
    /**
     * Determines whether the given actor is a valid target for attack.
     *
     * @param target the potential target actor
     * @param map the map the actor is on
     * @return true if this actor is allowed to attack the target; false otherwise
     */
    boolean canAttack(Actor target, GameMap map);
}
