package game.interfaces;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for NPCs that can be listened to for monologues or dialogue.
 * Implementing classes must provide a context-sensitive or fixed monologue that is returned
 * when another actor performs a {@code ListenAction}.
 * Created by:
 * @author Chan Chee Wei
 */
public interface Listenable {
    String getMonologue(Actor listener, GameMap map);
}
