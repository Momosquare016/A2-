package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.interfaces.Consumable;

import java.util.function.Supplier;

/**
 * An abstract class for eggs that can be consumed or hatched into an actor.
 * Concrete eggs define their own hatching conditions and hatch logic.
 * Created by:
 * @author Chan Chee Wei
 * @author Muhammad Ali Raza
 */
public abstract class Egg extends Item implements Consumable {
    protected final Supplier<Actor> spawnSupplier;

    public Egg(String name, Supplier<Actor> spawnSupplier) {
        super(name, '0', true);
        this.spawnSupplier = spawnSupplier;
    }

    /**
     * Called every turn when the egg is on the ground.
     * Checks if there are cursed entities nearby and hatches into a Golden Beetle if conditions are met.
     *
     * @param currentLocation the location of the egg
     */
    @Override
    public void tick(Location currentLocation) {
        if (canHatch(currentLocation)) {
            currentLocation.removeItem(this);
            hatch(currentLocation);
        }
    }

    /**
     * Returns the list of actions that can be performed with this egg.
     * Includes an ConsumeAction when the egg is in an actor's inventory.
     *
     * @param owner the actor who owns the egg
     * @param map the game map
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        actions.add(new ConsumeAction(this));

        return actions;
    }

    public abstract boolean canHatch(Location currentLocation);

    public abstract void hatch(Location currentLocation);
}
