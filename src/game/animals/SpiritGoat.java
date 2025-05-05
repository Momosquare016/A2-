package game.animals;

import game.Enum.EntityType;

/**
 * A spirit goat that wanders around the valley.
 */
public class SpiritGoat extends Animal {
    /**
     * Constructor for SpiritGoat
     */
    public SpiritGoat() {
        super("Spirit Goat", 'y', 50, EntityType.SPIRIT_GOAT, 10);
    }
}