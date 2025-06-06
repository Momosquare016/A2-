package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Listenable;

/**
 * An action that allows an actor (typically the player) to listen to a monologue from a {@link Listenable} NPC.
 * This action delegates the logic for generating monologue text to the {@code getMonologue} method
 * of the {@link Listenable} interface. It is typically used for creating conditional
 * dialogue with non-player characters (NPCs).
 * @author Chan Chee Wei
 */
public class ListenAction extends Action {
    private final Listenable npc;

    /**
     * Constructs a ListenAction targeting the given {@link Listenable} NPC.
     *
     * @param npc The NPC that implements {@link Listenable} and provides a monologue.
     */
    public ListenAction(Listenable npc) {
        this.npc = npc;
    }

    /**
     * Executes the listening action, returning the NPC's monologue text.
     *
     * @param actor The actor performing the action.
     * @param map   The current game map.
     * @return The NPC's monologue string.
     */
    @Override
    public String execute(Actor actor, GameMap map){
        return npc.getMonologue(actor, map);
    }

    /**
     * Returns a description of this action suitable for displaying in the action menu.
     *
     * @param actor The actor performing the action.
     * @return A string describing the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " listens to " + npc + "'s monologue";
    }
}
