package game.animals;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.Enum.EntityType;
import game.ground.RotEffect;
import game.Enum.Status;
import game.actions.CureAction;
import game.actions.WanderBehaviour;
import java.util.HashMap;
import java.util.Map;
import game.actors.Player;
import game.items.Talisman;


/**
 * An abstract class representing animals in the game.
 */
public abstract class Animal extends Actor implements RotEffect {

    protected Map<Integer, Behaviour> behaviours = new HashMap<>();
    protected EntityType type;
    protected int countdownTurns;
    protected int maxCountdownTurns;

    /**
     * Constructor.
     *
     * @param name        Name to call the animal in the UI
     * @param displayChar Character to represent the animal in the UI
     * @param hitPoints   Animal's starting number of hitpoints
     * @param type        The type of the animal
     * @param countdown   Initial countdown value for rot effect
     */
    public Animal(String name, char displayChar, int hitPoints, EntityType type, int countdown) {
        super(name, displayChar, hitPoints);
        this.type = type;
        this.countdownTurns = countdown;
        this.maxCountdownTurns = countdown;
        this.addCapability(Status.WANDERER);
        this.behaviours.put(999, new WanderBehaviour());
    }

    /**
     * Get the type of this animal.
     *
     * @return The EntityType of this animal
     */
    public EntityType getType() {
        return type;
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Decrement countdown due to rot
        decrementCountdown();

        // If countdown reaches zero, remove the animal
        if (countdownTurns <= 0) {
            map.removeActor(this);
            return new DoNothingAction();
        }

        // Normal behavior
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if(action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Returns a new collection of the Actions that the other Actor can do to this Animal.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of Actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // Allow attack if other actor is hostile to enemies
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction));
        }

        // Allow using the talisman on this animal if the actor has it
        if (otherActor instanceof Player) {
            for (Item item : otherActor.getItemInventory()) {
                if (item instanceof Talisman) {
                    actions.add(new CureAction(this, (Talisman) item));
                    break;
                }
            }
        }

        return actions;
    }

    // RotEffect implementation
    @Override
    public boolean hasCountdown() {
        return true;
    }

    @Override
    public int getCountdown() {
        return countdownTurns;
    }

    @Override
    public int decrementCountdown() {
        return --countdownTurns;
    }

    @Override
    public void resetCountdown() {
        countdownTurns = maxCountdownTurns;
    }

    @Override
    public int getMaxCountdown() {
        return maxCountdownTurns;
    }
}