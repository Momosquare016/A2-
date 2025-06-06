package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.effects.AttributeEffect;
import game.interfaces.Sellable;

/**
 * A basic weapon sold by merchants that deals moderate damage and applies
 * a health-related effect when purchased.
 * The Broadsword strikes with 30 damage at 50% hit rate.
 * When purchased, it heals the player by 10 HP (not max HP).
 * Implements the Sellable interface for merchant transactions.
 * @author Yathis
 */
public class Broadsword extends WeaponItem implements Sellable {

    /**
     * Constructor for the Broadsword.
     * Sets the name, display character, damage, verb, and hit chance.
     */
    public Broadsword() {
        super("Broadsword", 'b', 30, "slash", 50);
    }


    /**
     * Creates and returns a new instance of the Broadsword.
     *
     * @return this Broadsword instance (non-cloned).
     */
    @Override
    public Item createItem() {
        return this;
    }

    /**
     * Applies a health effect when the item is purchased.
     * Increases the purchaser's current HP by 10.
     *
     * @param purchaser The actor buying the item.
     * @param map       The current game map.
     * @return A description of the effect triggered.
     */
    @Override
    public String uponPurchase(Actor purchaser, GameMap map) {
        return new AttributeEffect(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 10, false).trigger(purchaser, map);
    }
}
