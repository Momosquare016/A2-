package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Relic of Berserker doubles the player's damage output
 * but causes them to lose 5 HP every turn.
 * Created by:
 * @author Yathis
 */
public class RelicOfBerserker extends CursedRelic {

    /**
     * Constructor for RelicOfBerserker, defining its name and description.
     */
    public RelicOfBerserker() {
        super("Relic of Berserker", "Double damage, lose 5 HP each turn");
    }

    /**
     * Applies the buff effect of this relic (e.g., logic handled in RelicEffectBehaviour).
     *
     * @param actor the player who is affected by the relic
     */
    @Override
    public void applyBuff(Actor actor) {
        // No direct effect here — handled in RelicEffectBehaviour
        System.out.println(actor + " gains the strength of the Berserker!");
    }

    /**
     * Applies the curse effect of this relic (e.g., lose HP per turn).
     *
     * @param actor the player who is affected by the relic
     */
    @Override
    public void applyCurse(Actor actor) {
        // Curse logic is applied via RelicEffectBehaviour each turn
        System.out.println(actor + " will lose 5 HP each turn due to Berserker's curse.");
    }

    /**
     * Removes the relic's effects. This is typically called when another relic is activated or the relic is destroyed.
     *
     * @param actor the player who held the relic
     */
    @Override
    public void removeEffect(Actor actor) {
        System.out.println(actor + " is no longer affected by the Relic of Berserker.");
    }
}







