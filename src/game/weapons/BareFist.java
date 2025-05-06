package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Class representing an intrinsic weapon called a bare fist.
 * This intrinsic weapon deals 25 damage points with a 50% chance
 * to hit the target.
 * @author Adrian Kristanto
 */
public class BareFist extends IntrinsicWeapon {

    /**
     * Constructs a new BareFist with 25 damage, "punches" as the verb, and 50% hit chance.
     */
    public BareFist() {
        super(25, "punches", 50);
    }

    /**
     * Returns a string representation of this intrinsic weapon.
     *
     * @return "Bare Fist"
     */
    @Override
    public String toString() {
        return "Bare Fist";
    }
}
