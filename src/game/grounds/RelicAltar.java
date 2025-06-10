package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.*;

import java.util.Random;

/**
 * A mystical altar that occasionally spawns a cursed relic on the ground.
 * <p>
 * The {@code RelicAltar} has a 10% chance each game tick to generate a random {@link CursedRelic},
 * which is then wrapped in a {@link RelicItemWrapper} and placed on the ground.
 * </p>
 *
 * Created by:
 * @author Yathis
 */
public class RelicAltar extends Ground {

    /** A random number generator used for relic spawning logic. */
    private final Random random = new Random();

    /**
     * Constructor to create a RelicAltar with a special ground symbol and name.
     */
    public RelicAltar() {
        super('Ω', "Relic Altar");  // You can change the symbol or name if needed
    }


    /**
     * Called once per turn to potentially spawn a relic on this altar.
     * <p>
     * If the location does not already have an item, there is a 10% chance that
     * a random cursed relic will spawn at this location.
     * </p>
     *
     * @param location the location where the altar is placed
     */
    @Override
    public void tick(Location location) {
        // If the ground has no item yet, 10% chance to spawn a relic
        if (!location.getItems().isEmpty()) return;

        if (random.nextInt(100) < 10) {  // 10% chance per tick
            CursedRelic relic = generateRandomRelic();
            location.addItem(new RelicItemWrapper(relic));
        }
    }

    /**
     * Randomly selects and returns a new instance of one of the available cursed relics.
     *
     * @return a randomly chosen {@link CursedRelic}
     */
    private CursedRelic generateRandomRelic() {
        int roll = random.nextInt(3);
        return switch (roll) {
            case 0 -> new RelicOfBerserker();
            case 1 -> new RelicOfSpeed();
            default -> new RelicOfAllure();
        };
    }
}


