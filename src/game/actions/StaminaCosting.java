package game.actions;

/**
 * Interface for actions that require stamina to perform.
 * Any class implementing this interface must specify the stamina cost of the action.
 * This allows generic handling of stamina-related behavior in the game engine.
 * Used by actions such as {@link PlantAction} and {@link CureAction}.
 * Created by:
 * @author Chan Chee Wei
 */
public interface StaminaCosting {
    /**
     * Gets the stamina cost required to perform the action.
     *
     * @return the amount of stamina required
     */
    int getStaminaCost();
}