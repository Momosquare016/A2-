package game.actors.npcs;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ListenAction;
import game.actors.NPC;
import game.behaviours.ActiveAttackBehaviour;
import game.behaviours.WanderBehaviour;
import game.enums.Status;
import game.interfaces.Attacker;
import game.interfaces.BehaviourSelectionStrategy;
import game.interfaces.Listenable;
import game.weapons.BareFist;

import java.util.ArrayList;
import java.util.Random;

/**
 * A concrete NPC representing Guts, a berserk warrior that can attack the player and surrounding npcs.
 * Guts is attackable and has a berserk combat style. He will engage in combat when triggered,
 * otherwise he wanders the map. He also provides conditional monologues when interacted with
 * using a {@link ListenAction}.
 * Created by:
 * @author Chan Chee Wei
 */
public class Guts extends NPC implements Listenable, Attacker {

    /** Random generator for selecting monologues. */
    private final Random random = new Random();

    /**
     * Constructor.
     * Guts has 500 HP and a display character of 'g'.
     * He is initialized with:
     * <ul>
     *     <li>{@link Status#ATTACKABLE} capability</li>
     *     <li>{@link ActiveAttackBehaviour} with a health threshold of 50</li>
     *     <li>{@link WanderBehaviour} as a fallback behaviour</li>
     *     <li>{@link BareFist} as an intrinsic weapon</li>
     * </ul>
     */
    public Guts(BehaviourSelectionStrategy strategy) {
        super("Guts", 'g', 500, strategy);
        addCapability(Status.ATTACKABLE);

        addBehaviour(1, new ActiveAttackBehaviour(this));
        addBehaviour(999, new WanderBehaviour());

        this.setIntrinsicWeapon(new BareFist());
    }

    /**
     * Returns one of Guts' monologues, selected at random.
     * If the listening actor has health below 50, an additional dialogue is included.
     *
     * @param listener the actor who is listening
     * @param map the map where the interaction occurs
     * @return a string representing Guts' spoken monologue
     */
    @Override
    public String getMonologue(Actor listener, GameMap map) {
        ArrayList<String> monologues = new ArrayList<>();
        monologues.add("RAAAAGH!");
        monologues.add("I’LL CRUSH YOU ALL!");
        if (listener.getAttribute(BaseActorAttributes.HEALTH) < 50) {
            monologues.add("WEAK! TOO WEAK TO FIGHT ME!");
        }
        int index = random.nextInt(monologues.size());
        return monologues.get(index);
    }

    @Override
    public boolean canAttack(Actor target, GameMap map) {
        return target.hasCapability(Status.ATTACKABLE) &&
                target.getAttribute(BaseActorAttributes.HEALTH) >= 50;
    }

    /**
     * Returns a list of actions that another actor can perform on Guts.
     * This includes any standard NPC interactions, along with a {@link ListenAction}.
     *
     * @param otherActor the actor performing the interaction
     * @param direction the direction of Guts relative to the other actor
     * @param map the map containing both actors
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        actions.add(new ListenAction(this));
        return actions;
    }
}
