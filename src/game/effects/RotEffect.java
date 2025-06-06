package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A status effect that represents Crimson Rot, a condition that eventually knocks out the afflicted actor.
 * The rot progresses each turn, and if not cured in time, the actor becomes unconscious after a fixed duration.
 * @author Chan Chee Wei
 */
public class RotEffect extends StatusEffect {
    private final int rotTimer;

    /** Tracks the number of turns the effect has been active. */
    private int currentTurn = 0;

    /**
     * Constructs a RotEffect with the specified duration.
     *
     * @param rotTimer the number of turns before the effect takes full effect
     */
    public RotEffect(int rotTimer) {
        super("Crimson Rot");
        this.rotTimer = rotTimer;
    }

    /**
     * Returns the configured rot duration in turns.
     *
     * @return number of turns before the actor becomes unconscious
     */
    public int getRotTimer() {
        return rotTimer;
    }

    /**
     * Resets the internal turn counter, as if the rot has just started.
     */
    public void reset() {
        currentTurn = 0;
    }

    /**
     * Called once per turn while the effect is active on an actor.
     * <p>
     * If the number of turns exceeds the rot timer, the actor is made unconscious.
     *
     * @param location the actor's current location
     * @param actor    the affected actor
     */
    @Override
    public void tick(Location location, Actor actor) {
        if (currentTurn >= rotTimer) {
            new Display().println(actor + " has succumbed to " + this);
            actor.unconscious(location.map());
        } else {
            currentTurn++;
        }
    }

    /**
     * Returns a string representation of the number of turns remaining until the rot takes effect.
     *
     * @return formatted string showing remaining turns
     */
    public String getTurnLeft() {
        int turnsLeft = getRotTimer() - currentTurn;

        String result = (turnsLeft) + "/" + getRotTimer();
        result += " turn" + (turnsLeft == 1 ? "" : "s");
        result += " left";
        return result;
    }
}