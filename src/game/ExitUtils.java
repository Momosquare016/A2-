package game;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to help determine directional relationships between locations,
 * such as identifying flank directions relative to a primary direction.
 *
 * @author Chan Chee Wei
 */
public class ExitUtils {

    /**
     * Returns the directions considered as flanks for a given primary exit direction.
     * For example, if the primary direction is "North", the flank directions are "North-East" and "North-West".
     *
     * @param exit the primary {@link Exit} whose flank directions are requested
     * @return a list of direction names that flank the primary direction, or an empty list if the direction is unknown
     */
    public List<String> getFlankDirections(Exit exit) {
        return switch (exit.getName()) {
            case "North" -> List.of("North-East", "North-West");
            case "North-East" -> List.of("North", "East");
            case "East" -> List.of("North-East", "South-East");
            case "South-East" -> List.of("East", "South");
            case "South" -> List.of("South-East", "South-West");
            case "South-West" -> List.of("South", "West");
            case "West" -> List.of("North-West", "South-West");
            case "North-West" -> List.of("North", "West");
            default -> List.of();  // Empty list if unknown direction
        };
    }

    /**
     * Returns a list of {@link Exit}s from the given location that represent flank positions relative to
     * a specified primary exit.
     *
     * @param from    the {@link Location} from which exits are evaluated
     * @param primary the primary {@link Exit} representing the main direction
     * @return a list of {@link Exit}s that flank the primary exit direction
     */
    public List<Exit> getFlankExits(Location from, Exit primary) {
        List<String> flankDirections = getFlankDirections(primary);
        List<Exit> flankExits = new ArrayList<>();
        for (Exit exit : from.getExits()) {
            if (flankDirections.contains(exit.getName())) {
                flankExits.add(exit);
            }
        }
        return flankExits;
    }

    /**
     * Finds the exit from a given location that leads directly to a specified target location.
     *
     * @param from the starting {@link Location}
     * @param to   the destination {@link Location}
     * @return the {@link Exit} from 'from' that leads to 'to', or {@code null} if no such exit exists
     */
    public Exit getExitTowards(Location from, Location to) {
        for (Exit exit : from.getExits()) {
            if (exit.getDestination() == to) {
                return exit;
            }
        }
        return null;
    }
}
