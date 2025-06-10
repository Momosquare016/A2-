package game.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.LocationUtils;
import game.actions.AttackAction;
import game.enums.Status;

import java.util.List;
import java.util.Random;

/**
 * Class representing items that can be used as a weapon.
 * @author Adrian Kristanto
 */
public class WeaponItem extends Item implements Weapon {

    private static final float DEFAULT_DAMAGE_MULTIPLIER = 1.0f;

    private int damage;
    private int hitRate;
    private final String verb;
    private float damageMultiplier;

    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public WeaponItem(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, true);
        this.damage = damage;
        this.verb = verb;
        this.hitRate = hitRate;
        this.damageMultiplier = DEFAULT_DAMAGE_MULTIPLIER;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return Math.round(damage * damageMultiplier);
    }

    public String getVerb() {
        return verb;
    }

    public void setHitRate(int hitRate) {
        this.hitRate = hitRate;
    }

    public int getHitRate() {
        return hitRate;
    }

    public void setDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public void resetDamageMultiplier() {
        this.damageMultiplier = DEFAULT_DAMAGE_MULTIPLIER;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        Location ownerLocation = map.locationOf(owner);
        List<Actor> targets = new LocationUtils(ownerLocation).getAdjacentActors();

        for (Actor target : targets) {
            if (target.hasCapability(Status.ATTACKABLE)) {
                String direction = new LocationUtils(ownerLocation).getDirectionToActor(target);
                actions.add(new AttackAction(target, direction, this));
            }
        }
        return actions;
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        if (isAttackSuccessful()) {
            target.hurt(getDamage());
            return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
        } else {
            return attacker + " misses " + target + ".";
        }
    }

    protected boolean isAttackSuccessful() {
        Random rand = new Random();
        return rand.nextInt(100) < this.hitRate;
    }
}
