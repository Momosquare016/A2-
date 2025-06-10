package game.enums;

/**
 * Use this enum class to represent a status.
 * Example #1: if the player is sleeping, you can attach a Status.SLEEP to the player class
 * @author Riordan D. Alfredo
 */
public enum Status {
    HOSTILE_TO_ENEMY,
    ATTACKABLE,
    CURSED,
    CURABLE,
    BLESSED_BY_GRACE,  // For Inheritree and other blessed entities
    FOLLOWABLE,    // For actors that can be followed
    CAN_GROW ,      // For entities that have growth capabilities
    POISONED,
    ACTIVE_ATTACKER,
    PASSIVE_ENEMY
}
