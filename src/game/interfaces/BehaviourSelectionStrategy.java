package game.interfaces;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.Map;
/**
 * An interface for defining different strategies to select an {@link Action} based on available behaviours.
 * <p>
 * Implementing classes can provide different ways of evaluating and prioritizing behaviours
 * to determine which action an {@link Actor} should perform during its turn.
 * </p>
 *
 * Created by:
 * @author Lin JIn Lei
 */
public interface BehaviourSelectionStrategy {

    /**
     * Selects an appropriate action for the actor from the provided behaviours.
     *
     * @param actor      the {@link Actor} whose action is being selected
     * @param map        the current {@link GameMap}
     * @param behaviours a map of priority integers to {@link Behaviour} instances
     * @return the selected {@link Action} to be performed, or {@code null} if no action is selected
     */
    Action selectBehaviour(Actor actor, GameMap map, Map<Integer, Behaviour> behaviours);
}
