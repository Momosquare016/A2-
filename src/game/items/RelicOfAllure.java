package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * A specific implementation of {@link CursedRelic} that provides a boost to critical damage
 * but also causes enemies to be more aggressive toward the player.
 * <p>
 * When activated, this relic increases the player's critical hit potential but adds the drawback
 * of drawing aggro from nearby enemies.
 * </p>
 *
 * Created by:
 * @author Yathis
 */
public class RelicOfAllure extends CursedRelic {

    /**
     * Constructs a RelicOfAllure with a name and description.
     */
    public RelicOfAllure() {
        super("Relic of Allure", "Boost critical damage, enemies chase you");
    }

    /**
     * Applies the beneficial buff effect of the relic.
     * Typically increases the player's critical damage output.
     *
     * @param player the {@link Actor} receiving the buff
     */
    @Override
    public void applyBuff(Actor player) {
        // Increase crit damage
    }

    /**
     * Applies the cursed effect of the relic.
     * Typically makes enemies more likely to target or follow the player.
     *
     * @param player the {@link Actor} afflicted by the curse
     */
    @Override
    public void applyCurse(Actor player) {
        // Attract nearby enemies
    }

    /**
     * Removes both the buff and the curse effects from the player.
     * Should revert the player’s state to normal.
     *
     * @param player the {@link Actor} whose effects are to be removed
     */
    @Override
    public void removeEffect(Actor player) {
        // Revert changes
    }
}


