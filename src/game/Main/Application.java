package game.Main;

import java.util.Arrays;
import java.util.List;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.Enum.EntityType;
import game.Enum.SeedType;
import game.Plants.Seed;
import game.actors.Player;
import game.ground.Blight;
import game.ground.Floor;
import game.ground.Soil;
import game.ground.Wall;
import game.animals.AnimalFactory;
import game.items.Talisman;

/**
 * The main class to setup and run the game.
 * @author Adrian Kristanto
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Blight(),
                new Wall(), new Floor(), new Soil());

        List<String> map = Arrays.asList(
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

        GameMap gameMap = new GameMap("Valley of the Inheritree", groundFactory, map);
        world.addGameMap(gameMap);

        // BEHOLD, ELDEN THING!
        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        Player player = new Player("Farmer", '@', 100);
        world.addPlayer(player, gameMap.at(23, 10));

        player.addItemToInventory(new Seed(SeedType.INHERITREE));
        player.addItemToInventory(new Seed(SeedType.BLOODROSE));

        // game setup
        gameMap.at(24, 11).addItem(new Talisman());

        // Add some spirit goats and omen sheep to the map
        AnimalFactory.createAnimalAt(EntityType.SPIRIT_GOAT, gameMap.at(20, 8));
        AnimalFactory.createAnimalAt(EntityType.SPIRIT_GOAT, gameMap.at(25, 12));
        AnimalFactory.createAnimalAt(EntityType.OMEN_SHEEP, gameMap.at(18, 10));
        AnimalFactory.createAnimalAt(EntityType.OMEN_SHEEP, gameMap.at(28, 9));


        world.run();
    }
}
