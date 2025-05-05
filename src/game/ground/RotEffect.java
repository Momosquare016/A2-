package game.ground;

/**
 * Interface for actors affected by Crimson Rot
 */
public interface RotEffect {
    /**
     * Check if this entity has a countdown timer
     *
     * @return true if the entity has a countdown
     */
    boolean hasCountdown();

    /**
     * Get the current countdown value
     *
     * @return current countdown in turns
     */
    int getCountdown();

    /**
     * Decrement the countdown
     *
     * @return the new countdown value
     */
    int decrementCountdown();

    /**
     * Reset the countdown to the original value
     */
    void resetCountdown();

    /**
     * Get the maximum countdown value
     *
     * @return the maximum countdown value
     */
    int getMaxCountdown();
}