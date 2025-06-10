package game.actors.npcs;

import game.actors.NPC;
import game.enums.Status;
import game.interfaces.BehaviourSelectionStrategy;

public class TrainingDummy extends NPC {
    public TrainingDummy(int hitPoints, BehaviourSelectionStrategy strategy) {
        super("Training Dummy", 'T', hitPoints, strategy);
        addCapability(Status.ATTACKABLE);
    }
}
