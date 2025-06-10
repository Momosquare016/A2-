package game.actors.boss;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for parts that can be attached to the Bed of Chaos boss.
 * Follows the Strategy pattern to allow different types of tree parts with varying behaviors.
 */
public abstract class TreePart {
    protected List<TreePart> attachedParts = new ArrayList<>();

    /**
     * Returns the damage contribution of this specific part (not including attached parts).
     * @return damage points this part contributes to the boss's attack
     */
    public abstract int getDamageContribution();

    /**
     * Called when the boss grows - allows this part to respond to growth.
     * @param boss the boss that is growing
     * @param map the current game map
     * @return a list of messages describing what this part did during growth
     */
    public abstract List<String> onBossGrowth(Actor boss, GameMap map);

    /**
     * Determines if this part can grow additional parts.
     * @return true if this part can grow, false otherwise
     */
    public abstract boolean canGrow();

    /**
     * Creates a new part when this part grows.
     * @return new TreePart instance, or null if cannot grow
     */
    public abstract TreePart grow();

    /**
     * Returns the name/type of this tree part for display purposes.
     * @return the type name of this part
     */
    public abstract String getPartType();

    /**
     * Adds a new part to this tree part.
     * @param part the part to attach
     */
    public void addPart(TreePart part) {
        attachedParts.add(part);
    }

    /**
     * Gets all parts attached to this part.
     * @return unmodifiable list of attached parts
     */
    public List<TreePart> getAttachedParts() {
        return Collections.unmodifiableList(attachedParts);
    }

    /**
     * Calculates total damage contribution including all attached parts recursively.
     * @return total damage from this part and all its attached parts
     */
    public int getTotalDamageContribution() {
        int total = getDamageContribution();
        for (TreePart part : attachedParts) {
            total += part.getTotalDamageContribution();
        }
        return total;
    }
}