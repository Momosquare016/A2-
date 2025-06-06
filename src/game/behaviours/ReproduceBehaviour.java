package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Reproducible;

/**
 * A behaviour that enables an actor to periodically perform a reproduction action, defined by a {@link Reproducible} entity.
 * <p>
 * The behaviour keeps track of time intervals and only triggers reproduction after a specified number of turns.
 * When triggered, it invokes the {@code reproduce} method from the associated {@code Reproducible} object.
 * </p>
 *
 * Created by:
 * @author LIJINLEI
 */
public class ReproduceBehaviour extends Action implements Behaviour {

    /** The entity that defines how reproduction occurs. */
    private final Reproducible reproducible;

    /** The number of turns between each reproduction attempt. */
    private final int productionInterval;

    /** Counter to keep track of the number of turns passed. */
    private int currentInterval = 0;

    /**
     * Constructor for {@code ReproduceBehaviour}.
     *
     * @param reproducible        the entity capable of reproduction
     * @param productionInterval  the number of turns between reproduction attempts
     */
    public ReproduceBehaviour(Reproducible reproducible, int productionInterval) {
        this.reproducible = reproducible;
        this.productionInterval = productionInterval;
    }

    /**
     * Determines whether the actor should perform the reproduction action this turn.
     * Increments the internal counter each turn. If the interval has elapsed and the
     * {@code Reproducible} deems it possible, this action will be returned for execution.
     *
     * @param actor the actor possessing this behaviour
     * @param map   the game map
     * @return this action if reproduction should occur, otherwise {@code null}
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (currentInterval < productionInterval) {
            currentInterval++;
            return null;
        } else if (reproducible.canReproduce(map)) {
            currentInterval = 0;
            return this;
        }
        return null;
    }

    /**
     * Executes the reproduction logic by calling the {@code reproduce} method of the {@link Reproducible}.
     *
     * @param actor the actor performing the action
     * @param map   the game map
     * @return a string describing the result of the reproduction
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = reproducible.reproduce(map);
        return reproducible + " has produced " + result;
    }

    /**
     * Provides a description of this action for display in the action menu.
     *
     * @param actor the actor performing the action
     * @return a string description of the reproduction action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " reproduces an offspring";
    }
}
