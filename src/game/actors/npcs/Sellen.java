package game.actors.npcs;


import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ListenAction;
import game.actions.PurchaseAction;
import game.actors.NPC;
import game.behaviours.RandomBehaviourSelector;
import game.behaviours.SequentialBehaviourSelector;
import game.behaviours.WanderBehaviour;
import game.effects.*;
import game.enums.Status;
import game.interfaces.BehaviourSelectionStrategy;
import game.interfaces.Listenable;
import game.interfaces.Merchant;
import game.weapons.Broadsword;
import game.weapons.Greatsword;
import game.weapons.Katana;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A concrete NPC representing Sellen.
 * Sellen is a character who shares monologues when approached.
 * She wanders around the map and may be attacked by the player.
 * Her dialogue options are non-conditional when interacted with a {@link ListenAction}.
 * Created by:
 * @author Chan Chee Wei
 */
public class Sellen extends NPC implements Listenable, Merchant {

    /** Random number generator used for selecting a monologue. */
    private final Random random = new Random();

    /**
     * Constructor.Sellen is initialized with:
     * <ul>
     *      <li>{@link Status#ATTACKABLE} capability</li>
     *      <li>{@link WanderBehaviour} to allow idle movement</li>
     * </ul>
     */
    public Sellen(BehaviourSelectionStrategy strategy) {
        super("Sellen", 's', 150, strategy);
        addCapability(Status.ATTACKABLE);

        addBehaviour(999, new WanderBehaviour());
    }

    /**
     * Returns one of Sellen’s fixed monologues, selected randomly.
     * Unlike other NPCs, her monologue does not depend on the listener’s state or context.
     *
     * @param listener the actor listening to the monologue
     * @param map the current game map
     * @return a string representing a line of monologue
     */
    @Override
    public String getMonologue(Actor listener, GameMap map) {
        ArrayList<String> monologues = new ArrayList<>();
        monologues.add("The academy casts out those it fears. Yet knowledge, like the stars, cannot be bound forever.");
        monologues.add("You sense it too, don’t you? The Glintstone hums, even now.");
        int index = random.nextInt(monologues.size());
        return monologues.get(index);
    }

    /**
     * Returns the list of allowable actions that another actor can perform on Sellen.
     * <p>
     * This includes inherited actions from {@link NPC}, a {@link ListenAction}
     * that allows the player to hear Sellen's monologue, and {@link PurchaseAction}s
     * for buying items such as Broadsword, Dragonslayer Greatsword, and Katana,
     * each with unique effects applied upon purchase.
     *
     * @param otherActor The actor interacting with Sellen (e.g., the player).
     * @param direction The direction from the actor to Sellen.
     * @param map The current game map.
     * @return A list of actions available to the interacting actor.
     */

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        actions.add(new ListenAction(this));

        Effect uniqueEffect = new AttributeEffect(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 20, true);
        actions.add(new PurchaseAction(this, new Broadsword(), uniqueEffect, 100));

        uniqueEffect = new SpawningEffect(new GoldenBeetle(new SequentialBehaviourSelector()));
        actions.add(new PurchaseAction(this, new Greatsword(), uniqueEffect, 1500));

        List<Effect> effects= new ArrayList<>();
        effects.add(new SpawningEffect(new GoldenBeetle(new SequentialBehaviourSelector())));
        effects.add(new AttributeEffect(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 10, false));
        effects.add(new AttributeEffect(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 20, true));
        uniqueEffect = new CompositeEffect(effects);
        actions.add(new PurchaseAction(this, new Katana(), uniqueEffect, 500));
        return actions;
    }
}

