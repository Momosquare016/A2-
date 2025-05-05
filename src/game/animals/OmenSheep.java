package game.animals;

import game.Enum.EntityType;

/**
 * An omen sheep that wanders around the valley.
 */
public class OmenSheep extends Animal {
    /**
     * Constructor for OmenSheep
     */
    public OmenSheep() {
        super("Omen Sheep", 'm', 75, EntityType.OMEN_SHEEP, 15);
    }
}