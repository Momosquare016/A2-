package game.animals;

import edu.monash.fit2099.engine.positions.Location;
import game.Enum.EntityType;

/**
 * A factory for creating animals.
 */
public class AnimalFactory {

    /**
     * Creates an animal of the specified type.
     *
     * @param type The type of animal to create
     * @return A new Animal instance
     */
    public static Animal createAnimal(EntityType type) {
        switch (type) {
            case SPIRIT_GOAT:
                return new SpiritGoat();
            case OMEN_SHEEP:
                return new OmenSheep();
            default:
                throw new IllegalArgumentException("Unknown animal type: " + type);
        }
    }

    /**
     * Creates an animal of the specified type at the specified location.
     *
     * @param type The type of animal to create
     * @param location The location to place the animal
     * @return A new Animal instance
     */
    public static Animal createAnimalAt(EntityType type, Location location) {
        Animal animal = createAnimal(type);
        location.addActor(animal);
        return animal;
    }
}