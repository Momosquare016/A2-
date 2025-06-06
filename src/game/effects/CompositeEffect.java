package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Effect} implementation that encapsulates and triggers multiple effects in sequence.
 * <p>
 * When {@code trigger()} is called, each contained {@code Effect} is triggered in order.
 * The result is a concatenation of the individual effect messages, separated by newlines.
 * This is useful when an action or event should apply multiple outcomes at once.
 * Created by:
 * @author Chan Chee Wei
 */
public class CompositeEffect implements Effect {
    private final List<Effect> effects;

    /**
     * An {@link Effect} implementation that encapsulates and triggers multiple effects in sequence.
     * When {@code trigger()} is called, each contained {@code Effect} is triggered in order.
     * The result is a concatenation of the individual effect messages, separated by newlines.
     * This is useful when an action or event should apply multiple outcomes at once.
     * Created by:
     * @author Chan Chee Wei
     */
    public CompositeEffect(List<Effect> effects) {
        this.effects = effects;
    }

    /**
     * Triggers all effects in the list on the given actor and game map.
     *
     * @param actor the actor affected by the effects
     * @param map   the game map context
     * @return a string combining the messages from each triggered effect, separated by newlines
     */
    @Override
    public String trigger(Actor actor, GameMap map) {
        List<String> result = new ArrayList<>();
        for (Effect effect : effects) {
            result.add(effect.trigger(actor, map));
        }
        return String.join("\n", result);
    }
}