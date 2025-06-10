package game.interfaces;

/**
 * Interface for weapons that can have dynamic damage calculations.
 * Allows weapons to modify their damage based on external factors.
 */
public interface DynamicWeapon {
    /**
     * Calculates the current damage for this weapon.
     * @return the current damage value
     */
    int getCurrentDamage();
}