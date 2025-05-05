package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import game.Enum.Status;
import game.weapons.BareFist;

/**
 * Class representing the Player.
 * @author Adrian Kristanto
 */
public class Player extends Actor {
    private int stamina;
    private final int maxStamina;
    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.stamina = 200;
        this.maxStamina = 200;
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Status.HAS_STAMINA);
        this.addCapability(Status.CAN_PLANT);
        this.setIntrinsicWeapon(new BareFist());
    }

    /**
     * Get the current stamina of the player.
     *
     * @return The current stamina
     */
    public int getStamina() {
        return stamina;
    }

    /**
     * Get the maximum stamina of the player.
     *
     * @return The maximum stamina
     */
    public int getMaxStamina() {
        return maxStamina;
    }

    /**
     * Use stamina.
     *
     * @param amount Amount of stamina to use
     * @return true if had enough stamina, false otherwise
     */
    public boolean useStamina(int amount) {
        if (stamina >= amount) {
            stamina -= amount;
            return true;
        }
        return false;
    }

    /**
     * Restore stamina.
     *
     * @param amount Amount of stamina to restore
     */
    public void restoreStamina(int amount) {
        stamina = Math.min(stamina + amount, maxStamina);
    }

    /**
     * Display player attributes (health and stamina).
     *
     * @param display The display to show the attributes on
     */
    public void displayAttributes(Display display) {
        display.println(this + " (Health: " + this.getAttribute(BaseActorAttributes.HEALTH) +
                "/" + this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                ", Stamina: " + this.getStamina() + "/" + this.getMaxStamina() + ")");
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Display player attributes at each game tick
        displayAttributes(display);

        // Handle multi-turn Actions
        if (lastAction != null && lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }
}