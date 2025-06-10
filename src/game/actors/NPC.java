package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.enums.Status;
import game.interfaces.BehaviourSelectionStrategy;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Abstract class representing a Non-Player Character (NPC) in the game.
 * NPCs a behavior-based system to determine their actions each turn.
 * Behaviors are prioritized and executed in ascending order of their keys.
 * If an NPC is {@code Status.ATTACKABLE}, it may be attacked by hostile actors.
 * Created by:
 * @author Chan Chee Wei
 */
public abstract class NPC extends Actor {
    /**
     * A mapping of behaviours with assigned priorities.
     * Lower integer values may indicate higher priority.
     */
    protected Map<Integer, Behaviour> behaviours = new TreeMap<>();
    protected BehaviourSelectionStrategy selectionStrategy;

    /**
     * Constructor to create an NPC with a name, display character, and hit points.
     *
     * @param name the name of the NPC
     * @param displayChar the character representing the NPC on the map
     * @param hitPoints the starting hit points of the NPC
     */
    public NPC(String name, char displayChar, int hitPoints, BehaviourSelectionStrategy strategy) {
        super(name, displayChar, hitPoints);
        this.selectionStrategy = strategy;
    }

    /**
     * Adds a behaviour to the NPC with a specified priority.
     *
     * @param priority the priority level of the behaviour
     * @param behaviour the behaviour to be added
     */
    public void addBehaviour(int priority, Behaviour behaviour) {
        this.behaviours.put(priority, behaviour);
    }

    /**
     * Determines the action that the NPC will take during its turn.
     * Executes the first non-null action returned by its behaviours in priority order.
     *
     * @param actions a list of allowable actions
     * @param lastAction the action the NPC performed last turn
     * @param map the current GameMap
     * @param display the Display object used to output messages
     * @return the chosen Action for this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return selectionStrategy.selectBehaviour(this, map, behaviours);
    }
    /**
     * Returns a list of allowable actions that another actor can perform on this NPC.
     * If the other actor is hostile and this NPC is attackable, an AttackAction is added.
     *
     * @param otherActor the actor interacting with this NPC
     * @param direction the direction of the other actor relative to this NPC
     * @param map the map containing both actors
     * @return an ActionList of actions that the other actor can perform
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY) && this.hasCapability(Status.ATTACKABLE)) {
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }

    /**
     * Returns a string representation of the NPC including current and maximum health.
     *
     * @return a formatted string displaying the NPC's name and health
     */
    @Override
    public String toString() {
        return name + " (HP: " +
                this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                ")";
    }
}