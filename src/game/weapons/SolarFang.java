package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.time.TimeManager;
import game.time.Time;
import game.interfaces.TimeSensitive;

/**
 * An intrinsic weapon used by solar-powered creatures that changes its effectiveness based on the time of day.
 * The weapon is strongest at {@link Time#NOON}, weaker at {@link Time#DAWN} and {@link Time#DUSK},
 * and least effective at {@link Time#NIGHT}. This weapon is registered as a {@link TimeSensitive} listener.
 *
 * @author Chan Chee Wei
 */
public class SolarFang extends IntrinsicWeapon implements TimeSensitive {

    /**
     * Constructs a SolarFang and registers it with the time manager to receive time updates.
     *
     * @param timeManager the global {@link TimeManager} instance
     */
    public SolarFang(TimeManager timeManager) {
        super(25, "bites", 50);
        timeManager.addListener(this);
    }

    /**
     * Returns the weapon's damage based on the specified time of day.
     *
     * @param time the current {@link Time}
     * @return the damage value to use
     */
    private int getDamage(Time time) {
        return switch (time) {
            case NOON -> 40;
            case DAWN, DUSK -> 25;
            case NIGHT -> 15;
        };
    }

    /**
     * Returns the weapon's hit rate based on the specified time of day.
     *
     * @param time the current {@link Time}
     * @return the hit rate percentage
     */
    private int getHitRate(Time time) {
        return switch (time) {
            case NOON -> 60;
            case DAWN, DUSK -> 40;
            case NIGHT -> 25;
        };
    }

    /**
     * Updates the weapon's damage and hit rate when the time of day changes.
     *
     * @param currentTime the new {@link Time} value
     */
    @Override
    public void onTimeChange(Time currentTime) {
        this.damage = getDamage(currentTime);
        this.hitRate = getHitRate(currentTime);
    }

    /**
     * Provides a message describing how the SolarFang changes with time.
     *
     * @param currentTime the current time of day
     * @return a message if applicable, or {@code null} if no message should be displayed
     */
    @Override
    public String getMessage(Time currentTime) {
        return switch (currentTime) {
            case NOON -> "The Solar Fang glows with power!";
            case NIGHT -> "The Solar Fang has dimmed.";
            default -> null;
        };
    }
}