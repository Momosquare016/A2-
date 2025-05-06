package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.PlantAction;
import game.enums.Ability;
import game.grounds.Bloodrose;
import game.grounds.Crop;
import game.grounds.Inheritree;

/**
 * A seed item that can be planted on fertile ground to grow a specific {@link Crop}.
 * Seeds are portable and can be used by players to plant magical crops like {@link Bloodrose} or {@link Inheritree}.
 * Created by:
 * @author Chan Chee Wei
 */
public class Seed extends Item {
    /**
     * The crop this seed will grow into.
     */
    private final Crop crop;

    /**
     * Constructs a seed for the given crop with a specified name and display character.
     *
     * @param name the name of the seed
     * @param displayChar the display character
     * @param crop the crop it will grow into
     */
    public Seed(String name, char displayChar, Crop crop) {
        super(name, displayChar, true);
        this.crop = crop;
    }

    /**
     * Constructs a seed for the given crop with auto-generated name and display character '*'.
     *
     * @param crop the crop it will grow into
     */
    public Seed(Crop crop) {
        super("Seed of " + crop, '*', true);
        this.crop = crop;
    }

    /**
     * Returns the crop this seed is associated with.
     *
     * @return the crop
     */
    public Crop getCrop() {
        return crop;
    }

    /**
     * Returns the actions that can be performed with this seed.
     * Adds {@link PlantAction} if the actor is standing on fertile ground.
     *
     * @param owner the actor who owns this seed
     * @param map the game map
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        if (map.locationOf(owner).getGround().hasCapability(Ability.FERTILE_GROUND)) {
            actions.add(new PlantAction(this));
        }
        return actions;
    }
}
