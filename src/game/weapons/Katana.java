package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.effects.AttributeEffect;
import game.interfaces.Sellable;

/**
 * Represents the Katana, a weapon that deals moderate damage but has a drawback upon purchase.
 * <p>
 * The Katana delivers 50 damage with a 60% hit rate. Upon purchase, it reduces
 * the player's current health by 25 points. This reflects the weapon's cursed nature.
 * <p>
 * Implements the {@code Sellable} interface to support merchant interactions.
 * @author yathis
 */
public class Katana extends WeaponItem implements Sellable {

    /**
     * Constructs a Katana with predefined damage and accuracy attributes.
     * Name: "Katana", Display character: 'j', Damage: 50, Hit rate: 60%.
     */

    public Katana() {
        super("Katana", 'j', 50, "slash", 60);
    }

    /**
     * Returns the current instance of the Katana.
     * Used when the weapon is sold to generate a reference for the buyer.
     *
     * @return This Katana instance.
     */
    @Override
    public Item createItem() {
        return this;
    }

    /**
     * Triggers the base effect applied to the player upon purchasing the Katana.
     * <p>
     * Reduces the purchaser's current health by 25 using an {@code AttributeEffect}.
     *
     * @param purchaser The actor buying the weapon.
     * @param map       The current game map.
     * @return A string describing the triggered purchase effect.
     */
    @Override
    public String uponPurchase(Actor purchaser, GameMap map) {
        return new AttributeEffect(BaseActorAttributes.HEALTH, ActorAttributeOperations.DECREASE, 25, false).trigger(purchaser, map);
    }
}
