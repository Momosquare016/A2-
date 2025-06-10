package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.interfaces.DynamicWeapon;
import java.util.Random;
import game.interfaces.DynamicDamageCalculator;

/**
 * Special weapon for bosses that can have dynamic damage calculations.
 * Uses dependency injection to get damage calculation from the actor.
 */
public class BossWeapon extends IntrinsicWeapon implements DynamicWeapon {
    private final DynamicDamageCalculator damageCalculator;

    public BossWeapon(DynamicDamageCalculator damageCalculator) {
        super(25, "strikes", 75); // Base damage: 25, hit rate: 75%
        this.damageCalculator = damageCalculator;
    }

    @Override
    public int getCurrentDamage() {
        return damage + damageCalculator.calculateBonusDamage();
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        int totalDamage = getCurrentDamage();

        Random rand = new Random();
        if (!(rand.nextInt(100) < hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(totalDamage);
        return String.format("%s %s %s for %d damage (base: %d + bonus: %d)",
                attacker, verb, target, totalDamage, damage, damageCalculator.calculateBonusDamage());
    }

    @Override
    public String toString() {
        return "Chaos Strike";
    }
}