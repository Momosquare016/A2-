package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface for entities (actors or ground) that can be cured via a {@link game.actions.CureAction}.
 * Implementing classes define how they respond to a curing attempt.
 * Created by:
 * @author Chan Chee Wei
 * @author Muhammad Ali Raza
 */
public interface Curable {
    /**
     * Defines how the object is cured.
     *
     * @param actor the actor performing the cure
     * @param map the game map
     * @return a string describing the result of the cure
     */
    String cure(Actor actor, GameMap map);
}