package game.Enum;

/**
 * Use this enum class to represent a status.
 * Example #1: if the player is sleeping, you can attack a Status.SLEEP to the player class
 * @author Riordan D. Alfredo
 */
public enum Status {
    HOSTILE_TO_ENEMY,
    WANDERER,       // Entity that wanders around
    HOSTILE,        // Entity that is hostile to the player
    HAS_STAMINA,    // Entity that has stamina
    CAN_PLANT,      // New capability for REQ2
}
