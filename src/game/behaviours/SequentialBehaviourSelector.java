package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.BehaviourSelectionStrategy;

import java.util.Map;

public class SequentialBehaviourSelector implements BehaviourSelectionStrategy {
    @Override
    public Action selectBehaviour(Actor actor, GameMap map, Map<Integer, Behaviour> behaviours) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(actor, map);
            if (action != null) return action;
        }
        return new DoNothingAction();
    }
}