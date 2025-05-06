package game;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.grounds.Bloodrose;
import game.grounds.Inheritree;
import game.items.Seed;
import game.grounds.Blight;
import game.grounds.Floor;
import game.grounds.Soil;
import game.grounds.Wall;
import game.items.Talisman;
import game.actors.OmenSheep;
import game.actors.SpiritGoat;
import game.actors.Player;

import java.util.Arrays;
import java.util.List;

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

        gameMap.at(22,11).addActor(new OmenSheep());
        gameMap.at(22,10).addActor(new SpiritGoat());

        Player player = new Player("Farmer", '@', 100,200);
        world.addPlayer(player, gameMap.at(23, 10));

        player.addItemToInventory(new Seed(new Inheritree()));
        player.addItemToInventory(new Seed(new Bloodrose()));

        // game setup
        gameMap.at(24, 11).addItem(new Talisman());

        world.run();
    }
}
