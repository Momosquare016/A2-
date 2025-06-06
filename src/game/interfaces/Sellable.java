package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for items that can be sold by merchants in the game.
 * <p>
 * Sellable items define custom purchase behavior and allow for instantiation
 * of item instances during transactions. Used by merchant-related features such as REQ4.
 * @author yathis
 */
public interface Sellable {

    /**
     * Creates and returns a new instance of the item being sold.
     * <p>
     * This method ensures each purchase results in a fresh item instance,
     * supporting inventory management and avoiding shared references.
     *
     * @return A new instance of the item.
     */
    Item createItem();

    /**
     * Defines the effect that should occur when the item is purchased.
     * <p>
     * This may include attribute changes, health/stamina effects, or spawning creatures,
     * and is triggered immediately upon successful purchase.
     *
     * @param purchaser The actor purchasing the item (usually the player).
     * @param map       The game map where the purchase is occurring.
     * @return A string describing the result of the purchase effect.
     */
    String uponPurchase(Actor purchaser, GameMap map);
}
