package game.actors;

/**
 * Tracks a countdown of turns before an NPC disappears or becomes unconscious.
 * Used by passive NPCs like the {@link OmenSheep} and {@link SpiritGoat}.
 * Once the countdown reaches zero, the NPC becomes inactive or is removed.
 * Created by:
 * @author Chan Chee Wei
 */
public class RotBehaviour {
    /**
     * The initial maximum number of turns before expiry.
     */
    private final int rotDuration;

    /**
     * The current number of turns left before expiry.
     */
    private int turnsRemaining;

    /**
     * Constructs a new RotBehaviour with a given rot duration in turns.
     *
     * @param rotDuration the number of turns before expiry
     */
    public RotBehaviour(int rotDuration) {
        this.rotDuration = rotDuration;
        this.turnsRemaining = rotDuration;
    }

    /**
     * Decrements the remaining turns and checks if the timer has expired.
     *
     * @return {@code true} if the countdown has reached zero or below; otherwise, {@code false}
     */
    public boolean tick() {
        turnsRemaining--;
        return turnsRemaining <= 0;
    }

    /**
     * Resets the countdown to the original duration.
     */
    public void reset() {
        this.turnsRemaining = rotDuration;
    }

    /**
     * Returns the number of turns remaining before expiry as a string.
     *
     * @return a formatted string showing countdown status
     */
    @Override
    public String toString() {
        return "(" + turnsRemaining + " turns to disappear)";
    }
}