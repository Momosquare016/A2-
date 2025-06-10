package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Abstract base class for all Cursed Relics in the game.
 * <p>
 * A CursedRelic is an ancient artifact that grants the player powerful buffs
 * at the cost of dangerous curses. Only one relic can be active at a time.
 * Subclasses must define the specific buffs and curses applied to the player,
 * as well as how to remove those effects.
 * </p>
 *
 * <p>
 * Created by: @author yathis
 * </p>
 */
public abstract class CursedRelic {
    /**
     * The name of this relic (e.g., "Relic of Berserker").
     */
    private final String name;
    /**
     * The description of this relic's effects.
     */
    private final String description;

    /**
     * Constructs a new CursedRelic with the given name and description.
     *
     * @param name        the name of the relic
     * @param description a short description of the relic's effects
     */
    public CursedRelic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name of the relic.
     *
     * @return the relic's name
     */
    public String getName() { return name; }

    /**
     * Returns the description of the relic's effects.
     *
     * @return the relic's description
     */
    public String getDescription() { return description; }

    /**
     * Applies the beneficial (buff) effect of this relic to the player.
     *
     * @param actor the player or actor affected by the relic's buff
     */
    public abstract void applyBuff(Actor actor);

    /**
     * Applies the harmful (curse) effect of this relic to the player.
     *
     * @param actor the player or actor affected by the relic's curse
     */
    public abstract void applyCurse(Actor actor);

    /**
     * Removes all effects (buffs and curses) of this relic from the player.
     *
     * @param actor the player or actor previously affected by the relic
     */
    public abstract void removeEffect(Actor actor);
}




