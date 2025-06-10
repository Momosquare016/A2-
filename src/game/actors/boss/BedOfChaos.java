// BedOfChaos.java - Corrected with proper imports
package game.actors.boss;

import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.NPC;
import game.behaviours.BossAttackBehaviour;
import game.behaviours.BossGrowthBehaviour;
import game.enums.Status;
import game.interfaces.BehaviourSelectionStrategy;
import game.interfaces.Growable;
import game.interfaces.DynamicDamageCalculator;
import game.weapons.BossWeapon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The Bed of Chaos is a stationary boss that grows tree parts each turn and becomes stronger.
 * It implements both {@link Growable} and {@link DynamicDamageCalculator} interfaces, allowing
 * it to grow and scale its damage dynamically.
 * <p>
 * Each turn, it grows a new {@link TreePart} (either a {@link Branch} or {@link Leaf}) and allows
 * existing branches to grow further. Its damage increases based on the total contribution from all parts.
 * </p>
 *
 * Created by:
 * @author Ali Raza
 */
public class BedOfChaos extends NPC implements Growable, DynamicDamageCalculator {

    /** The list of all TreeParts currently attached to the boss. */
    private final List<TreePart> parts = new ArrayList<>();

    /** Random instance used for growth and branching decisions. */
    private final Random random = new Random();

    /**
     * Constructor for the Bed of Chaos.
     *
     * @param strategy the behaviour selection strategy that determines how this boss acts
     */
    public BedOfChaos(BehaviourSelectionStrategy strategy) {
        super("Bed of Chaos", 'T', 1000, strategy);

        this.addCapability(Status.ATTACKABLE);
        this.addCapability(Status.CAN_GROW);

        // Behaviors in priority order: attack first, then grow
        this.addBehaviour(1, new BossAttackBehaviour());
        this.addBehaviour(10, new BossGrowthBehaviour(this));

        // Set the special weapon that uses this boss as damage calculator
        this.setIntrinsicWeapon(new BossWeapon(this));
    }

    /**
     * Performs growth for the Bed of Chaos.
     * Grows a new TreePart, then allows existing Branches to grow recursively.
     *
     * @param map the current game map
     * @return a string summarizing all growth messages
     */
    @Override
    public String performGrowth(GameMap map) {
        // Start with the main growth message
        List<String> allMessages = new ArrayList<>();
        allMessages.add(this + " is growing...");

        // RULE 1: Boss itself always grows one new branch or leaf (50% branch or 50% leaf)
        TreePart newMainPart = random.nextBoolean() ? new Branch() : new Leaf();
        addPart(newMainPart);
        allMessages.add("It grows a " + newMainPart.getPartType() + "...");

        // RULE 2: Every branch that ALREADY EXISTS in that turn grows once
        // RULE 3: Any branch created in this turn does NOT get to grow until the next turn

        // Create snapshot of parts that existed BEFORE this growth turn started
        // This ensures newly created parts (including the one we just added) don't grow
        List<TreePart> partsExistingBeforeThisTurn = new ArrayList<>();
        for (TreePart part : parts) {
            if (part != newMainPart) { // Exclude the part we just created
                partsExistingBeforeThisTurn.add(part);
            }
        }

        // Only process parts that existed before this turn
        // Use polymorphism - each part knows how to handle growth
        for (TreePart existingPart : partsExistingBeforeThisTurn) {
            List<String> partMessages = existingPart.onBossGrowth(this, map);
            if (!partMessages.isEmpty()) {
                allMessages.add(""); // Add blank line before each part's growth
                allMessages.addAll(partMessages);
            }
        }

        // Add final summary showing current boss state
        int currentHP = this.getAttribute(BaseActorAttributes.HEALTH);
        int maxHP = this.getAttributeMaximum(BaseActorAttributes.HEALTH);
        int currentATK = 25 + calculateBonusDamage();

        allMessages.add("");
        allMessages.add(this.name + " (HP: " + currentHP + "/" + maxHP + ", ATK: " + currentATK + ")");

        // Join all messages with newlines for proper display
        return String.join("\n", allMessages);
    }

    /**
     * Determines if the Bed of Chaos can grow this turn.
     *
     * @param map the current game map
     * @return true, since the boss can always grow
     */
    @Override
    public boolean canPerformGrowth(GameMap map) {
        return true; // Boss can always grow
    }

    /**
     * Calculates the bonus damage contributed by all TreeParts.
     *
     * @return the total additional damage
     */
    @Override
    public int calculateBonusDamage() {
        int total = 0;
        for (TreePart part : parts) {
            total += part.getTotalDamageContribution();
        }
        return total;
    }

    /**
     * Adds a new tree part to the boss.
     * @param part the tree part to add
     */
    public void addPart(TreePart part) {
        parts.add(part);
    }

    /**
     * Gets all tree parts attached to this boss.
     * @return unmodifiable list of tree parts
     */
    public List<TreePart> getParts() {
        return Collections.unmodifiableList(parts);
    }

    /**
     * Enhanced toString showing current damage potential.
     */
    @Override
    public String toString() {
        int totalDamage = 25 + calculateBonusDamage(); // Base + parts
        return name + " (HP: " +
                this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                ", ATK: " + totalDamage + ")";
    }
}