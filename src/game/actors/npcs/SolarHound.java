package game.actors.npcs;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.BehaviourSelectionStrategy;
import game.time.TimeManager;
import game.actors.NPC;
import game.behaviours.ActiveAttackBehaviour;
import game.behaviours.PassiveAttackBehaviour;
import game.behaviours.WanderBehaviour;
import game.enums.Status;
import game.time.Time;
import game.interfaces.Attacker;
import game.interfaces.TimeSensitive;
import game.weapons.SolarFang;

/**
 * A Solar Hound is a time-sensitive NPC that changes its behaviour and health attributes based on the time of day.
 * It becomes the most powerful at noon and weakest at night.
 * Implements {@link TimeSensitive} and {@link Attacker}.
 *
 * @author Chan Chee Wei
 */
public class SolarHound extends NPC implements TimeSensitive, Attacker {

    /**
     * Behaviour when the Solar Hound is in a passive state (e.g. at night).
     */
    private final PassiveAttackBehaviour passiveAttackBehaviour = new PassiveAttackBehaviour();

    /**
     * Behaviour when the Solar Hound is active and aggressive (e.g. during the day).
     */
    private final ActiveAttackBehaviour activeAttackBehaviour = new ActiveAttackBehaviour(this);

    /**
     * Constructs a Solar Hound and registers it as a listener to the provided {@link TimeManager}.
     * Adds intrinsic weapon and default behaviours.
     *
     * @param timeManager the global time manager to observe time changes from
     */
    public SolarHound(TimeManager timeManager, BehaviourSelectionStrategy strategy) {
        super("Solar Hound", 'S', 100, strategy);
        timeManager.addListener(this);
        addCapability(Status.ATTACKABLE);

        addBehaviour(1, new PassiveAttackBehaviour());
        addBehaviour(999, new WanderBehaviour());

        this.setIntrinsicWeapon(new SolarFang(timeManager));
    }

    /**
     * Called by the {@link TimeManager} when the time changes.
     * Updates the maximum HP and adjusts the current HP accordingly.
     * Also updates the Solar Hound’s attack behaviour based on the time of day.
     *
     * @param currentTime the new time of day
     */
    @Override
    public void onTimeChange(Time currentTime) {
        BaseActorAttributes health = BaseActorAttributes.HEALTH;
        int currentHP = this.getAttribute(health);
        int currentMaxHP = this.getAttributeMaximum(health);

        int newMaxHP;
        if (currentTime == Time.DAWN || currentTime == Time.DUSK) {
            newMaxHP = 150;
        } else if (currentTime == Time.NOON) {
            newMaxHP = 200;
        } else {
            newMaxHP = 100;
        }

        this.modifyAttributeMaximum(health, ActorAttributeOperations.UPDATE, newMaxHP);

        int adjustedHP = newMaxHP - (currentMaxHP - currentHP);
        adjustedHP = Math.min(adjustedHP, newMaxHP);
        adjustedHP = Math.max(0, adjustedHP);
        this.modifyAttribute(health, ActorAttributeOperations.UPDATE, adjustedHP);

        updateBehaviours(currentTime);
    }

    /**
     * Updates the Solar Hound's behaviour based on the current time.
     * Switches between passive and aggressive attack modes.
     *
     * @param currentTime the current time of day
     */
    private void updateBehaviours(Time currentTime) {
        if (currentTime == Time.DUSK || currentTime == Time.NIGHT) {
            behaviours.replace(1, passiveAttackBehaviour);
            addCapability(Status.PASSIVE_ENEMY);
        } else {
            passiveAttackBehaviour.clearAttacker();
            behaviours.replace(1, activeAttackBehaviour);
            removeCapability(Status.PASSIVE_ENEMY);
        }
    }

    /**
     * Provides a descriptive message based on the time of day.
     *
     * @param currentTime the current time of day
     * @return a String message if applicable, or {@code null} if no message is appropriate
     */
    @Override
    public String getMessage(Time currentTime) {
        return switch (currentTime) {
            case NOON -> "The Solar Hound basks in the full glory of the sun!";
            case NIGHT -> "The Solar Hound has become sluggish...";
            default -> null;
        };
    }

    /**
     * Determines whether the Solar Hound can attack a given target.
     * The target must have at least 25% of its maximum health to be attacked.
     *
     * @param target the target actor
     * @param map the game map the actor is on
     * @return true if the target is attackable; false otherwise
     */
    @Override
    public boolean canAttack(Actor target, GameMap map) {
        BaseActorAttributes health = BaseActorAttributes.HEALTH;
        int maxHP = target.getAttributeMaximum(health);

        return target.getAttribute(BaseActorAttributes.HEALTH) >= 0.25 * maxHP;
    }
}
