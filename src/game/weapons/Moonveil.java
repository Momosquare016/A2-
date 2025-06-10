package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.ExitUtils;
import game.time.TimeManager;
import game.time.Time;
import game.interfaces.TimeSensitive;

import java.util.List;

/**
 * A special weapon that becomes stronger at night, known as the Moonveil Katana.
 * When equipped, the katana gains enhanced accuracy during {@link Time#NIGHT}.
 * Additionally, successful attacks at night trigger a "flanking wave" that deals
 * bonus damage to actors adjacent to the attacker but not the primary target.
 *
 * @author Chan Chee Wei
 */
public class Moonveil extends WeaponItem implements TimeSensitive {
    private final TimeManager timeManager;

    /**
     * Constructs a Moonveil Katana and registers it as time-sensitive.
     *
     * @param timeManager the global {@link TimeManager} to subscribe to
     */
    public Moonveil(TimeManager timeManager) {
        super("Moonveil Katana", 'M', 60, "cuts", 70);
        this.timeManager = timeManager;
        timeManager.addListener(this);
    }

    /**
     * Updates the weapon's hit rate based on the current time of day.
     * <p>
     * Gains increased hit rate (85%) at night; otherwise defaults to 70%.
     *
     * @param currentTime the updated time of day
     */
    @Override
    public void onTimeChange(Time currentTime) {
        setHitRate(currentTime == Time.NIGHT? 85 : 70);
    }

    /**
     * Provides a descriptive message when time changes, adding flavour to the weapon.
     *
     * @param currentTime the current time of day
     * @return a relevant message, or {@code null} if none should be shown
     */
    @Override
    public String getMessage(Time currentTime) {
        return switch (currentTime) {
            case DAWN -> "The Moonveil grows silent as the sun rises.";
            case NIGHT -> "The Moonveil hums softly under the moonlight...";
            default -> null;
        };
    }

    /**
     * Performs an attack on the target. If it's night, the attack also triggers
     * flanking damage to adjacent enemies that are on either side of the attacker and adjacent to the attacker.
     *
     * @param attacker the actor performing the attack
     * @param target   the actor being attacked
     * @param map      the map where the attack takes place
     * @return a string describing the outcome of the attack
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        if (isAttackSuccessful()) {
            target.hurt(getDamage());
            StringBuilder result = new StringBuilder(String.format("%s %s %s for %d damage", attacker, getVerb(), target, getDamage()));

            if (timeManager.getCurrentTime() == Time.NIGHT) {
                Location attackerLocation = map.locationOf(attacker);
                Location targetLocation = map.locationOf(target);

                int flankDamage = Math.max(1, Math.floorDiv(getDamage(), 2));

                ExitUtils exitUtils = new ExitUtils();
                Exit targetExit = exitUtils.getExitTowards(attackerLocation, targetLocation);
                List<Exit> flankExits = exitUtils.getFlankExits(attackerLocation, targetExit);

                for (Exit exit : flankExits) {
                    Actor flankTarget = exit.getDestination().getActor();
                    if (flankTarget != null) {
                        flankTarget.hurt(flankDamage);
                        result.append(String.format("%n%s %s %s using %s for %d damage from the flank", attacker, getVerb(), flankTarget, this, flankDamage));
                    }
                }
            }

            return result.toString();
        } else {
            return attacker + " misses " + target + ".";
        }
    }
}
