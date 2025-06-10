package game.actors.boss;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a growing branch attached to the {@link BedOfChaos} boss.
 * <p>
 * A branch contributes 3 damage to the boss's total attack power and can grow
 * additional branches or leaves during the boss's growth cycle.
 * </p>
 *
 * Created by:
 * @author Ali Raza
 */
public class Branch extends TreePart {
    private static final int DAMAGE_CONTRIBUTION = 3;
    private static final Random random = new Random();

    /**
     * Returns the fixed damage this branch contributes to the boss's attack.
     *
     * @return 3 damage points
     */
    @Override
    public int getDamageContribution() {
        return DAMAGE_CONTRIBUTION;
    }


    /**
     * Handles the growth behavior of this branch when the boss performs a growth cycle.
     * <p>
     * The branch may grow a new {@link TreePart} (branch or leaf) and propagate the growth
     * to any of its already-attached parts recursively.
     * </p>
     *
     * @param boss the {@link Actor} representing the boss
     * @param map  the game map context
     * @return a list of strings describing growth events
     */
    @Override
    public List<String> onBossGrowth(Actor boss, GameMap map) {
        List<String> messages = new ArrayList<>();

        // Branches have a chance to grow when the boss grows
        if (canGrow()) {
            TreePart newPart = grow();
            if (newPart != null) {
                addPart(newPart);
                messages.add(this + " is growing...");
                messages.add("It grows a " + newPart.getPartType() + "...");
            }
        }

        // Allow all attached parts to respond to growth and collect their messages
        for (TreePart part : attachedParts) {
            List<String> partMessages = part.onBossGrowth(boss, map);
            if (!partMessages.isEmpty()) {
                messages.add(""); // Add blank line before each sub-part's growth
                messages.addAll(partMessages);
            }
        }

        return messages;
    }

    /**
     * Determines whether this branch is eligible to grow this turn.
     *
     * @return true always (branches can always grow)
     */
    @Override
    public boolean canGrow() {
        return true;
    }

    /**
     * Grows a new {@link TreePart}, either a branch or a leaf (50/50 chance).
     *
     * @return a new {@link TreePart} instance
     */
    @Override
    public TreePart grow() {
        // 50% chance for branch, 50% chance for leaf
        return random.nextBoolean() ? new Branch() : new Leaf();
    }

    /**
     * Returns the type label of this tree part.
     *
     * @return the string "branch"
     */
    @Override
    public String getPartType() {
        return "branch";
    }

    /**
     * Returns the string representation of this part.
     *
     * @return "Branch"
     */
    @Override
    public String toString() {
        return "Branch";
    }
}