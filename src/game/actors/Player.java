package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.GameMap;
import game.FancyMessage;
import game.enums.Status;
import game.weapons.BareFist;

/**
 * Class representing the Player.
 * @author Adrian Kristanto
 */
public class Player extends Actor {
    /**
     * Constructor.
     * Adds STAMINA attribute to the Player class.
     * Marks Player as HOSTILE_TO_ENEMY.
     * Assigns BareFist as the IntrinsicWeapon.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints, int stamina) {
        super(name, displayChar, hitPoints);
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(stamina));

        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Status.ATTACKABLE);

        this.setIntrinsicWeapon(new BareFist());
    }

    /**
     * Displays the player's death message using the {@link FancyMessage#YOU_DIED} banner.
     * Introduces a short delay between each line similar to the Title message.
     *
     * @param display the display to print to
     */
    public void printDeathMessage(Display display) {
        for (String line : FancyMessage.YOU_DIED.split("\n")) {
            display.println(line);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public String unconscious(Actor actor, GameMap map) {
        printDeathMessage(new Display());

        return super.unconscious(actor, map);
    }

    @Override
    public String unconscious(GameMap map) {
        printDeathMessage(new Display());

        return super.unconscious(map);
    }

    /**
     * Handles the Player's turn.
     * If the player is unconscious, shows the death message and returns a dummy action.
     * If the last action is a multi-turn action, its next action will be executed.
     * Otherwise, shows a menu to let the player choose the next action.
     *
     * @param actions     The actions the player can choose from
     * @param lastAction  The last action the player performed
     * @param map         The current game map
     * @param display     The display to show the menu
     * @return The selected action to perform this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    /**
     * Returns a string representation of the Player's current health and stamina.
     *
     * @return A formatted string with the player's HP and STA
     */
    @Override
    public String toString() {
        return name + " (HP: " +
                this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                ")" + " (STA: " +
                this.getAttribute(BaseActorAttributes.STAMINA) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.STAMINA) +
                ")";
    }
}