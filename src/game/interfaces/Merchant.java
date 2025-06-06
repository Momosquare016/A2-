package game.interfaces;

/**
 * Marker interface for actors that serve as merchants in the game.
 * <p>
 * Implementing this interface identifies an NPC as a merchant,
 * allowing players to interact with them for item purchases (e.g., via {@code PurchaseAction}).
 * <p>
 * Merchants typically provide {@code allowableActions()} that return {@code PurchaseAction}s
 * for items they offer, along with optional {@code ListenAction}s if they also implement {@code Listenable}.
 * @author yathis
 */
public interface Merchant {
    // No methods defined — used as a type marker for merchant identification.
}
