package game.actors.npcs;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.LocationUtils;
import game.actions.ListenAction;
import game.actions.PurchaseAction;
import game.actors.NPC;
import game.behaviours.RandomBehaviourSelector;
import game.behaviours.SequentialBehaviourSelector;
import game.behaviours.WanderBehaviour;
import game.effects.AttributeEffect;
import game.enums.Status;
import game.interfaces.BehaviourSelectionStrategy;
import game.interfaces.Listenable;
import game.interfaces.Merchant;
import game.weapons.Broadsword;
import game.weapons.Greatsword;

import java.util.ArrayList;
import java.util.Random;


/**
 * A concrete NPC representing Merchant Kale.
 * Merchant Kale is a non-hostile, attackable wanderer who provides dynamic monologues
 * based on the player's state and surroundings.
 * He wanders randomly and offers conditional dialogue through {@link ListenAction}.
 * Created by:
 * @author Chan Chee Wei
 */
public class MerchantKale extends NPC implements Listenable, Merchant {

    /** Random number generator for selecting a monologue. */
    private final Random random = new Random();

    /**
     * Constructor.
     * Merchant Kale is initialized with:
     <ul>
     *     <li>{@link Status#ATTACKABLE} capability</li>
     *     <li>A {@link WanderBehaviour} to allow random movement across the map</li>
     * </ul>
     */
    public MerchantKale(BehaviourSelectionStrategy strategy) {
        super("Merchant Kale", 'k', 200, strategy);
        addCapability(Status.ATTACKABLE);

        addBehaviour(999, new WanderBehaviour());
    }

    /**
     * Returns a monologue from Merchant Kale based on the state of the listener
     * and the surrounding environment.
     * Monologues are conditionally selected depending on:
     * <ul>
     *     <li>If the listener has a low balance (&lt; 500)</li>
     *     <li>If the listener's inventory is empty</li>
     *     <li>If nearby tiles contain cursed elements (actors, items, or ground)</li>
     * </ul>
     * If none of the above conditions are met, a general wandering monologue is still provided.
     *
     * @param listener the actor who is listening to Kale
     * @param map the current game map
     * @return a selected monologue string
     */
    @Override
    public String getMonologue(Actor listener, GameMap map) {
        ArrayList<String> monologues = new ArrayList<>();
        if (listener.getBalance() < 500) {
            monologues.add("Ah, hard times, I see. Keep your head low and your blade sharp.");
        }
        if (listener.getItemInventory().isEmpty()) {
            monologues.add("Not a scrap to your name? Even a farmer should carry a trinket or two.");
        }
        if (checkSurroundingsForCursed(map)) {
            monologues.add("Rest by the flame when you can, friend. These lands will wear you thin.");
        }
        monologues.add("A merchant’s life is a lonely one. But the roads… they whisper secrets to those who listen.");

        int index = random.nextInt(monologues.size());
        return monologues.get(index);
    }

    /**
     * Checks the tiles adjacent to Merchant Kale for any cursed entities that
     * are present (ground, actor, or item).
     * A tile is considered cursed if its ground, occupying actor, or any item on it
     * has the {@link Status#CURSED} capability.
     *
     * @param map the game map containing the actor
     * @return {@code true} if any adjacent location has a cursed entity, false otherwise
     */
    private boolean checkSurroundingsForCursed(GameMap map) {
        return new LocationUtils(map.locationOf(this)).hasSurroundingWith(Status.CURSED);
    }


    /**
     * Returns a list of actions that another actor can perform on Merchant Kale.
     * In addition to default NPC interactions, this includes the {@link ListenAction}
     * which allows the interacting actor to hear a conditional monologue.
     *
     * @param otherActor the actor performing the interaction
     * @param direction the direction of Kale relative to the other actor
     * @param map the current game map
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        actions.add(new ListenAction(this));

        // Broadsword: increase max stamina by 30
        actions.add(new PurchaseAction(this, new Broadsword(),
                new AttributeEffect(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 30, true),
                150
        ));
        actions.add(new PurchaseAction(this, new Greatsword(),
                new AttributeEffect(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 20, false),
                1700
        ));

        return actions;
    }
}

