package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * The RelicOfSpeed grants the player enhanced movement capability at the cost of potential turn loss.
 * <p>
 * Buff: Allows the player to move two tiles per turn. <br>
 * Curse: 50% chance to skip the player's turn.
 * </p>
 * Created by:
 * @author yathis
 */
public class RelicOfSpeed extends CursedRelic {

    /**
     * Constructor to initialize the Relic of Speed with a name and description.
     */
    public RelicOfSpeed() {
        super("Relic of Speed", "Move two tiles per turn, 50% chance to skip next turn");
    }

    /**
     * Applies the positive effect of the relic.
     * This typically would increase the player's movement range or allow extra movement.
     *
     * @param player The actor receiving the buff.
     */
    @Override
    public void applyBuff(Actor player) {
        // Increase movement range
    }

    /**
     * Applies the negative effect of the relic.
     * This introduces a 50% chance for the player to lose their next turn.
     *
     * @param player The actor affected by the curse.
     */
    @Override
    public void applyCurse(Actor player) {
        // Randomly skip player turn
    }

    /**
     * Removes both buff and curse effects from the player.
     * Should revert the player's movement to its original state and stop turn skipping.
     *
     * @param player The actor whose effects should be removed.
     */
    @Override
    public void removeEffect(Actor player) {
        // Revert movement range
    }
}


