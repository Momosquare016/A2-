package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.enums.Ability;

/**
 * A class representing a Talisman that an actor can pick up and drop
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 * @author Chan Chee Wei
 */
public class Talisman extends Item {
    /**
     * Constructs a new Talisman item.
     */
    public Talisman() {
        super("Talisman", 'o', true);
        this.addCapability(Ability.CURING_ITEM);
    }
}
