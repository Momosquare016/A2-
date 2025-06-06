package game;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.actors.*;
import game.actors.npcs.*;
import game.behaviours.RandomBehaviourSelector;
import game.behaviours.SequentialBehaviourSelector;
import game.grounds.*;
import game.items.Seed;
import game.items.Talisman;

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
                new Wall(), new Floor(), new Soil(), new TeleportationPortal('A'));

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
        GameMap valley = new GameMap("Valley of the Inheritree",groundFactory, valleyMap);
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

        GameMap limveld = new GameMap("portal of the Inheritree",groundFactory, limveldMap);
        world.addGameMap(limveld);


        Location portalValley = valley.at(10, 10);
        Location portalLimveld = limveld.at(10, 10);

        portalValley.setGround(new TeleportationPortal('A'));
        portalLimveld.setGround(new TeleportationPortal('A'));

        ((TeleportationPortal) portalValley.getGround()).addDestination("Limveld", portalLimveld);
        ((TeleportationPortal) portalLimveld.getGround()).addDestination("Inheritree", portalValley);

        // BEHOLD, ELDEN THING!
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





        SpiritGoat randomGoat = new SpiritGoat(new RandomBehaviourSelector());
        OmenSheep randomSheep = new OmenSheep(new RandomBehaviourSelector());
        GoldenBeetle randomBeetle = new GoldenBeetle(new RandomBehaviourSelector());


        valley.addActor(sequentialGoat, valley.at(20, 11));
        valley.addActor(sequentialBeetle, valley.at(7, 7));
        valley.addActor(sequentialSheep, valley.at(26, 13));
        valley.addActor(sequentialGuts, valley.at(23, 14));
        valley.addActor(sequentialMerchantKale, valley.at(16, 10));
        valley.addActor(sequentialSellen, valley.at(22, 11));




        Player player = new Player("Farmer", '@', 100,200);
        world.addPlayer(player, valley.at(23, 10));

        player.addItemToInventory(new Seed(new Inheritree()));
        player.addItemToInventory(new Seed(new Bloodrose()));

        // game setup
        valley.at(24, 11).addItem(new Talisman());

        world.run();
    }
}
