package game.behaviours;

import edu.monash.fit2099.demo.huntsman.AttackAction;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.LocationUtils;
import game.enums.Status;

/**
 * A behaviour that allows an actor to retaliate passively when attacked.
 * <p>
 * The actor does not actively pursue combat unless it detects a health drop or finds a nearby attacker
 * with the {@link Status#ACTIVE_ATTACKER} capability. Once engaged, it will chase and attempt to attack the aggressor.
 *
 *  @author Chan Chee Wei
 */
public class PassiveAttackBehaviour implements Behaviour {

    /**
     * The actor currently being tracked as the attacker. May be null if no aggressor is detected.
     */
    private Actor attacker;

    /**
     * The actor's current health, used to detect when it has taken damage.
     */
    private int currentHealth;

    /**
     * Determines the next action the actor should take.
     * If the actor's health has dropped during the turn, it searches for an adjacent attacker.
     * If an attacker is known and still present, the actor will move toward or attack them.
     *
     * @param actor the actor this behaviour belongs to
     * @param map the map the actor is on
     * @return an {@link Action} to perform (attack or move), or {@code null} if no action is taken
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        LocationUtils utils = new LocationUtils(here);

        int previousHealth = currentHealth;
        currentHealth = actor.getAttribute(BaseActorAttributes.HEALTH);

        if (attacker == null || previousHealth > currentHealth) {
            attacker = utils.getAdjacentActorWith(Status.ACTIVE_ATTACKER);
            if (attacker != null) {
                attacker.removeCapability(Status.ACTIVE_ATTACKER);
            }
        }

        if (attacker != null) {
            if (!map.contains(attacker)) {
                attacker = null;
                return null;
            }

            Location attackerLocation = map.locationOf(attacker);
            if (utils.isAdjacentTo(attackerLocation)) {
                String direction = utils.getDirectionToActor(attacker);
                return new AttackAction(attacker, direction);
            }

            Exit bestExit = utils.getBestExitTowards(attackerLocation, actor);
            if (bestExit != null) {
                return new MoveActorAction(bestExit.getDestination(), bestExit.getName());
            }
        }

        return null;
    }

    /**
     * Clears the currently tracked attacker.
     * Used to reset the behaviour, typically when switching from passive to aggressive states.
     */
    public void clearAttacker() {
        this.attacker = null;
    }
}
