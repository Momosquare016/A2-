package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Curable;
import game.actions.CureAction;
import game.enums.Ability;
import game.enums.Status;

/**
 * A class representing a blight covering the ground of the valley.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 * @author Chan Chee Wei
 */
public class Blight extends Ground implements Curable {
    /**
     * Constructs a Blight ground with its display character and capabilities.
     */
    public Blight() {
        super('x', "Blight");
        this.addCapability(Status.CURSED);
        this.addCapability(Status.CURABLE);
    }

    /**
     * Returns a list of allowable actions, including curing if actor stands on this ground and has a curing item.
     *
     * @param actor the actor near or on the ground
     * @param location the location of this ground
     * @param direction the direction of the actor relative to the ground
     * @return the list of actions that can be performed
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        for (Item item : actor.getItemInventory()) {
            if (item.hasCapability(Ability.CURING_ITEM) && location.getActor() == actor) {
                actions.add(new CureAction(item, this));
            }
        }

        return actions;
    }

    /**
     * Cures this blight by transforming it into soil.
     *
     * @param actor the actor performing the cure
     * @param map the game map
     * @return a string describing the result of the cure
     */
    @Override
    public String cure(Actor actor, GameMap map) {
        map.locationOf(actor).setGround(new Soil());

        return "The blight under " + actor + " has transformed into soil.";
    }
}