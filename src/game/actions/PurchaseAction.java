package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.effects.Effect;
import game.interfaces.Merchant;
import game.interfaces.Sellable;

/**
 * Represents an action where an actor (typically the player) purchases an item from a merchant.
 * This action deducts the cost, adds the item to the actor's inventory, and applies both the item's purchase effect
 * and any unique effect associated with the merchant or item.
 * @author yathis
 */
public class PurchaseAction extends Action {
    /** The merchant selling the item. */
    private final Merchant merchant;
    /** The item being sold, must implement {@link Sellable}. */
    private final Sellable sellingItem;
    /** A unique effect triggered upon purchase (e.g. spawn an NPC, boost stamina). */
    private final Effect uniqueEffect;
    /** The price of the item in runes. */
    private final int price;

    /**
     * Constructor for PurchaseAction.
     *
     * @param merchant     The merchant selling the item.
     * @param sellingItem  The item being sold, which implements {@link Sellable}.
     * @param uniqueEffect An additional effect applied upon purchase.
     * @param price        The rune cost of the item.
     */
    public PurchaseAction(Merchant merchant, Sellable sellingItem, Effect uniqueEffect, int price) {
        this.merchant = merchant;
        this.sellingItem = sellingItem;
        this.uniqueEffect = uniqueEffect;
        this.price = price;
    }

    /**
     * Executes the purchase logic: checks balance, adds the item to inventory, and applies effects.
     *
     * @param actor The actor performing the action (e.g., the player).
     * @param map   The game map the actor is on.
     * @return A string summarizing the result of the purchase.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        if (actor.getBalance() < price) {
            return actor + "'s balance is insufficient.";
        }

        Item item = sellingItem.createItem();
        actor.addItemToInventory(item);

        String result = actor + "has successfully purchased " + sellingItem;
        result += "\n" + sellingItem.uponPurchase(actor, map);
        result += "\n" + uniqueEffect.trigger(actor, map);
        return result;
    }

    /**
     * Returns a description of this action suitable for display in the game menu.
     *
     * @param actor The actor performing the action.
     * @return A string describing the purchase menu option.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " purchases " + sellingItem + " from " + merchant;
    }
}

