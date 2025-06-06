package game.interfaces;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.Map;

public interface BehaviourSelectionStrategy {
    Action selectBehaviour(Actor actor, GameMap map, Map<Integer, Behaviour> behaviours);
}
