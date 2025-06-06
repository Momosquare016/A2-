package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for items or entities that can be consumed by actors.
 * Implementing classes define their own consumption effects.
 * @author Muhammad Ali Raza
 */
public interface Consumable {
    /**
     * Called when an entity is eaten by an actor.
     *
     * @param consumer the actor consuming this entity
     * @return a message describing the consumption
     */
    String consumeEffect(Actor consumer, GameMap map);
}