package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.npcs.OmenSheep;
import game.behaviours.RandomBehaviourSelector;
import game.behaviours.SequentialBehaviourSelector;
import game.interfaces.BehaviourSelectionStrategy;


public class OmenEgg extends Egg {
    private int hatchCounter = 0;
    private static final int HATCH_TIME = 3;
    private final BehaviourSelectionStrategy strategy;


    private OmenEgg(BehaviourSelectionStrategy strategy) {
        super("Omen Sheep Egg", () -> new OmenSheep(strategy));
        this.strategy = strategy;
    }


    public static OmenEgg createValleyEgg() {
        return new OmenEgg(new SequentialBehaviourSelector());
    }


    public static OmenEgg createLimveldEgg() {
        return new OmenEgg(new RandomBehaviourSelector());
    }

    @Override
    public boolean canHatch(Location currentLocation) {
        return ++hatchCounter >= HATCH_TIME;
    }

    @Override
    public void hatch(Location currentLocation) {
        currentLocation.removeItem(this);
        Actor omenSheep = spawnSupplier.get();


        if (!currentLocation.containsAnActor()) {
            currentLocation.addActor(omenSheep);
        } else {
            for (var exit : currentLocation.getExits()) {
                if (!exit.getDestination().containsAnActor()) {
                    exit.getDestination().addActor(omenSheep);
                    return;
                }
            }
        }
    }

    @Override
    public String consumeEffect(Actor consumer, GameMap map) {
        consumer.removeItemFromInventory(this);
        consumer.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 10);
        consumer.heal(10);  // Also heal to match the new maximum
        return consumer + " consume " + this + " and gains 10 maximum health!";
    }}