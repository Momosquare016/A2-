package game.actors.boss;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.ArrayList;
import java.util.List;

/**
 * A leaf part that heals the boss when it grows.
 * <p>
 * A {@code Leaf} contributes 1 damage point to the boss's total attack power
 * and provides a healing effect each time the boss performs a growth cycle.
 * Leaves cannot grow additional parts.
 * </p>
 *
 * Created by:
 * @author Ali Raza
 */
public class Leaf extends TreePart {
    private static final int DAMAGE_CONTRIBUTION = 1;
    private static final int HEALING_AMOUNT = 5;

    /**
     * Returns the fixed damage this leaf contributes to the boss's attack.
     *
     * @return 1 damage point
     */
    @Override
    public int getDamageContribution() {
        return DAMAGE_CONTRIBUTION;
    }

    /**
     * Performs the growth logic for a leaf.
     * <p>
     * When the boss grows, this leaf heals the boss for a fixed amount of HP.
     * </p>
     *
     * @param boss the {@link Actor} representing the boss
     * @param map  the game map context
     * @return a list of messages describing the healing effect
     */
    @Override
    public List<String> onBossGrowth(Actor boss, GameMap map) {
        List<String> messages = new ArrayList<>();

        // Leaves heal the boss when it grows
        boss.heal(HEALING_AMOUNT);
        messages.add(boss + " is healed by " + HEALING_AMOUNT + " points from " + this + " growth");

        return messages;
    }

    /**
     * Indicates whether the leaf can grow further.
     * Always returns {@code false} because leaves cannot grow.
     *
     * @return false
     */
    @Override
    public boolean canGrow() {
        return false; // Leaves cannot grow
    }

    /**
     * Attempts to grow a new part.
     * Always returns {@code null} because leaves cannot grow new parts.
     *
     * @return null
     */
    @Override
    public TreePart grow() {
        return null; // Leaves cannot grow additional parts
    }

    /**
     * Returns the type name of this part for display.
     *
     * @return "leaf"
     */
    @Override
    public String getPartType() {
        return "leaf";
    }

    /**
     * Returns the string representation of this part.
     *
     * @return "Leaf"
     */
    @Override
    public String toString() {
        return "Leaf";
    }
}
