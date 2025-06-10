package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * A singleton manager responsible for handling the activation and deactivation of cursed relics.
 * Ensures that only one {@link CursedRelic} can be active at a time for an {@link Actor}.
 * <p>
 * Created by:
 * @author Yathis
 */
public class RelicManager {

    /** The single instance of the RelicManager (singleton pattern). */
    private static RelicManager instance;

    /** The currently active cursed relic. Only one can be active at a time. */
    private CursedRelic activeRelic;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private RelicManager() {}

    /**
     * Returns the single instance of RelicManager.
     * If it doesn't exist yet, it is created.
     *
     * @return the singleton instance of RelicManager
     */
    public static RelicManager getInstance() {
        if (instance == null) instance = new RelicManager();
        return instance;
    }

    /**
     * Activates a new cursed relic for the specified actor.
     * If another relic is currently active, its effect is removed first.
     *
     * @param relic the {@link CursedRelic} to activate
     * @param actor the {@link Actor} receiving the relic's buff
     */
    public void activateRelic(CursedRelic relic, Actor actor) {
        if (activeRelic != null) activeRelic.removeEffect(actor);
        activeRelic = relic;
        relic.applyBuff(actor);
    }

    /**
     * Gets the currently active cursed relic.
     *
     * @return the active {@link CursedRelic}, or null if none is active
     */
    public CursedRelic getActiveRelic() {
        return activeRelic;
    }

    /**
     * Destroys the currently active cursed relic, removing its effects from the actor.
     *
     * @param actor the {@link Actor} whose buff should be removed
     */
    public void destroyRelic(Actor actor) {
        if (activeRelic != null) {
            activeRelic.removeEffect(actor);
            activeRelic = null;
        }
    }
}





