package game.interfaces;

import game.time.Time;

/**
 * An interface for objects that react to changes in the game’s time cycle.
 * Implementing classes can define behaviour changes or state updates that occur
 * at specific times of day (e.g., dawn, noon, dusk, night).
 *
 * @author Chan Chee Wei
 */
public interface TimeSensitive {

    /**
     * Called whenever the time of day changes.
     * Implementing classes should update their state or behaviour accordingly.
     *
     * @param currentTime the new time of day
     */
    void onTimeChange(Time currentTime);

    /**
     * Returns a descriptive message (if any) to display when the time changes.
     * Useful for flavor text or alerts tied to the time transition.
     *
     * @param currentTime the current time of day
     * @return a message to display, or {@code null} if no message should be shown
     */
    String getMessage(Time currentTime);
}
