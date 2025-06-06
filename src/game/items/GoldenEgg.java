package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.npcs.GoldenBeetle;
import game.behaviours.RandomBehaviourSelector;
import game.behaviours.SequentialBehaviourSelector;
import game.enums.Status;
import game.LocationUtils;
import game.interfaces.BehaviourSelectionStrategy;

import java.util.function.Supplier;


public class GoldenEgg extends Egg {
    private final BehaviourSelectionStrategy strategy;


    private GoldenEgg(BehaviourSelectionStrategy strategy) {
        super("Golden Egg", () -> new GoldenBeetle(strategy));
        this.strategy = strategy;
    }


    public static GoldenEgg createValleyEgg() {
        return new GoldenEgg(new SequentialBehaviourSelector());
    }


    public static GoldenEgg createLimveldEgg() {
        return new GoldenEgg(new RandomBehaviourSelector());
    }

    @Override
    public boolean canHatch(Location currentLocation) {
        return new LocationUtils(currentLocation).hasSurroundingWith(Status.CURSED);
    }

    @Override
    public void hatch(Location currentLocation) {
        currentLocation.removeItem(this);
        Actor goldenBeetle = spawnSupplier.get();

        if (!currentLocation.containsAnActor()) {
            currentLocation.addActor(goldenBeetle);
        } else {
            for (var exit : currentLocation.getExits()) {
                if (!exit.getDestination().containsAnActor()) {
                    exit.getDestination().addActor(goldenBeetle);
                    return;
                }
            }
        }
    }

    @Override
    public String consumeEffect(Actor consumer, GameMap map) {
        if (consumer.hasAttribute(BaseActorAttributes.STAMINA)) {
            consumer.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 20);
        }
        return consumer + " consumes " + this + " and restores 20 stamina";
    }
}