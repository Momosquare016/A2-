package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import game.LocationUtils;
import game.enums.Ability;
import game.enums.Status;

/**
 * A status effect that represents poison damage over time.
 * <p>
 * Actors affected by {@code PoisonEffect} receive damage each tick,
 * with damage decreasing by 1 each turn (to a minimum of 1).
 * If the actor is near a poisonous location, the effect resets.
 * The effect ends when the duration expires, or when the actor dies.
 *
 * @author Chan Chee Wei
 */
public class PoisonEffect extends StatusEffect {
    private final int initialDamage;
    private int currentDamage;
    private final int tickDuration;
    private int currentTick;

    public PoisonEffect(int damage, int tickDuration) {
        super("Poison");
        this.initialDamage = damage;
        this.tickDuration = tickDuration;

        this.currentDamage = initialDamage;
        this.currentTick = tickDuration;
    }

    /**
     * Applies the poison effect to the actor at each game tick.
     * Damage is applied and reduced by 1 each tick (minimum 1).
     * If the actor is near a poisonous source, the effect is refreshed.
     * When the actor becomes unconscious, they are removed from the world.
     * When the duration runs out, the {@link Status#POISONED} capability is removed.
     *
     * @param location the location of the actor
     * @param actor    the actor being affected
     */
    @Override
    public void tick(Location location, Actor actor) {
        if (!actor.isConscious()) {
            return;
        }
        if (new LocationUtils(location).hasSurroundingWith(Ability.POISONOUS)) {
            currentDamage = initialDamage;
            currentTick = tickDuration;
        }
        if (currentTick > 0) {
            actor.hurt(currentDamage);
            currentDamage = Math.max(1, currentDamage - 1);
            currentTick--;

            if (!actor.isConscious()) {
                new Display().println(actor + " has succumbed to poison");
                actor.unconscious(location.map());
            }
        } else {
            actor.removeCapability(Status.POISONED);
        }
    }

    /**
     * Returns a string representation of the poison effect, including remaining duration.
     *
     * @return the effect's name and remaining tick count
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" (Remaining Duration: %d)", currentTick);
    }
}