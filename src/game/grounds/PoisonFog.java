package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.LocationUtils;
import game.time.TimeManager;
import game.effects.PoisonEffect;
import game.enums.Ability;
import game.enums.Status;
import game.time.Time;
import game.interfaces.TimeSensitive;

import java.util.List;

/**
 * A toxic fog that temporarily spreads over an area during specific times of day,
 * poisoning nearby actors with a {@link PoisonEffect}. Reverts to the original ground outside these times.
 * The fog appears at {@link Time#DAWN} and {@link Time#NOON}, and disappears otherwise.
 *
 * @author Chan Chee Wei
 */
public class PoisonFog extends Ground implements TimeSensitive {
    private final TimeManager timeManager;
    private final Location location;

    /**
     * The ground that this fog temporarily replaces.
     */
    private Ground fallbackGround;

    /**
     * Constructs a PoisonFog instance that overlays a location.
     * Registers itself with the {@link TimeManager} and stores the replaced ground.
     *
     * @param timeManager the game's time manager
     * @param location    the map location where the fog will appear
     */
    public PoisonFog(TimeManager timeManager, Location location) {
        super('p', "Poison Fog");
        this.timeManager = timeManager;
        this.location = location;

        setFallbackGround(location.getGround());
        this.addCapability(Ability.POISONOUS);
    }

    /**
     * Sets the ground to revert to when the poison fog disappears.
     * Also registers this fog with the {@link TimeManager}.
     *
     * @param ground the original ground to restore
     */
    public void setFallbackGround(Ground ground) {
        this.fallbackGround = ground;
        timeManager.addListener(this);
    }

    /**
     * Delegates to the fallback ground's implementation.
     *
     * @param actor the actor attempting to enter the location
     * @return true if the fallback ground allows entry
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return fallbackGround.canActorEnter(actor);
    }

    /**
     * Called every game tick. Applies poison to all adjacent and occupying actors if they are not already poisoned.
     * Delegates the tick behaviour to the fallback ground to ensure underlying effects persist.
     *
     * @param location the location where the ground resides
     */
    @Override
    public void tick(Location location) {
        fallbackGround.tick(location);
        List<Actor> actors = new LocationUtils(location).getAdjacentActors();

        if (location.containsAnActor()) {
            actors.add(location.getActor());
        }
        if (location.getActor() != null) {
            actors.add(location.getActor());
        }

        for (Actor actor : actors) {
            if (!actor.hasCapability(Status.POISONED)) {
                actor.addCapability(Status.POISONED);
                actor.addStatusEffect(new PoisonEffect(5, 5));
            }
        }
    }

    /**
     * Delegates thrown-object blocking behaviour to the fallback ground.
     *
     * @return true if the fallback ground blocks thrown objects
     */
    @Override
    public boolean blocksThrownObjects() {
        return fallbackGround.blocksThrownObjects();
    }

    /**
     * Handles time-based activation and deactivation of the poison fog.
     * At {@link Time#DAWN} and {@link Time#NOON}, the fog replaces the ground at its location.
     * At all other times, the original ground is restored.
     *
     * @param currentTime the current time of day
     */
    @Override
    public void onTimeChange(Time currentTime) {
        if (currentTime == Time.DAWN || currentTime == Time.NOON) {
            if (location.getGround() != this) {
                this.fallbackGround = location.getGround();
            }
            location.setGround(this);
        } else {
            location.setGround(fallbackGround);
        }
    }

    /**
     * Provides descriptive flavor messages based on the current time.
     *
     * @param currentTime the time of day
     * @return a message describing the fog's behaviour, or null if no message is relevant
     */
    @Override
    public String getMessage(Time currentTime) {
        return switch (currentTime) {
            case DAWN -> "A sickly green mist creeps over the land...";
            case DUSK -> "The fog recedes as the sun sets over the horizon.";
            default -> null;
        };
    }
}
