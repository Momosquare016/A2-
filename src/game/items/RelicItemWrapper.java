package game.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ActivateRelicAction;

import java.util.List;

/**
 * A wrapper class for cursed relics to make them usable as in-game items.
 * <p>
 * This class allows a {@link CursedRelic} to be treated as a non-portable {@link Item} and
 * enables interaction with the relic via an {@link ActivateRelicAction}.
 * </p>
 *
 * Created by:
 * @author Yathis
 */
public class RelicItemWrapper extends Item {

    /** The cursed relic this item wraps. */
    private final CursedRelic relic;

    /**
     * Constructor to create a RelicItemWrapper.
     * Sets the item name and display character based on the given relic,
     * and marks the item as non-portable.
     *
     * @param relic The {@link CursedRelic} to be wrapped as an item.
     */
    public RelicItemWrapper(CursedRelic relic) {
        super(relic.getName(), '¤', false);  // portable=false
        this.relic = relic;
    }

    /**
     * Returns the list of actions available when the item is on the ground.
     * Adds an {@link ActivateRelicAction} to allow activation from the ground.
     *
     * @param location The location where the item currently is.
     * @return An {@link ActionList} containing the possible actions.
     */
    @Override
    public ActionList allowableActions(Location location) {
        ActionList actions = new ActionList();
        actions.add(new ActivateRelicAction(relic));
        return actions;
    }

    /**
     * Returns the list of actions available when the item is in an actor's inventory.
     * This implementation returns an empty list, meaning no inventory actions are available.
     *
     * @param actor     The actor possessing the item.
     * @param direction The direction of the item (unused).
     * @param map       The current game map (unused).
     * @return An empty list of actions.
     */
    public List<Action> getAllowableActions(Actor actor, String direction, GameMap map) {
        return List.of();
    }
}











