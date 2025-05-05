package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;

/**
 * An Action to attack another Actor.
 */
public class AttackAction extends Action {

    private Actor target;
    private String direction;

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     * @param direction the direction of the attack
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Weapon weapon = actor.getIntrinsicWeapon();

        // The weapon's attack method handles the hit chance, damage calculation,
        // and returns an appropriate message
        String result = weapon.attack(actor, target, map);

        // Check if target is knocked out after the attack
        if (!target.isConscious()) {
            result += System.lineSeparator() + target.unconscious(actor, map);
        }

        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction;
    }
}