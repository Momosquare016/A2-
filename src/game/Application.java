package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.*;
import game.actors.*;
import game.actors.boss.BedOfChaos;
import game.actors.npcs.*;
import game.behaviours.RandomBehaviourSelector;
import game.behaviours.SequentialBehaviourSelector;
import game.grounds.*;
import game.items.*;
import game.time.TimeManager;
import game.weapons.Moonveil;

import java.util.Arrays;
import java.util.List;

/**
 * The main class to setup and run the game.
 * @author Adrian Kristanto.
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Blight(),
                new Wall(), new Floor(), new Soil(), new TeleportationPortal());

        List<String> valleyMap = Arrays.asList(
                "xxxx...xxxxxxxxxxxxxxxxxxxxxxx........xx",
                "xxx.....xxxxxxx..xxxxxxxxxxxxx.........x",
                "..........xxxx....xxxxxxxxxxxxxx.......x",
                "....xxx...........xxxxxxxxxxxxxxx.....xx",
                "...xxxxx...........xxxxxxxxxxxxxx.....xx",
                "...xxxxxxxxxx.......xxxxxxxx...xx......x",
                "....xxxxxxxxxx........xxxxxx...xxx......",
                "....xxxxxxxxxxx.........xxx....xxxx.....",
                "....xxxxxxxxxxx................xxxx.....",
                "...xxxx...xxxxxx.....#####.....xxx......",
                "...xxx....xxxxxxx....#___#.....xx.......",
                "..xxxx...xxxxxxxxx...#___#....xx........",
                "xxxxx...xxxxxxxxxx...##_##...xxx.......x",
                "xxxxx..xxxxxxxxxxx.........xxxxx......xx",
                "xxxxx..xxxxxxxxxxxx.......xxxxxx......xx");
        GameMap valley = new GameMap("Valley of the Inheritree", groundFactory, valleyMap);
        world.addGameMap(valley);

        List<String> limveldMap = Arrays.asList(
                ".............xxxx",
                "..............xxx",
                "................x",
                ".................",
                "................x",
                "...............xx",
                "..............xxx",
                "..............xxx",
                "..............xxx",
                ".............xxxx",
                ".............xxxx",
                "....xxx.....xxxxx",
                "....xxxx...xxxxxx");

        GameMap limveld = new GameMap("portal of the Inheritree", groundFactory, limveldMap);
        world.addGameMap(limveld);

        SpiritGoat randomGoat = new SpiritGoat(new RandomBehaviourSelector());
        OmenSheep randomSheep = new OmenSheep(new RandomBehaviourSelector());
        GoldenBeetle randomBeetle = new GoldenBeetle(new RandomBehaviourSelector());

        limveld.at(11,12).addActor(randomGoat);
        limveld.at(8, 10).addActor(randomSheep);
        limveld.at(13,8).addActor(randomBeetle);

        Actor bedOfChaos = new BedOfChaos(new SequentialBehaviourSelector());
        limveld.addActor(bedOfChaos, limveld.at(8, 6));

        Location portalValley = valley.at(10, 10);
        Location portalLimveld = limveld.at(10, 10);

        setUpPortal("Limveld", portalValley, portalLimveld);
        setUpPortal("Inheritree", portalLimveld, portalValley);

        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        SpiritGoat sequentialGoat = new SpiritGoat(new SequentialBehaviourSelector());
        OmenSheep sequentialSheep = new OmenSheep(new SequentialBehaviourSelector());
        GoldenBeetle sequentialBeetle = new GoldenBeetle(new SequentialBehaviourSelector());
        Guts sequentialGuts = new Guts(new SequentialBehaviourSelector());
        MerchantKale sequentialMerchantKale = new MerchantKale(new SequentialBehaviourSelector());
        Sellen sequentialSellen = new Sellen(new SequentialBehaviourSelector());

        valley.addActor(sequentialGoat, valley.at(20, 11));
        valley.addActor(sequentialBeetle, valley.at(7, 7));
        valley.addActor(sequentialSheep, valley.at(26, 13));
        valley.addActor(sequentialGuts, valley.at(21, 14));
        valley.addActor(sequentialMerchantKale, valley.at(16, 10));
        valley.addActor(sequentialSellen, valley.at(22, 11));

        TimeManager timeManager = new TimeManager();
        Player player = new Player("Farmer", '@', 100, 200, timeManager);
        world.addPlayer(player, valley.at(23, 10));

        player.addItemToInventory(new Seed(new Inheritree()));
        player.addItemToInventory(new Seed(new Bloodrose()));

        valley.at(24, 11).addItem(new Talisman());

        //Setup for A3REQ3 starts here
        generatePoisonFog(valley, 13, 9, 17, 11, timeManager);

        valley.at(24, 13).addActor(new SolarHound(timeManager, new SequentialBehaviourSelector()));
        valley.at(22, 11).addItem(new Moonveil(timeManager));

        valley.at(24, 14).addActor(new TrainingDummy(500, new SequentialBehaviourSelector()));
        valley.at(23, 14).addActor(new TrainingDummy(500, new SequentialBehaviourSelector()));
        valley.at(22, 14).addActor(new TrainingDummy(500, new SequentialBehaviourSelector()));
        //Setup for A3REQ3 ends here

        // === REQ4: Cursed Relics setup ===
        valley.at(23, 10).setGround(new RelicAltar());
        valley.at(23, 10).addItem(new RelicItemWrapper(new RelicOfBerserker()));
        valley.at(25, 10).addItem(new RelicItemWrapper(new RelicOfAllure()));   // optional: more relics
        valley.at(24, 9).addItem(new RelicItemWrapper(new RelicOfSpeed()));     // optional: more relics
        new Display().println("Tip: Find the Relic Altar at (23, 10) for ancient powers!");


        world.run();
    }

    public static void generatePoisonFog(GameMap map, int xA, int yA, int xB, int yB, TimeManager timeManager) {
        for (int x = xA; x <= xB; x++) {
            for (int y = yA; y <= yB; y++) {
                Location location = map.at(x, y);
                PoisonFog fog = new PoisonFog(timeManager, location);
                location.setGround(fog);
            }
        }
    }

    public static void setUpPortal(String name, Location location, Location destination) {
        TeleportationPortal portal = new TeleportationPortal();
        portal.addDestination(name, destination);
        location.setGround(portal);
    }
}
