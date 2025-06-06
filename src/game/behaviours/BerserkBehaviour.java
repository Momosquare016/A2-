package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.enums.Status;

/**
 * A behaviour that makes an actor attack nearby enemies with sufficient health.
 * This behaviour triggers when a target is within an adjacent location is {@link Status#ATTACKABLE},
 * and has health greater than or equal to the specified threshold.
 * If such a target is found, the actor will initiate an {@link AttackAction}.
 * Created by:
 * @author Chan Chee Wei
 */
public class BerserkBehaviour implements Behaviour {
    private final int healthThreshold;

    /**
     * Constructor.
     * Only adjacent actors with health equal to or above this threshold will be attacked.
     *
     * @param healthThreshold the minimum HP a target must have to trigger a berserk attack
     */
    public BerserkBehaviour(int healthThreshold) {
        this.healthThreshold = healthThreshold;
    }

    /**
     * Evaluates the surroundings of the given actor to determine if a valid attack target exists.
     * <p>
     * Checks each adjacent location (via exits) for an actor that:
     * <ul>
     *     <li>Is not null</li>
     *     <li>Has the {@link Status#ATTACKABLE} capability</li>
     *     <li>Has health greater than or equal to the health threshold</li>
     * </ul>
     * If such a target is found, returns an {@link AttackAction} targeting that actor.
     *
     * @param actor the actor possessing this behaviour
     * @param map the game map containing the actor
     * @return an {@link AttackAction} if a valid target is found; {@code null} otherwise
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);

        for (Exit exit : location.getExits()) {
            Location surrounding = exit.getDestination();
            Actor candidate = surrounding.getActor();
            if (candidate != null && canAttack(candidate)) {
                return new AttackAction(candidate, exit.getName());
            }
        }
        return null;
    }

    /**
     * Determines if the target actor meets the conditions to be attacked.
     *
     * @param target the actor being evaluated
     * @return {@code true} if the target has the {@code ATTACKABLE} status and enough health; {@code false} otherwise
     */
    private boolean canAttack(Actor target) {
        return target.hasCapability(Status.ATTACKABLE) &&
                target.getAttribute(BaseActorAttributes.HEALTH) >= healthThreshold;
    }
}
