package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Represents an effect that can be applied to an {@link Actor} within a {@link GameMap}.
 * Effects can include modifying actor attributes (e.g., healing, damaging, stamina changes),
 * spawning new entities, or combinations of multiple effects.
 * @author Chan Chee Wei
 */
public interface Effect {

    /**
     * Applies the effect to the given actor within the specified game map.
     *
     * @param actor the actor that the effect will be applied to
     * @param map   the game map in which the effect occurs
     * @return a string description of what happened when the effect was triggered
     */
    String trigger(Actor actor, GameMap map);
}