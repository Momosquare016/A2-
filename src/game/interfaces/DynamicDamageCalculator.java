package game.interfaces;

/**
 * Interface for calculating dynamic damage bonuses.
 * Allows different entities to provide their own damage calculation logic.
 */
public interface DynamicDamageCalculator {
    /**
     * Calculates bonus damage to add to base weapon damage.
     * @return additional damage points
     */
    int calculateBonusDamage();
}
