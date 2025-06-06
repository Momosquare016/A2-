package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An {@link Effect} implementation that modifies an {@link Actor}'s attribute.
 * This effect can apply an operation (increase, decrease, or update) to an actor's current or maximum attribute value.
 * It supports modifying base actor attributes such as health, stamina, or any other attribute defined in {@link BaseActorAttributes}.
 * Created by:
 * @author Chan Chee Wei
 */
public class AttributeEffect implements Effect{
    private final BaseActorAttributes attribute;
    private final ActorAttributeOperations operation;
    private final int points;
    private final boolean isMaximum;

    /**
     * Constructs a new AttributeEffect.
     *
     * @param attribute the attribute to modify
     * @param operation the operation to apply (e.g., increase, decrease)
     * @param points the number of points to apply the operation with
     * @param isMaximum whether to apply the operation on the maximum attribute value
     */
    public AttributeEffect(BaseActorAttributes attribute, ActorAttributeOperations operation, int points, boolean isMaximum) {
        this.attribute = attribute;
        this.operation = operation;
        this.points = points;
        this.isMaximum = isMaximum;
    }

    /**
     * Applies the attribute effect to the given actor.
     * If {@code isMaximum} is true, modifies the actor's maximum attribute value.
     * The effect applies only if the actor possesses the specified attribute.
     *
     * @param actor the actor affected by the effect
     * @param map the current game map (unused in this effect)
     * @return a string description of the result of the attribute change
     */
    @Override
    public String trigger(Actor actor, GameMap map) {
        if (!actor.hasAttribute(attribute)) {
            return String.format("%s does not have a %s attribute", actor, attribute.toString().toLowerCase());
        }
        String result = actor + "'s ";
        if (isMaximum) {
            actor.modifyAttributeMaximum(attribute, operation, points);
            result += "maximum ";
        }

        actor.modifyAttribute(attribute, operation, points);
        result += attribute.toString().toLowerCase();

        switch (operation) {
            case INCREASE:
                result += " increases by ";
                break;
            case DECREASE:
                result += " decreases by ";
                break;
            case UPDATE:
                result += " is now ";
                break;
        }

        result += points;

        return result;
    }
}