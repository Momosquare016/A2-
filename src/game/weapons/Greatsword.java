package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.effects.AttributeEffect;
import game.interfaces.Sellable;

/**
 * Represents the Dragonslayer Greatsword, a powerful weapon available for purchase from merchants.
 * <p>
 * This weapon deals high damage (70) with a 75% hit rate. Upon purchase, it increases
 * the purchaser's maximum health by 15 using an AttributeEffect.
 * Implements the {@code Sellable} interface to allow it to be bought from merchants.
 * @author yathis
 */
public class Greatsword extends WeaponItem implements Sellable {
    /**
     * Constructs a Dragonslayer Greatsword with predefined attributes.
     * Name: "Dragonslayer Greatsword", Display char: 'D', Damage: 70, Hit rate: 75%.
     */
    public Greatsword() {
        super("Dragonslayer Greatsword", 'D', 70, "slash", 75);
    }

    /**
     * Returns the current instance of the weapon for use during purchase.
     *
     * @return This Greatsword instance.
     */
    @Override
    public Item createItem() {
        return this;
    }

    /**
     * Applies the effect of purchasing the Greatsword.
     * Increases the purchaser's maximum health by 15.
     *
     * @param purchaser The actor buying the weapon.
     * @param map       The current game map.
     * @return A description of the triggered effect.
     */
    @Override
    public String uponPurchase(Actor purchaser, GameMap map) {
        return new AttributeEffect(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15, true).trigger(purchaser, map);
    }
}
