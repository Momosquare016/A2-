package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.BehaviourSelectionStrategy;

import java.util.*;

public class RandomBehaviourSelector implements BehaviourSelectionStrategy {
    private Random random = new Random();

    @Override
    public Action selectBehaviour(Actor actor, GameMap map, Map<Integer, Behaviour> behaviours) {
        if (behaviours.isEmpty()) {
            return null;
        }

        List<Behaviour> behaviourList = new ArrayList<>(behaviours.values());
        Collections.shuffle(behaviourList, random);

        for (Behaviour behaviour : behaviourList) {
            Action action = behaviour.getAction(actor, map);
            if (action != null) {
                return action;
            }
        }
        return new DoNothingAction();
    }
}
